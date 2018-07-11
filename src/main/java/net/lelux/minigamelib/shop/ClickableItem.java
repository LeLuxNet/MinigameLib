package net.lelux.minigamelib.shop;

import lombok.Getter;
import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class ClickableItem implements ItemConvertable {

    private static Map<Integer, ClickableItem> map = new HashMap<>();
    private static int nextId = 0;
    @Getter
    private final ItemStack item;
    private final int id;
    @Getter
    private final ItemStack cooldownItem;
    private final int cooldown;
    private final ClickEvent event;
    private BukkitTask task;
    private int cooldownCount;

    public ClickableItem(ItemStack item, ItemStack cooldownItem, int cooldown, ClickEvent event) {
        this.id = nextId++;
        this.item = new ItemBuilder(item)
                .setNBTInteger("minigamelib_clickableitem_id", id).build();
        this.cooldown = cooldown;
        this.cooldownItem = cooldownItem == null ? item : cooldownItem;
        this.event = event;
        map.put(id, this);
    }

    public ClickableItem(ItemStack item, ClickEvent event) {
        this(item, null, 0, event);
    }

    private void startCooldown(Player p, int slot) {
        if (cooldown > 0 && cooldownCount <= 0) {
            cooldownCount = cooldown;
            task = Bukkit.getScheduler().runTaskTimer(Minigame.getMinigame(), () -> {
                if (cooldownCount <= 0) {
                    p.getInventory().setItem(slot, item);
                    task.cancel();
                } else {
                    cooldownItem.setAmount(cooldownCount);
                    p.getInventory().setItem(slot, cooldownItem);
                    cooldownCount--;
                }
            }, 0, 20);
        }
    }

    public static ClickableItem toClickableItem(int id) {
        if (map.containsKey(id)) {
            return map.get(id);
        }
        return null;
    }

    public void fireClick(ClickEvent.ClickType type, Player p) {
        if (p.getInventory().getItemInHand().equals(item)) {
            if (event.onClick(p, type)) {
                startCooldown(p, p.getInventory().getHeldItemSlot());
            }
        }
    }
}
