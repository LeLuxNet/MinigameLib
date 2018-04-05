package net.lelux.minigamelib.player;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.teams.GameTeam;
import net.lelux.minigamelib.timer.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePlayer {

    private final Player player;

    private boolean spectator;
    private GameTeam team;

    private static Map<String, GamePlayer> gamePlayerMap = new HashMap<>();
    private static List<GamePlayer> invisiblePlayers = new ArrayList<>();

    private GamePlayer(Player player) {
        this.player = player;
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
        if(invisiblePlayers.contains(this) == val) {
            if(val) {
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
        if(val) {
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

    public static List<GamePlayer> getInvisiblePlayers() {
        return invisiblePlayers;
    }
}
