package net.lelux.minigamelib.config;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.connections.MySQL;
import net.lelux.minigamelib.connections.Vault;
import net.lelux.minigamelib.timer.Countdown;
import net.lelux.minigamelib.timer.CountdownEvent;
import net.lelux.minigamelib.timer.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class GameConfig {

    private final Minigame minigame;
    private final GameMap[] maps;
    private final MySQL mySQL;
    private final Vault vault;
    private final int respawnCount;
    private final Countdown startCountdown;
    private final int skippedStartCountdown;
    private final Countdown stopCountdown;
    private final Location lobbyLoc;
    private final Location endLoc;

    public GameConfig(Minigame minigame, GameMap[] maps, MySQL mySQL, int respawnCount,
                      int startCountdown, int skippedStartCountdown, int stopCountdown,
                      Location lobbyLoc, Location endLoc) {
        this.minigame = minigame;
        this.maps = maps;
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
        this.lobbyLoc = lobbyLoc;
        this.endLoc = endLoc;
    }

    public Minigame getMinigame() {
        return minigame;
    }

    public GameMap[] getMaps() {
        return maps;
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

    public Location getLobbyLoc() {
        return lobbyLoc;
    }

    public Location getEndLoc() {
        return endLoc;
    }
}
