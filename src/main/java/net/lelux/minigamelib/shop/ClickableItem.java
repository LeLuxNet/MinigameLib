package net.lelux.minigamelib.shop;

import de.tr7zw.itemnbtapi.NBTItem;
import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class ClickableItem implements ToItemConvertable {

    private static Map<Integer, ClickableItem> map = new HashMap<>();
    private static int nextId = 0;
    private final ItemStack item;
    private final int id;
    private final ItemStack cooldownItem;
    private final int cooldown;
    private final ClickEvent event;
    private BukkitTask task;
    private int cooldownCount;

    public ClickableItem(ItemStack item, ItemStack cooldownItem, int cooldown, ClickEvent event) {
        this.id = nextId++;
        NBTItem nbti = new NBTItem(item);
        nbti.setInteger("minigamelib_clickableitem_id", id);
        this.item = nbti.getItem();
        this.cooldown = cooldown;
        this.cooldownItem = cooldownItem;
        this.event = event;
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

    public void fireClick(boolean button, Player p) {
        if(p.getInventory().getItemInHand().equals(item)) {
            if(button ? event.onLeftClick(p) : event.onRightClick(p)) {
                startCooldown(p, p.getInventory().getHeldItemSlot());
            }
        }
    }
}
