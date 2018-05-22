package net.lelux.minigamelib.shop;

import net.lelux.minigamelib.utils.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopItem {

    private final Buyable item;
    private final ItemStack watchItem;
    private final ItemStack price;

    public ShopItem(Buyable item, ItemStack watchItem, ItemStack price) {
        this.item = item;
        this.watchItem = watchItem;
        this.price = price;
    }

    public ItemStack getWatchItem() {
        return watchItem;
    }

    public Buyable getItem() {
        return item;
    }

    public ItemStack getPrice() {
        return price;
    }

    public void buy(Player p, int count) {
        if(count < 0) {
            count = item.getItem().getAmount() * -count;
        }
        int counter = 0;
        for(ItemStack is : p.getInventory()) {
            if(is.equals(price)) {
                counter += is.getAmount();
            }
        }
        counter /= price.getAmount();
        int counter2 = counter;
        for(ItemStack is : p.getInventory()) {
            if(is.equals(price)) {
                if(counter2 >= is.getAmount()) {
                    p.getInventory().remove(is);
                    counter2 -= is.getAmount();
                } else {
                    is.setAmount(is.getAmount() - counter2);
                    counter2 = 0;
                }
            }
        }
        ItemStack is = item.getItem();
        is.setAmount(is.getAmount() * counter);
        p.getInventory().addItem(is);
    }
}
