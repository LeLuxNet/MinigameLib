package net.lelux.minigamelib.listeners;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.player.GamePlayer;
import net.lelux.minigamelib.timer.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        GamePlayer gp = GamePlayer.toGamePlayer(e.getPlayer());
        if(GameState.is(GameState.LOBBY)) {
            gp.setVisible(true);
        } else {
            gp.setSpectator(true);
        }
        GamePlayer.getInvisiblePlayers().forEach(p ->
                gp.toPlayer().hidePlayer(p.toPlayer()));
        Minigame.getScoreboardManager().reloadScoreboard();
    }
}