package net.lelux.minigamelib.timer;

import lombok.Getter;
import lombok.Setter;
import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Countdown {

    private final CountdownEvent event;
    private final int seconds;
    @Getter
    @Setter
    private int countdown;
    private BukkitTask task;
    private int runCount;
    private int ticks;
    @Getter
    private final boolean smooth;

    public Countdown(int seconds, CountdownEvent event, boolean smooth) {
        this.countdown = seconds;
        this.seconds = seconds;
        this.event = event;
        this.runCount = 0;
        this.smooth = smooth;
        ticks = 0;
    }

    public void start() {
        if (!isRunning()) {
            runCount++;
            task = Bukkit.getScheduler().runTaskTimer(Minigame.getMinigame(), () -> {
                ticks++;
                Bukkit.getOnlinePlayers().forEach(p -> p.setExp((float) seconds / (float) countdown + (smooth ? ticks / 20F : 0)));
                if (ticks >= 20) {
                    Bukkit.getOnlinePlayers().forEach(p -> p.setLevel(countdown));
                    if (countdown <= 0) {
                        event.fire();
                        task.cancel();
                    }
                    countdown--;
                    ticks = 0;
                }
            }, 0, 1);
        }
    }

    public int getRunCount() {
        return runCount;
    }

    public boolean isRunning() {
        return task != null;
    }
}
