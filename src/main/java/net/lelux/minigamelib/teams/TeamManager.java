package net.lelux.minigamelib.teams;

import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class TeamManager {

    private static Map<String, GameTeam> map;

    public static boolean setTeam(Player p, GameTeam t) {
        boolean full = getPlayerCount(t) >= Minigame.getMap().getTeamSize();
        map.put(p.getUniqueId().toString(), t);
        return full;
    }

    public static GameTeam getTeam(Player p) {
        return map.get(p);
    }

    public static void generateRandomTeams() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(!map.containsKey(p.getUniqueId().toString()) && map.get(p.getUniqueId().toString()) == null) {
                int minCount = Integer.MAX_VALUE;
                GameTeam team = null;
                for(GameTeam t : Minigame.getMap().getTeamList()) {
                    if(getPlayerCount(t) < minCount) {
                        minCount = getPlayerCount(t);
                        team = t;
                    }
                }
                map.put(p.getUniqueId().toString(), team);
            }
        }
    }

    public static int getPlayerCount(GameTeam t) {
        int count = 0;
        for(GameTeam team : map.values()) {
            if(team == t) {
                count++;
            }
        }
        return count;
    }

    public static Player[] getPlayers(GameTeam t) {
        Player[] list = new Player[Minigame.getMap().getTeamSize()];
        int id = 0;
        for(String s : map.keySet()) {
            if(map.get(s) == t) {
                list[id] = Bukkit.getPlayer(UUID.fromString(s));
                id++;
                if(id >= Minigame.getMap().getTeamSize()) {
                    break;
                }
            }
        }
        return list;
    }
}