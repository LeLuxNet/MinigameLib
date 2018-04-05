package net.lelux.minigamelib.timer;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.utils.Languages;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Countdown {

    private final long ticks;
    private final Event event;
    private int countdown;
    private BukkitTask task;

    public Countdown(int secounds, Event event) {
        ticks = secounds * 20;
        countdown = secounds;
        this.event = event;
    }

    public void start() {
        if(task == null) {
            task = Bukkit.getScheduler().runTaskTimer(Minigame.getMinigame(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.getServer().getOnlinePlayers().forEach(p -> p.setLevel(countdown));
                    if(countdown <= 0) {
                        event.fire();
                        task.cancel();
                    }
                    countdown--;
                }
            }, 0, ticks);
        }
    }
}
