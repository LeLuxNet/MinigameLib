package net.lelux.minigamelib.stats;

import lombok.Getter;
import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

@Getter
public class StatsType {

    private String name;
    private String uniqueName;
    private Map<String, Integer> list;

    public StatsType(String name, String uniqueName) {
        this.name = name;
        this.uniqueName = uniqueName;
        Minigame.getGameConfig().getMySQL().update("CREATE TABLE IF NOT EXISTS s_" + uniqueName +
                "(uuid VARCHAR(36) NOT NULL, status INT NOT NULL, PRIMARY KEY (uuid));");
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

    public int getLocalStats(Player p) {
        return list.get(p.getUniqueId().toString());
    }

    public int getDbStats(Player p) {
        ResultSet rs = Minigame.getGameConfig().getMySQL().getResult("SELECT * FROM s_" +
                uniqueName + " WHERE uuid=" + p.getUniqueId().toString());
        try {
            return rs.getInt("status");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getStats(Player p) {
        return getDbStats(p) + getLocalStats(p);
    }

    void upload() {
        for (String s : list.keySet()) {
            Player p = Bukkit.getPlayer(UUID.fromString(s));
            Minigame.getGameConfig().getMySQL().update("UPDATE s_" + uniqueName + " SET status=" +
                    getStats(p) + " WHERE uuid=" + s);
        }
    }
}
