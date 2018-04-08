package net.lelux.minigamelib;

import net.lelux.minigamelib.config.GameConfig;
import net.lelux.minigamelib.listeners.DamageListener;
import net.lelux.minigamelib.listeners.DeathListener;
import net.lelux.minigamelib.listeners.JoinLeaveListener;
import net.lelux.minigamelib.player.GamePlayer;
import net.lelux.minigamelib.teams.ScoreboardManager;
import net.lelux.minigamelib.timer.GameState;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Minigame extends JavaPlugin {

    private static GameConfig config;
    private static ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        preInitialisation();

        config = initialisation();

        GameState.set(GameState.LOBBY);
        config.getMySQL().connect();
        config.getVault().connect();
        scoreboardManager = new ScoreboardManager(config.getMap().getTeamList(), config.getMap().getTeamCount());
        initListeners();

        postInitialisation();
    }

    @Override
    public void onDisable() {
        onStop();
        config.getVault().disconnect();
        config.getMySQL().disconnect();
    }

    public void preInitialisation() {}

    public GameConfig initialisation() { return null; }

    public void postInitialisation() {}

    public void onStop() {

    }

    public static GameConfig getGameConfig() {
        return config;
    }

    public static Minigame getMinigame() {
        return config.getMinigame();
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public static void changedGameState() {
        if(GameState.is(GameState.LOBBY)) {
            Bukkit.getServer().getOnlinePlayers()
                    .forEach(p -> p.teleport(config.getMap().getLobbySpawn()));
        } else if(GameState.is(GameState.INGAME)) {
            Bukkit.getServer().getOnlinePlayers()
                    .forEach(p -> p.teleport(GamePlayer.toGamePlayer(p).getTeam().getSpawn()));
        } else if(GameState.is(GameState.END)) {
            Bukkit.getServer().getOnlinePlayers()
                    .forEach(p -> p.teleport(config.getMap().getEndSpawn()));
            Bukkit.getServer().getOnlinePlayers()
                    .forEach(p -> GamePlayer.toGamePlayer(p).setVisible(true));
            config.getStopCountdown().start();
        }
    }

    private void initListeners() {
        initListener(new DamageListener());
        initListener(new JoinLeaveListener());
        initListener(new DeathListener());
    }
    private void initListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, Minigame.getMinigame());
    }
}
