package net.lelux.minigamelib.shop;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.player.GamePlayer;
import net.lelux.minigamelib.utils.ChestUtils;
import net.lelux.minigamelib.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop implements Listener {

    private final String name;
    private final List<ShopCategory> categories;
    private final Map<String, Integer> selectedCategories;
    private int categoryInvSize;
    private int itemsInvSize;

    public Shop(String name) {
        this.name = "§r" + name;
        categories = new ArrayList<>();
        selectedCategories = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, Minigame.getMinigame());
    }

    public void addCategory(ShopCategory category) {
        categories.add(category);
    }

    private void calcInvSize() {
        categoryInvSize = ChestUtils.calcSize(categories.size());
        itemsInvSize = 0;
        for (ShopCategory sc : categories) {
            if (sc.items.size() > itemsInvSize) {
                itemsInvSize = sc.items.size();
            }
        }
        itemsInvSize = ChestUtils.calcSize(itemsInvSize);
    }

    private Inventory build(Player p) {
        calcInvSize();
        Inventory inv = Bukkit.createInventory(null, categoryInvSize + itemsInvSize, name);
        for (ShopItem si : categories.get(selectedCategories.get(p.getUniqueId().toString())).items) {
            inv.addItem(si.getWatchItem());
        }
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).equals(categories.get(selectedCategories.get(p.getUniqueId().toString())))) {
                inv.setItem(i + itemsInvSize, new ItemBuilder(categories.get(i)
                        .getWatchItem()).addEnchantment(Enchantment.LUCK, 1).build());
            }
            inv.setItem(i + itemsInvSize, categories.get(i).getWatchItem());
        }
        return inv;
    }

    public void open(Player p, int category) {
        if (selectedCategories.containsKey(p.getUniqueId().toString())) {
            selectedCategories.remove(p.getUniqueId().toString());
        }
        selectedCategories.put(p.getUniqueId().toString(), category);

        p.openInventory(build(p));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory().getName().equals(name)) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getRawSlot() > categoryInvSize + itemsInvSize) {
                return;
            }
            if (e.getRawSlot() >= itemsInvSize) {
                open(p, e.getRawSlot() - itemsInvSize);
            } else {
                ShopItem si = categories.get(selectedCategories.get(
                        p.getUniqueId().toString())).items.get(e.getRawSlot());
                si.buy(p, e.isShiftClick() ? -1 : 1);
            }
        }
    }
}
