package net.lelux.minigamelib.achievements;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.player.GamePlayer;
import net.lelux.minigamelib.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GameAchievementGroup {

    private final List<GameAchievement> list;
    private final String name;
    private final String uniqueName;

    public GameAchievementGroup(String name, String uniqueName) {
        list = new ArrayList<>();
        this.name = name;
        this.uniqueName = "a_" + uniqueName;
        Minigame.getGameConfig().getMySQL().update("CREATE TABLE IF NOT EXISTS " + uniqueName
                + " (uuid VARCHAR(36) NOT NULL, PRIMARY KEY (uuid));");
    }

    public void add(GameAchievement a) {
        list.add(a);
        Minigame.getGameConfig().getMySQL().update("ALTER TABLE " + uniqueName + " ADD COLUMN " + a.getUniqueName() + " TINYINT NOT NULL");
    }

    public Inventory build(GamePlayer p) {
        Inventory inv = Bukkit.createInventory(null, MathUtils.calcSize(list.size()), "§r" + name);
        list.forEach(a -> a.build(p.hasAchievement(a)));
        return inv;
    }
}
