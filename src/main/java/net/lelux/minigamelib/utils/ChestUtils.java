package net.lelux.minigamelib.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestUtils {

    private final int minCount;
    private final int maxCount;
    private final Map<ItemStack, Integer> items;

    public ChestUtils(int minCount, int maxCount) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        items = new HashMap<>();
    }

    public void addItem(ItemStack item, int probability) {
        items.put(item, probability);
    }

    public Inventory generateChest(int size) {
        Inventory inv = Bukkit.createInventory(null, size);

        int count = MathUtils.generateRandomInt(minCount, maxCount);

        int probabilityCount = 0;
        for(Integer i : items.values()) {
            probabilityCount += i;
        }

        List<Integer> freePlaces = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            freePlaces.add(i);
        }
        for (int i = 0; i < count; i++) {
            int place = freePlaces.get(MathUtils.generateRandomInt(freePlaces.size()));
            inv.setItem(place, getItem(MathUtils.generateRandomInt(probabilityCount)));
            freePlaces.remove(place);
        }
        return inv;
    }

    public static int calcSize(int val) {
        return (int) Math.ceil(val / 9D) * 9;
    }

    private ItemStack getItem(int numer) {
        int count = 0;
        for (ItemStack is : items.keySet()) {
            count += items.get(is);
            if(count >= numer) {
                return is;
            }
        }
        return null;
    }
}
