package net.lelux.minigamelib.achievements;

import lombok.Getter;
import lombok.ToString;
import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.player.GamePlayer;
import net.lelux.minigamelib.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class GameAchievementGroup {

    private final List<GameAchievement> list;
    private final String name;
    private final String uniqueName;

    public GameAchievementGroup(String name, String uniqueName) {
        list = new ArrayList<>();
        this.name = name;
        this.uniqueName = "a_" + uniqueName;
    }

    public void add(GameAchievement a) {
        list.add(a);
        Minigame.getGameConfig().getMySQL().update("CREATE TABLE IF NOT EXISTS " + a.getTableName()
                + " (uuid VARCHAR(36) NOT NULL, status TINYINT NOT NULL, PRIMARY KEY (uuid));");
    }

    public Inventory build(GamePlayer p) {
        Inventory inv = Bukkit.createInventory(null, MathUtils.calcSize(list.size()), "§r" + name);
        list.forEach(a -> a.build(p.hasAchievement(a)));
        return inv;
    }
}
