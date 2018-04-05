package net.lelux.minigamelib.config;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.connections.MySQL;
import net.lelux.minigamelib.connections.Vault;

public class GameConfig {

    private final Minigame minigame;
    private final GameMap map;
    private final MySQL mySQL;
    private final Vault vault;

    public GameConfig(Minigame minigame, GameMap map, MySQL mySQL) {
        this.minigame = minigame;
        this.map = map;
        this.mySQL = mySQL;
        this.vault = new Vault();
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
}
