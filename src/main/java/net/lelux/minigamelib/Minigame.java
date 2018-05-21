package net.lelux.minigamelib;

import net.lelux.minigamelib.config.GameConfig;
import net.lelux.minigamelib.config.GameMap;
import net.lelux.minigamelib.listeners.DamageListener;
import net.lelux.minigamelib.listeners.DeathListener;
import net.lelux.minigamelib.listeners.InteractListener;
import net.lelux.minigamelib.listeners.JoinLeaveListener;
import net.lelux.minigamelib.player.GamePlayer;
import net.lelux.minigamelib.stats.StatsManager;
import net.lelux.minigamelib.teams.ScoreboardManager;
import net.lelux.minigamelib.timer.GameState;
import net.lelux.minigamelib.utils.Languages;
import net.lelux.minigamelib.utils.Log;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Minigame extends JavaPlugin {

    private static GameConfig config;
    private static ScoreboardManager scoreboardManager;
    private static StatsManager statsManager;

    @Override
    public void onEnable() {
        Log.info(Languages.getString("start", "PreInitialisation"), true);
        preInitialisation();
        Log.info(Languages.getString("stop", "PreInitialisation"), true);

        Log.info(Languages.getString("start", "Initialisation"), true);
        config = initialisation();

        Log.info(Languages.getString("stop", "Initialisation"), true);

        GameState.set(GameState.LOBBY);
        config.getMySQL().connect();
        config.getVault().connect();
        scoreboardManager = new ScoreboardManager(config.getMap().getTeamList(), config.getMap().getTeamCount());
        statsManager = new StatsManager();
        initListeners();

        Log.info(Languages.getString("start", "PostInitialisation"), true);
        postInitialisation();
        Log.info(Languages.getString("stop", "PostInitialisation"), true);
    }

    @Override
    public void onDisable() {
        onStop();
        config.getVault().disconnect();
        config.getMySQL().disconnect();
    }

    public void preInitialisation() {
    }

    public GameConfig initialisation() {
        return null;
    }

    public void postInitialisation() {
    }

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

    public static StatsManager getStatsManager() {
        return statsManager;
    }

    public static void changedGameState() {
        if (GameState.is(GameState.LOBBY)) {
            Bukkit.getServer().getOnlinePlayers()
                    .forEach(p -> p.teleport(config.getMap().getLobbySpawn()));
        } else if (GameState.is(GameState.INGAME)) {
            Bukkit.getServer().getOnlinePlayers()
                    .forEach(p -> p.teleport(GamePlayer.toGamePlayer(p).getTeam().getSpawn()));
        } else if (GameState.is(GameState.END)) {
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
        initListener(new InteractListener());
    }

    private void initListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, Minigame.getMinigame());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("map")) {
            if (sender.hasPermission("minigamelib.map")) {
                GameMap map = Minigame.getGameConfig().getMap();
                sender.sendMessage(Languages.getString("map_command",
                        map.getTeamCount() + "x" + map.getTeamSize(), map.getName()));
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("start")) {
            if (sender.hasPermission("minigamelib.start")) {
                if (Minigame.getGameConfig().getStartCountdown().isRunning() &&
                        Minigame.getGameConfig().getStartCountdown().getRunCount() == 1) {
                    Minigame.getGameConfig().getStartCountdown().
                            setCountdown(Minigame.getGameConfig().getSkippedStartCountdown());
                }
                return true;
            }
        }
        return false;
    }
}
