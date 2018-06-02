package net.lelux.minigamelib.timer;

import lombok.Getter;
import lombok.Setter;
import net.lelux.minigamelib.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Countdown {

    private final CountdownEvent event;
    private final int secounds;
    @Getter @Setter private int countdown;
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
            task = Bukkit.getScheduler().runTaskTimer(Minigame.getMinigame(), () -> {
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
            }, 0, 20);
        }
    }

    public int getRunCount() {
        return runCount;
    }

    public boolean isRunning() {
        return task != null;
    }
}
