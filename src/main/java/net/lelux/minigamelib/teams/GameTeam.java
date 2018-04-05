package net.lelux.minigamelib.teams;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public class GameTeam {

    private final String name;
    private final ChatColor textColor;
    private final DyeColor blockColor;

    public GameTeam(String name, ChatColor textColor, DyeColor blockColor) {
        this.name = name;
        this.textColor = textColor;
        this.blockColor = blockColor;
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
}
