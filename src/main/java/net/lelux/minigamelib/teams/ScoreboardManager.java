package net.lelux.minigamelib.teams;

import net.lelux.minigamelib.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {

    private final Scoreboard scoreboard;
    private final GameTeam[] teamList;

    public ScoreboardManager(GameTeam[] teamList) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        scoreboard.registerNewTeam("01").setPrefix("§8");
        scoreboard.registerNewTeam("02").setPrefix("§0");

        this.teamList = teamList;

        for (int i = 0; i < teamList.length; i++) {
            Team t = scoreboard.registerNewTeam(String.valueOf(i + 1));
            t.setPrefix(teamList[i].getTextColor().toString());
            t.setAllowFriendlyFire(false);
            t.setCanSeeFriendlyInvisibles(true);
        }

        scoreboard.registerNewObjective("sidebar", "dummy");
        scoreboard.getObjective("sidebar").setDisplaySlot(DisplaySlot.SIDEBAR);

        scoreboard.registerNewObjective("player_health", "health");
        scoreboard.getObjective("player_health").setDisplayName(" / 20");
    }

    public GameTeam[] getTeamList() {
        return teamList;
    }

    public void setScoreTeam(GamePlayer p, String name) {
        scoreboard.getTeam(name).addEntry(p.toPlayer().getName());
        reloadScoreboard();
    }

    public void setTeam(GamePlayer p, GameTeam t) {
        for (int i = 0; i < teamList.length; i++) {
            if (teamList[i].equals(t)) {
                setScoreTeam(p, String.valueOf(i));
                reloadScoreboard();
                return;
            }
        }
    }

    public void reloadScoreboard() {
        Bukkit.getServer().getOnlinePlayers().forEach(p -> p.setScoreboard(scoreboard));
    }

    public void setSideboard(String... line) {
        scoreboard.getObjective("sidebar").unregister();
        scoreboard.registerNewObjective("sidebar", "dummy");
        scoreboard.getObjective("sidebar").setDisplaySlot(DisplaySlot.SIDEBAR);
        for (Player p : Bukkit.getOnlinePlayers()) {
            GamePlayer gp = GamePlayer.toGamePlayer(p);
            for (int i = 0; i < line.length; i++) {
                String s = gp.localizeString(line[i]);
                scoreboard.getObjective("sidebar").getScore(s).setScore(line.length - i);
            }
        }
    }

    public void showPlayerHealth(boolean b) {
        if (b) {
            scoreboard.getObjective("player_health").setDisplaySlot(DisplaySlot.BELOW_NAME);
        } else {
            scoreboard.getObjective("player_health").setDisplaySlot(null);
        }
    }
}
