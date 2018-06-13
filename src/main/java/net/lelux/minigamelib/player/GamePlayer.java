package net.lelux.minigamelib.player;

import lombok.Getter;
import lombok.Setter;
import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.achievements.GameAchievement;
import net.lelux.minigamelib.config.GameMap;
import net.lelux.minigamelib.teams.GameTeam;
import net.lelux.minigamelib.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePlayer {

    private final Player player;

    @Getter
    private boolean spectator;

    @Getter
    private int respawnCount;

    @Getter
    @Setter
    private int votes;

    @Getter
    @Setter
    private boolean winner;

    private static Map<String, GamePlayer> gamePlayerMap = new HashMap<>();

    @Getter
    private static List<GamePlayer> invisiblePlayers = new ArrayList<>();

    private GamePlayer(Player player) {
        this.player = player;
        respawnCount = Minigame.getGameConfig().getRespawnCount();
        if (!Minigame.getGameConfig().getVault().getEco().hasAccount(player)) {
            Minigame.getGameConfig().getVault().getEco().createPlayerAccount(player);
        }
    }

    public static GamePlayer toGamePlayer(Player p) {
        if (!gamePlayerMap.containsKey(p.getUniqueId().toString())) {
            gamePlayerMap.put(p.getUniqueId().toString(), new GamePlayer(p));
        }
        return gamePlayerMap.get(p.getUniqueId().toString());
    }

    public Player toPlayer() {
        return player;
    }

    public void setVisible(boolean val) {
        if (invisiblePlayers.contains(this) == val) {
            if (val) {
                invisiblePlayers.remove(this);
                Bukkit.getServer().getOnlinePlayers()
                        .forEach(p -> p.showPlayer(player));
            } else {
                invisiblePlayers.add(this);
                Bukkit.getServer().getOnlinePlayers()
                        .forEach(p -> p.hidePlayer(p));
            }
        }
    }

    public void setSpectator(boolean val) {
        spectator = val;
        setVisible(!val);
        if (val) {
            Minigame.getScoreboardManager().setScoreTeam(this, "01");
        } else {
            Minigame.getScoreboardManager().setTeam(this, getTeam());
        }
    }

    public void setRespawnCount(int respawnCount) {
        if (respawnCount >= -1) {
            this.respawnCount = respawnCount;
        }
    }

    public boolean hasAchievement(GameAchievement a) {
        ResultSet rs = Minigame.getGameConfig().getMySQL().getResult("SELECT * FROM "
                + a.getTableName() + " WHERE uuid=" + player.getUniqueId().toString());
        try {
            return rs.getBoolean("status");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void addAchievement(GameAchievement a) {
        if (!hasAchievement(a)) {
            Minigame.getGameConfig().getMySQL().update("UPDATE " + a.getTableName()
                    + " SET status=1 WHERE uuid=" + player.getUniqueId().toString());
        }
    }

    public void removeAchievement(GameAchievement a) {
        if (hasAchievement(a)) {
            Minigame.getGameConfig().getMySQL().update("UPDATE " + a.getTableName()
                    + " SET status=0 WHERE uuid=" + player.getUniqueId().toString());
        }
    }

    public boolean buyAchievement(GameAchievement a) {
        if (!hasAchievement(a) && Minigame.getGameConfig().getVault().isConnected()
                && Minigame.getGameConfig().getVault().getEco().has(player, a.getPrice())) {
            Minigame.getGameConfig().getVault().getEco().withdrawPlayer(player, a.getPrice());
            addAchievement(a);
            return true;
        }
        return false;
    }

    public int getLeftVotes() {
        int count = 0;
        for (GameMap map : Minigame.getGameConfig().getMaps()) {
            count = map.getVote(player);
        }
        return votes - count;
    }

    public boolean sellAchievement(GameAchievement a) {
        if (hasAchievement(a) && Minigame.getGameConfig().getVault().isConnected()) {
            Minigame.getGameConfig().getVault().getEco().depositPlayer(player, a.getPrice());
            removeAchievement(a);
            return true;
        }
        return false;
    }

    public void setTeam(GameTeam team) {
        TeamManager.setTeam(player, team);
    }

    public GameTeam getTeam() {
        return TeamManager.getTeam(player);
    }

    public String localizeString(String s) {
        return s.replace("${PLAYER_NAME}", player.getName())
                .replace("${TEAM_NAME}", getTeam().getName())
                .replace("${TEAM_TEXT_COLOR}", getTeam().getTextColor().toString())
                .replace("${VOTE_COUNT}", String.valueOf(votes));
    }
}
