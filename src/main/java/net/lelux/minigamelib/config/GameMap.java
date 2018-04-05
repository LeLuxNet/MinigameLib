package net.lelux.minigamelib.config;

import net.lelux.minigamelib.teams.GameTeam;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private final int teamCount;
    private final int teamSize;
    private final String name;
    private final List<GameSpawner> spawners;
    private final List<GameTeam> teamList;
    private final Location lobbySpawn;
    private final Location spectatorSpawn;
    private final Location endSpawn;

    public GameMap(String name, int teamCount, int teamSize, List<GameTeam> teamList,
                   Location lobbySpawn, Location spectatorSpawn, Location endSpawn) {
        this.name = name;
        this.teamCount = teamCount;
        this.teamSize = teamSize;
        this.teamList = teamList;
        spawners = new ArrayList<>();
        this.lobbySpawn = lobbySpawn;
        this.spectatorSpawn = spectatorSpawn;
        this.endSpawn = endSpawn == null ? lobbySpawn : endSpawn;
    }

    public String getName() {
        return name;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public String toString() {
        return teamCount + "x" + teamSize + "," + name + "," + spawners;
    }

    public List<GameSpawner> getSpawners() {
        return spawners;
    }

    public void startAllSpawners() {
        spawners.forEach(GameSpawner::start);
    }

    public void stopAllSpawners() {
        spawners.forEach(GameSpawner::stop);
    }

    public List<GameTeam> getTeamList() {
        return teamList;
    }

    public Location getLobbySpawn() {
        return lobbySpawn;
    }

    public Location getSpectatorSpawn() {
        return spectatorSpawn;
    }

    public Location getEndSpawn() {
        return endSpawn;
    }
}