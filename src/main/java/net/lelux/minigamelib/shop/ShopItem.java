package net.lelux.minigamelib.shop;

import org.bukkit.inventory.ItemStack;

public class ShopItem implements Buyable {

    private final ItemStack item;
    private final ItemStack watchItem;
    private final long price;

    public ShopItem(ItemStack item, ItemStack watchItem, long price) {
        this.item = item;
        this.watchItem = watchItem;
        this.price = price;
    }

    public ItemStack getWatchItem() {
        return watchItem;
    }

    public ItemStack getItem() {
        return item;
    }

    public long getPrice() {
        return price;
    }
}
