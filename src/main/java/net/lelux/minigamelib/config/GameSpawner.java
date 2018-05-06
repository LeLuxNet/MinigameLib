package net.lelux.minigamelib.config;

import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class GameSpawner {

    private final Location loc;
    private final ItemStack drop;
    private final long ticks;
    private BukkitTask task;

    public GameSpawner(Location loc, ItemStack drop, long ticks) {
        this.loc = loc;
        this.drop = drop;
        this.ticks = ticks;
    }

    public boolean isActive() {
        return task != null;
    }

    public void start() {
        if (!isActive()) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(Minigame.getMinigame(), new Runnable() {
                @Override
                public void run() {
                    // TODO: Summon 'drop'-Item
                }
            }, 0, ticks);
        }
    }

    public void stop() {
        if (isActive()) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public String toString() {
        return loc + "," + drop + "," + ticks;
    }
}