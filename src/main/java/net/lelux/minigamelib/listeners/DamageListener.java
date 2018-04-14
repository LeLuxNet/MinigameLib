package net.lelux.minigamelib.listeners;

import net.lelux.minigamelib.timer.GameState;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(!GameState.is(GameState.INGAME)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if(e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) && e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1, 0);
        }
    }
}
