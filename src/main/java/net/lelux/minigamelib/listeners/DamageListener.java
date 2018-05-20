package net.lelux.minigamelib.listeners;

import net.lelux.minigamelib.timer.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!GameState.is(GameState.INGAME)) {
            e.setCancelled(true);
        }
    }
}
