package net.lelux.minigamelib.listeners;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.player.GamePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        GamePlayer p = GamePlayer.toGamePlayer(e.getPlayer());
        p.setRespawnCount(subtractRespawnCount(p.getRespawnCount()));
        if (p.getRespawnCount() == 0 || p.isSpectator()) {
            p.setSpectator(true);
            e.setRespawnLocation(Minigame.getMap().getSpectatorSpawn());
        } else {
            e.setRespawnLocation(p.getTeam().getSpawn());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            GamePlayer p = GamePlayer.toGamePlayer(e.getEntity().getKiller());
        }
    }

    private int subtractRespawnCount(int val) {
        if (val < 0) {
            return val - 1;
        }
        return val;
    }
}
