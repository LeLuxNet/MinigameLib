package net.lelux.minigamelib.stats;

public class GameStats {

    private long deaths;
    private long kills;

    public GameStats() {
        deaths = 0;
        kills = 0;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
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
