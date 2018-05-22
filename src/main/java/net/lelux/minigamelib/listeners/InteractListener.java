package net.lelux.minigamelib.listeners;

import de.tr7zw.itemnbtapi.NBTItem;
import net.lelux.minigamelib.shop.ClickableItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getItem() != null) {
            NBTItem nbti = new NBTItem(e.getItem());
            if (nbti.hasKey("minigamelib_clickableitem_id")) {
                ClickableItem clicki = ClickableItem.toClickableItem(
                        nbti.getInteger("minigamelib_clickableitem_id"));
                if (e.getAction() != Action.PHYSICAL) {
                    clicki.fireClick(e.getAction() == Action.LEFT_CLICK_AIR ||
                            e.getAction() == Action.LEFT_CLICK_BLOCK, e.getPlayer());
                    e.setCancelled(true);
                }
            }
        }
    }
}
