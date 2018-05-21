package net.lelux.minigamelib.player;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.achievements.GameAchievement;
import net.lelux.minigamelib.teams.GameTeam;
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

    private boolean spectator;
    private GameTeam team;
    private int respawnCount;

    private static Map<String, GamePlayer> gamePlayerMap = new HashMap<>();
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

    public boolean isSpectator() {
        return spectator;
    }

    public void setSpectator(boolean val) {
        spectator = val;
        setVisible(!val);
        if (val) {
            Minigame.getScoreboardManager().setScoreTeam(this, "01");
        } else {
            Minigame.getScoreboardManager().setTeam(this, team);
        }
    }

    public void setTeam(GameTeam team) {
        this.team = team;
    }

    public GameTeam getTeam() {
        return team;
    }

    public int getRespawnCount() {
        return respawnCount;
    }

    public void setRespawnCount(int respawnCount) {
        if (respawnCount >= -1) {
            this.respawnCount = respawnCount;
        }
    }

    public boolean hasAchievement(GameAchievement a) {
        ResultSet rs = Minigame.getGameConfig().getMySQL().getResult("SELECT * FROM "
                + a.getGroup() + " WHERE uuid=" + player.getUniqueId().toString());
        try {
            return rs.getBoolean(a.getUniqueName());
        } catch (SQLException e) {

        }
        return true;
    }

    public void addAchievement(GameAchievement a) {
        if (!hasAchievement(a)) {
            Minigame.getGameConfig().getMySQL().update("UPDATE " + a.getGroup() + " SET "
                    + a.getUniqueName() + "=1 WHERE uuid=" + player.getUniqueId().toString());
        }
    }

    public void removeAchievement(GameAchievement a) {
        if (hasAchievement(a)) {
            Minigame.getGameConfig().getMySQL().update("UPDATE " + a.getGroup() + " SET "
                    + a.getUniqueName() + "=0 WHERE uuid=" + player.getUniqueId().toString());
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

    public boolean sellAchievement(GameAchievement a) {
        if (hasAchievement(a) && Minigame.getGameConfig().getVault().isConnected()) {
            Minigame.getGameConfig().getVault().getEco().depositPlayer(player, a.getPrice());
            removeAchievement(a);
            return true;
        }
        return false;
    }

    public static List<GamePlayer> getInvisiblePlayers() {
        return invisiblePlayers;
    }

    public String localizeString(String s) {
        return s.replaceAll("${PLAYER_NAME}", player.getName())
                .replaceAll("${TEAM_NAME}", team.getName())
                .replaceAll("${TEAM_TEXT_COLOR}", team.getTextColor().toString());
    }
}
