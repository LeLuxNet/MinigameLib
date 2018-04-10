package net.lelux.minigamelib.stats;

import net.lelux.minigamelib.player.GamePlayer;

import java.util.HashMap;
import java.util.Map;

public class StatsManager {

    private Map<GamePlayer, GameStats> stats;

    public void upload() {
        // TODO: Upload stats to MySQL
    }

    public void resetManager() {
        stats = new HashMap<>();
    }

    public void resetManager(GamePlayer p) {
        if(stats != null && stats.containsKey(p)) {
            stats.remove(p);
        }
    }
}
