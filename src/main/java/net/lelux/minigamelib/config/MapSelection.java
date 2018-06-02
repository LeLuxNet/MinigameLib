package net.lelux.minigamelib.config;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.player.GamePlayer;
import net.lelux.minigamelib.shop.ClickEvent;
import net.lelux.minigamelib.utils.ItemBuilder;
import net.lelux.minigamelib.utils.Languages;
import net.lelux.minigamelib.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MapSelection implements ClickEvent, Listener {

    private final String name;

    public MapSelection() {
        name = "§r" + Languages.getString("map_selection");
        Bukkit.getScheduler().runTaskTimer(Minigame.getMinigame(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getOpenInventory().getTitle().equals(name)) {
                    p.openInventory(getInv(p));
                }
            }
        }, 5, 0);
    }

    @Override
    public boolean onLeftClick(Player p) {
        return false;
    }

    @Override
    public boolean onRightClick(Player p) {
        p.openInventory(getInv(p));
        return true;
    }

    private ItemStack getItem(GameMap map, Player p) {
        int voteCount = map.getVoteCount();
        ItemBuilder builder;
        if (voteCount == 0) {
            builder = new ItemBuilder(Material.PAPER);
        } else {
            if (map.getVote(p) != 0) {
                builder = new ItemBuilder(Material.MAP);
            } else {
                builder = new ItemBuilder(Material.EMPTY_MAP);
            }
            builder.setAmount(map.getVoteCount());
        }
        return builder.setName(map.getTeamCount() + "x" + map.getTeamSize() + " " +
                map.getName()).addLoreLine(voteCount + " " + Languages
                .getString(voteCount == 0 ? "vote" : "vote_pl")).build();
    }

    private Inventory getInv(Player p) {
        Inventory inv = Bukkit.createInventory(null, MathUtils
                .calcSize(Minigame.getGameConfig().getMaps().length), name);
        for (int i = 0; i < Minigame.getGameConfig().getMaps().length; i++) {
            inv.setItem(i, getItem(Minigame.getGameConfig().getMaps()[i], p));
        }
        return inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals(name)) {
            Player p = (Player) e.getWhoClicked();
            GamePlayer gp = GamePlayer.toGamePlayer(p);
            e.setCancelled(true);
            if (e.getRawSlot() > Minigame.getGameConfig().getMaps().length) {
                GameMap map = Minigame.getGameConfig().getMaps()[e.getRawSlot()];
                if (e.isLeftClick()) {
                    if (gp.getLeftVotes() > 0) {
                        map.setVote(p, map.getVote(p) +
                                (e.isShiftClick() ? gp.getLeftVotes() : 1));
                    }
                } else if (e.isRightClick()) {
                    if (map.getVote(p) > 0) {
                        map.setVote(p, e.isShiftClick() ? 0 : map.getVote(p) - 1);
                    }
                }
                p.openInventory(getInv(p));
            }
        }
    }
}
