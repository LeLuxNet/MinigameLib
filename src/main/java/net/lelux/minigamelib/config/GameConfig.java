package net.lelux.minigamelib.config;

import net.lelux.minigamelib.Minigame;

public class GameConfig {

    private final Minigame minigame;

    public GameConfig(Minigame minigame) {
        this.minigame = minigame;
    }

    public Minigame getMinigame() {
        return minigame;
    }
}
