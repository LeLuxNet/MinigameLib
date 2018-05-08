package net.lelux.minigamelib.shop;

import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class ClickableItem implements Listener, Buyable {

    private final ItemStack item;
    private final ItemStack cooldownItem;
    private final int cooldown;
    private final ClickEvent clickEvent;
    private BukkitTask task;
    private int cooldownCount;

    public ClickableItem(ItemStack item, ItemStack cooldownItem, int cooldown, ClickEvent clickEvent) {
        this.item = item;
        this.cooldown = cooldown;
        this.cooldownItem = cooldownItem;
        this.clickEvent = clickEvent;
        Bukkit.getServer().getPluginManager().registerEvents(this, Minigame.getMinigame());
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemStack getCooldownItem() {
        return cooldownItem;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem().equals(item) && cooldownCount <= 0) {
            if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (clickEvent.onLeftClick()) {
                    startCooldown(e.getPlayer(), e.getPlayer().getInventory().getHeldItemSlot());
                }
            } else if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (clickEvent.onRightClick()) {
                    startCooldown(e.getPlayer(), e.getPlayer().getInventory().getHeldItemSlot());
                }
            }
        }
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
}
