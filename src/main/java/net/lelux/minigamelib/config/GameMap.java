package net.lelux.minigamelib.config;

import net.lelux.minigamelib.teams.GameTeam;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private final int teamCount;
    private final int teamSize;
    private final String name;
    private final List<GameSpawner> spawners;
    private final List<GameTeam> teamList;

    public GameMap(String name, int teamCount, int teamSize, List<GameTeam> teamList) {
        this.name = name;
        this.teamCount = teamCount;
        this.teamSize = teamSize;
        spawners = new ArrayList<>();
        if(teamList == null) {
            this.teamList = new ArrayList<>();
            this.teamList.add(new GameTeam("Red", ChatColor.RED, DyeColor.RED));
            this.teamList.add(new GameTeam("Blue", ChatColor.BLUE, DyeColor.BLUE));
            this.teamList.add(new GameTeam("Yellow", ChatColor.YELLOW, DyeColor.YELLOW));
            this.teamList.add(new GameTeam("Green", ChatColor.GREEN, DyeColor.GREEN));
        } else {
            this.teamList = teamList;
        }
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
}