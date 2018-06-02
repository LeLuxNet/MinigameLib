package net.lelux.minigamelib.shop;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopCategory {

    @Getter
    private final ItemStack watchItem;
    final List<ShopItem> items;

    public ShopCategory(ItemStack watchItem) {
        this.watchItem = watchItem;
        items = new ArrayList<>();
    }

    public void addItem(ShopItem item) {
        items.add(item);
    }
}
