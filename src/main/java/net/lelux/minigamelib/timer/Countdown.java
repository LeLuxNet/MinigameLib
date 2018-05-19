package net.lelux.minigamelib.timer;

import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Countdown {

    private final CountdownEvent event;
    private final int secounds;
    private int countdown;
    private BukkitTask task;
    private int runCount;

    public Countdown(int seconds, CountdownEvent event) {
        this.countdown = seconds;
        this.secounds = seconds;
        this.event = event;
        this.runCount = 0;
    }

    public void start() {
        if (!isRunning()) {
            runCount++;
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

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public int getRunCount() {
        return runCount;
    }

    public boolean isRunning() {
        return task != null;
    }
}
