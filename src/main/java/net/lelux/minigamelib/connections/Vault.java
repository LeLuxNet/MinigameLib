package net.lelux.minigamelib.connections;

import net.lelux.minigamelib.Minigame;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault extends Connection<Economy> {

    public Vault() {
        super("Vault");
    }

    public boolean forceConnect() {
        RegisteredServiceProvider<Economy> rsp = Minigame.getMinigame().getServer()
                .getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            con = rsp.getProvider();
        }
        return isConnected();
    }

    public boolean forceDisconnect() {
        con = null;
        return true;
    }

    public Economy getEco() {
        return con;
    }
}
