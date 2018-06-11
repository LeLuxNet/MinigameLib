package net.lelux.minigamelib.achievements;

import lombok.Getter;
import lombok.ToString;
import net.lelux.minigamelib.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class GameAchievement {

    private final String name;
    private final String uniqueName;
    private final GameAchievementGroup group;
    private final Material hasMaterial;
    private final Material hasntMaterial;
    private final double price;
    private final List<String> description;

    public GameAchievement(String name, String uniqueName, GameAchievementGroup group,
                           Material hasMaterial, Material hasntMaterial, double price) {
        this.name = name;
        this.uniqueName = uniqueName;
        this.group = group;
        group.add(this);
        this.hasMaterial = hasMaterial;
        this.hasntMaterial = hasntMaterial;
        this.price = price;
        description = new ArrayList<>();
    }

    public GameAchievement(String name, String uniqueName, GameAchievementGroup group,
                           Material hasMaterial, Material hasntMaterial) {
        this(name, uniqueName, group, hasMaterial, hasntMaterial, 0);
    }

    public GameAchievement(String name, String uniqueName, GameAchievementGroup group,
                           Material material, double price) {
        this(name, uniqueName, group, material, material, price);
    }

    public GameAchievement(String name, String uniqueName, GameAchievementGroup group, Material material) {
        this(name, uniqueName, group, material, material);
    }

    public GameAchievement(String name, String uniqueName, GameAchievementGroup group, double price) {
        this(name, uniqueName, group, Material.EMPTY_MAP, Material.PAPER, price);
    }

    public GameAchievement(String name, String uniqueName, GameAchievementGroup group) {
        this(name, uniqueName, group, 0);
    }

    public ItemStack build(boolean has) {
        return new ItemBuilder(has ? hasMaterial : hasntMaterial)
                .setName(name)
                .setLore(description)
                .build();
    }

    public String getTableName() {
        return "a_" + getGroup().getUniqueName() + "_" + uniqueName;
    }
}