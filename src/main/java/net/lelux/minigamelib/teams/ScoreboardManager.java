package net.lelux.minigamelib.teams;

import net.lelux.minigamelib.player.GamePlayer;
import org.bukkit.Bukkit;
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
}
