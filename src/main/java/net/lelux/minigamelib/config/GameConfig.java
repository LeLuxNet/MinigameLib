package net.lelux.minigamelib.config;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.connections.MySQL;
import net.lelux.minigamelib.connections.Vault;
import net.lelux.minigamelib.timer.Countdown;
import net.lelux.minigamelib.timer.CountdownEvent;
import net.lelux.minigamelib.timer.GameState;
import org.bukkit.Bukkit;

public class GameConfig {

    private final Minigame minigame;
    private final GameMap map;
    private final MySQL mySQL;
    private final Vault vault;
    private final int respawnCount;
    private final Countdown startCountdown;
    private final int skippedStartCountdown;
    private final Countdown stopCountdown;

    public GameConfig(Minigame minigame, GameMap map, MySQL mySQL, int respawnCount,
                      int startCountdown, int skippedStartCountdown, int stopCountdown) {
        this.minigame = minigame;
        this.map = map;
        this.mySQL = mySQL;
        this.vault = new Vault();
        this.respawnCount = respawnCount;
        this.startCountdown = new Countdown(startCountdown, new CountdownEvent() {
            @Override
            public void fire() {
                GameState.set(GameState.INGAME);
            }
        });
        this.skippedStartCountdown = skippedStartCountdown;
        this.stopCountdown = new Countdown(stopCountdown, new CountdownEvent() {
            @Override
            public void fire() {
                Bukkit.getServer().shutdown();
            }
        });
    }

    public Minigame getMinigame() {
        return minigame;
    }

    public GameMap getMap() {
        return map;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public Vault getVault() {
        return vault;
    }

    public int getRespawnCount() {
        return respawnCount;
    }

    public Countdown getStartCountdown() {
        return startCountdown;
    }

    public Countdown getStopCountdown() {
        return stopCountdown;
    }

    public int getSkippedStartCountdown() {
        return skippedStartCountdown;
    }
}
