package net.lelux.minigamelib.shop;

import org.bukkit.entity.Player;

public interface ClickEvent {

    boolean onLeftClick(Player p);

    boolean onRightClick(Player p);
}
