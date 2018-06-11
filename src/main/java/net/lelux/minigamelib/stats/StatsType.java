package net.lelux.minigamelib.stats;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Map;

@Getter
public class StatsType {

    private String name;
    private String uniqueName;
    private Map<String, Integer> list;

    public StatsType(String name, String uniqueName) {
        this.name = name;
        this.uniqueName = uniqueName;
    }

    public void set(Player p, int val) {
        list.put(p.getUniqueId().toString(), val);
    }

    public void add(Player p, int val) {
        String uuid = p.getUniqueId().toString();
        list.put(uuid, list.containsKey(uuid) ? list.get(uuid) + val : val);
    }

    public void substract(Player p, int val) {
        add(p, val * -1);
    }
}
