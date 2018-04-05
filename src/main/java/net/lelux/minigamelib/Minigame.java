package net.lelux.minigamelib;

import net.lelux.minigamelib.config.GameConfig;
import net.lelux.minigamelib.player.GamePlayer;
import net.lelux.minigamelib.teams.ScoreboardManager;
import net.lelux.minigamelib.timer.GameState;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Minigame extends JavaPlugin {

    private static GameConfig config;
    private static ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        GameState.set(GameState.LOBBY);

        config = onStart();

        scoreboardManager = new ScoreboardManager(config.getMap().getTeamList(), config.getMap().getTeamCount());
    }

    @Override
    public void onDisable() {
        onStop();
    }

    abstract GameConfig onStart();

    abstract void onStop();

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
}
