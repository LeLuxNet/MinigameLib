package net.lelux.minigamelib.config;

import lombok.Getter;
import lombok.ToString;
import net.lelux.minigamelib.teams.GameTeam;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class GameMap {

    private final int teamCount;
    private final int teamSize;
    private final String name;
    private final List<GameSpawner> spawners;
    private final GameTeam[] teamList;
    private final Location spectatorSpawn;
    private Map<String, Integer> votes;

    public GameMap(String name, int teamCount, int teamSize,
                   Location spectatorSpawn, GameTeam... teamList) {
        this.name = name;
        this.teamCount = teamCount;
        this.teamSize = teamSize;
        this.teamList = teamList;
        spawners = new ArrayList<>();
        this.spectatorSpawn = spectatorSpawn;
        votes = new HashMap<>();
    }

    public void startAllSpawners() {
        spawners.forEach(GameSpawner::start);
    }

    public void stopAllSpawners() {
        spawners.forEach(GameSpawner::stop);
    }

    public void setVote(Player p, int count) {
        votes.remove(p.getUniqueId().toString());
        if (count > 0) {
            votes.put(p.getUniqueId().toString(), count);
        }
    }

    public int getVote(Player p) {
        if (votes.containsKey(p.getUniqueId().toString())) {
            return votes.get(p.getUniqueId().toString());
        }
        return 0;
    }

    public int getVoteCount() {
        int count = 0;
        for (int i : votes.values()) {
            count += i;
        }
        return count;
    }
}