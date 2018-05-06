package net.lelux.minigamelib.stats;

import net.lelux.minigamelib.player.GamePlayer;

import java.util.HashMap;
import java.util.Map;

public class StatsManager {

    private boolean active;
    private Map<GamePlayer, StatsPacket> packets;

    public void upload() {
        if (active) {
            // TODO: Upload packets to MySQL
        }
    }

    public void resetManager() {
        packets = new HashMap<>();
    }

    public void resetManager(GamePlayer p) {
        if (packets != null && packets.containsKey(p)) {
            packets.remove(p);
        }
    }

    public StatsPacket getStats(GamePlayer p) {
        if (!packets.containsKey(p)) {
            packets.put(p, new StatsPacket());
        }
        return packets.get(p);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
