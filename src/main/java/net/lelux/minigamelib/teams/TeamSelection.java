package net.lelux.minigamelib.teams;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.config.GameMap;
import net.lelux.minigamelib.utils.ItemBuilder;
import net.lelux.minigamelib.utils.Languages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class TeamSelection implements Listener {

    private final String name;

    public TeamSelection() {
        name = "§r" + Languages.getString("team_selection");
        Bukkit.getPluginManager().registerEvents(this, Minigame.getMinigame());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory().getTitle().equals(name)) {
            if(e.getRawSlot() <= Minigame.getMap().getTeamList().length) {
                GameTeam t = null;
                if(e.getRawSlot() < Minigame.getMap().getTeamList().length) {
                    t = Minigame.getMap().getTeamList()[e.getRawSlot()];
                }
                if(TeamManager.setTeam(p, t)) {
                    p.sendMessage(Languages.getString("join_team", t.getName()));
                } else {
                    p.sendMessage(Languages.getString("team_full", t.getName()));
                }
            }
        }
    }

    public Inventory getInv(Player p) {
        Inventory inv = Bukkit.createInventory(null, Minigame.getMap()
                .getTeamList().length + 1, name);
        for(GameTeam t : Minigame.getMap().getTeamList()) {
            System.out.println(t.getBlockColor().ordinal());
            System.out.println(t.getBlockColor().getDyeData());
            System.out.println(t.getBlockColor().getWoolData());
            ItemBuilder item = new ItemBuilder(Material.WOOL, 1, t.getBlockColor().getWoolData())
                    .setName(t.getTextColor() + "§l" + t.getName());
            for(Player pl : TeamManager.getPlayers(t)) {
                item.addLoreLine(t.getTextColor() + (pl == null ? "-" : pl.getName()));
            }
            if(t == TeamManager.getTeam(p)) {
                item.addEnchantment(Enchantment.LUCK, 1).removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        return inv;
    }
}
