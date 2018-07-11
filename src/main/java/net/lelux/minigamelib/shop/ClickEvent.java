package net.lelux.minigamelib.shop;

import org.bukkit.entity.Player;

public interface ClickEvent {

    boolean onClick(Player p, ClickType type);

    enum ClickType {
        RIGHT,
        LEFT
    }
}
