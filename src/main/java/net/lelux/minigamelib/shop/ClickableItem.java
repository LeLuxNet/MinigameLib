package net.lelux.minigamelib.shop;

import de.tr7zw.itemnbtapi.NBTItem;
import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class ClickableItem implements Buyable {

    private static Map<Integer, ClickableItem> map = new HashMap<>();
    private static int nextId = 0;
    private final ItemStack item;
    private final int id;
    private final ItemStack cooldownItem;
    private final int cooldown;
    private final ClickEvent clickEvent;
    private BukkitTask task;
    private int cooldownCount;

    public ClickableItem(ItemStack item, ItemStack cooldownItem, int cooldown, ClickEvent clickEvent) {
        this.id = nextId++;
        NBTItem nbti = new NBTItem(item);
        nbti.setInteger("minigamelib_clickableitem_id", id);
        this.item = nbti.getItem();
        this.cooldown = cooldown;
        this.cooldownItem = cooldownItem;
        this.clickEvent = clickEvent;
        map.put(id, this);
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemStack getCooldownItem() {
        return cooldownItem;
    }

    private void startCooldown(Player p, int slot) {
        if (cooldownCount <= 0) {
            cooldownCount = cooldown;
            task = Bukkit.getScheduler().runTaskTimer(Minigame.getMinigame(), new Runnable() {

                @Override
                public void run() {
                    if (cooldownCount <= 0) {
                        p.getInventory().setItem(slot, item);
                        task.cancel();
                    } else {
                        cooldownItem.setAmount(cooldownCount);
                        p.getInventory().setItem(slot, cooldownItem);
                        cooldownCount--;
                    }
                }

            }, 0, 20);
        }
    }

    public static ClickableItem toClickableItem(int id) {
        if(map.containsKey(id)) {
            return map.get(id);
        }
        return null;
    }

    public void fireLeftClick(Player p) {
        if(p.getInventory().getItemInHand().equals(item)) {
            clickEvent.onLeftClick();
            startCooldown(p, p.getInventory().getHeldItemSlot());
        }
    }

    public void fireRightClick(Player p) {
        if(p.getInventory().getItemInHand().equals(item)) {
            clickEvent.onRightClick();
            startCooldown(p, p.getInventory().getHeldItemSlot());
        }
    }
}
