package net.lelux.minigamelib.teams;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;

public class GameTeam {

    private final String name;
    private final ChatColor textColor;
    private final DyeColor blockColor;
    private final Location spawn;

    public GameTeam(String name, ChatColor textColor, DyeColor blockColor, Location spawn) {
        this.name = name;
        this.textColor = textColor;
        this.blockColor = blockColor;
        this.spawn = spawn;
    }

    public String getName() {
        return name;
    }

    public ChatColor getTextColor() {
        return textColor;
    }

    public DyeColor getBlockColor() {
        return blockColor;
    }

    public Location getSpawn() {
        return spawn;
    }
}
