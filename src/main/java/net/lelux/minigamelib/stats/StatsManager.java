package net.lelux.minigamelib.stats;

import lombok.Getter;

import java.util.List;

public class StatsManager {

    private List<StatsType> list;
    @Getter
    private boolean active;

    public void registerStatsType(StatsType type) {
        list.add(type);
    }

    public void upload(StatsType type) {
        if (active) {
            // TODO: Upload to MySQL
        }
    }

    public void upload() {
        list.forEach(this::upload);
    }
}
