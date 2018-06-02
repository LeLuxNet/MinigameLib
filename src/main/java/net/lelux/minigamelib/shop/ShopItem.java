package net.lelux.minigamelib.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@ToString
@AllArgsConstructor
public class ShopItem {

    private final ItemStack item;
    private final ItemStack watchItem;
    private final ItemStack price;

    public void buy(Player p, int count) {
        int counter = 0;
        for (ItemStack is : p.getInventory()) {
            if (is.equals(price)) {
                counter += is.getAmount();
            }
        }
        counter /= price.getAmount();
        int counter2 = counter;
        for (ItemStack is : p.getInventory()) {
            if (is.equals(price)) {
                if (counter2 >= is.getAmount()) {
                    p.getInventory().remove(is);
                    counter2 -= is.getAmount();
                } else {
                    is.setAmount(is.getAmount() - counter2);
                    counter2 = 0;
                }
            }
        }
        ItemStack is = item;
        is.setAmount(is.getAmount() * counter);
        p.getInventory().addItem(is);
    }
}
