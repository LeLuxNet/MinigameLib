package net.lelux.minigamelib;

import net.lelux.minigamelib.config.GameConfig;
import net.lelux.minigamelib.teams.ScoreboardManager;
import net.lelux.minigamelib.timer.GameState;
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
}
