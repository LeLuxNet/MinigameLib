package net.lelux.minigamelib.timer;

import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Countdown {

    private final Event event;
    private final int secounds;
    private int countdown;
    private BukkitTask task;

    public Countdown(int seconds, Event event) {
        this.countdown = seconds;
        this.secounds = seconds;
        this.event = event;
    }

    public void start() {
        if (task == null) {
            task = Bukkit.getScheduler().runTaskTimer(Minigame.getMinigame(), new Runnable() {
                @Override
                public void run() {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        p.setLevel(countdown);
                        p.setExp((float) countdown / secounds);
                    }
                    System.out.println(countdown);
                    if (countdown <= 0) {
                        event.fire();
                        task.cancel();
                    }
                    countdown--;
                }
            }, 0, 20);
        }
    }
}
