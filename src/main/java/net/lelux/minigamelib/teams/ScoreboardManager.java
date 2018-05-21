package net.lelux.minigamelib.teams;

import net.lelux.minigamelib.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {

    private final Scoreboard scoreboard;
    private final List<GameTeam> teamList;

    public ScoreboardManager(List<GameTeam> teamList, int teamCount) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        scoreboard.registerNewTeam("01").setPrefix("§8");
        scoreboard.registerNewTeam("02").setPrefix("§0");

        this.teamList = new ArrayList<>();

        for (int i = 0; i < teamCount; i++) {
            this.teamList.add(teamList.get(i));
            Team t = scoreboard.registerNewTeam(String.valueOf(i + 1));
            t.setPrefix(teamList.get(i).getTextColor().toString());
            t.setAllowFriendlyFire(false);
            t.setCanSeeFriendlyInvisibles(true);
        }

        scoreboard.registerNewObjective("sidebar", "dummy");
        scoreboard.getObjective("sidebar").setDisplaySlot(DisplaySlot.SIDEBAR);

        scoreboard.registerNewObjective("player_health", "health");
        scoreboard.getObjective("player_health").setDisplayName(" / 20");
    }

    public List<GameTeam> getTeamList() {
        return teamList;
    }

    public void setScoreTeam(GamePlayer p, String name) {
        scoreboard.getTeam(name).addEntry(p.toPlayer().getName());
        reloadScoreboard();
    }

    public void setTeam(GamePlayer p, GameTeam t) {
        setScoreTeam(p, String.valueOf(teamList.indexOf(t) - 1));
        reloadScoreboard();
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
        if(b) {
            scoreboard.getObjective("player_health").setDisplaySlot(DisplaySlot.BELOW_NAME);
        } else {
            scoreboard.getObjective("player_health").setDisplaySlot(null);
        }
    }
}
