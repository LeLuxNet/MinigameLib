package net.lelux.minigamelib.teams;

import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class TeamManager {

    private static Map<String, GameTeam> map;

    public static void setTeam(Player p, GameTeam t) {
        map.put(p.getUniqueId().toString(), t);
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
        return map.values().size();
    }
}
