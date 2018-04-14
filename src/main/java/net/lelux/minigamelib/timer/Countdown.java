package net.lelux.minigamelib.timer;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.utils.Languages;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Countdown {

    private final Event event;
    private int seconds;
    private BukkitTask task;

    public Countdown(int seconds, Event event) {
        this.seconds = seconds;
        this.event = event;
    }

    public void start() {
        if(task == null) {
            task = Bukkit.getScheduler().runTaskTimer(Minigame.getMinigame(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.getServer().getOnlinePlayers().forEach(p -> p.setLevel(seconds));
                    System.out.println(seconds);
                    if(seconds <= 0) {
                        event.fire();
                        task.cancel();
                    }
                    seconds--;
                }
            }, 0, 20);
        }
    }
}
