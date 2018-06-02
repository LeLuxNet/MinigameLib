package net.lelux.minigamelib.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

@ToString
@AllArgsConstructor
public class GameSpawner {

    @Getter private final Location loc;
    @Getter private final ItemStack drop;
    @Getter private final long ticks;
    private BukkitTask task;

    public boolean isActive() {
        return task != null;
    }

    public void start() {
        if (!isActive()) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(Minigame.getMinigame(), () -> {
                // TODO: Summon 'drop'-Item
            }, 0, ticks);
        }
    }

    public void stop() {
        if (isActive()) {
            task.cancel();
            task = null;
        }
    }
}