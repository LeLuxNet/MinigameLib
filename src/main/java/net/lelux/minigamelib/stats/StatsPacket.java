package net.lelux.minigamelib.stats;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StatsPacket {

    private long deaths;
    private long kills;

    StatsPacket() {
        deaths = 0;
        kills = 0;
    }

    public void addKill() {
        kills++;
    }

    public void addDeath() {
        deaths++;
    }

    public double getKD() {
        return kills / deaths;
    }
}
