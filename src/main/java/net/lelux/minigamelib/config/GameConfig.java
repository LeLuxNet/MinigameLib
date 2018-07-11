package net.lelux.minigamelib.config;

import lombok.Getter;
import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.connections.MySQL;
import net.lelux.minigamelib.connections.Vault;
import net.lelux.minigamelib.timer.Countdown;
import net.lelux.minigamelib.timer.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
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
    private final boolean smoothExp;

    public GameConfig(Minigame minigame, GameMap[] maps, MySQL mySQL, int respawnCount,
                      int startCountdown, int skippedStartCountdown, int stopCountdown,
                      Location lobbyLoc, Location endLoc, boolean smoothExp) {
        this.minigame = minigame;
        this.maps = maps;
        this.mySQL = mySQL;
        this.vault = new Vault();
        this.respawnCount = respawnCount;
        this.startCountdown = new Countdown(startCountdown, () -> GameState.set(GameState.INGAME), smoothExp);
        this.skippedStartCountdown = skippedStartCountdown;
        this.stopCountdown = new Countdown(stopCountdown, () -> Bukkit.getServer().shutdown(), smoothExp);
        this.lobbyLoc = lobbyLoc;
        this.endLoc = endLoc == null ? lobbyLoc : endLoc;
        this.smoothExp = smoothExp;
    }
}
