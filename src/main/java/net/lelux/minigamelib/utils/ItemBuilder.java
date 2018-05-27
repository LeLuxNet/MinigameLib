package net.lelux.minigamelib.utils;

import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack is;

    public ItemBuilder(Material material, int amount) {
        is = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(Material material, int amount, byte durability) {
        is = new ItemStack(material, amount, durability);
    }

    public ItemBuilder setName(String name) {
        ItemMeta ism = is.getItemMeta();
        ism.setDisplayName(name);
        is.setItemMeta(ism);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        is.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        is.setDurability(durability);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment ench, int level) {
        is.addEnchantment(ench, level);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        is.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta ism = is.getItemMeta();
        ism.setLore(lore);
        is.setItemMeta(ism);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        ItemMeta ism = is.getItemMeta();
        List<String> lore;
        if (ism.hasLore()) {
            lore = ism.getLore();
        } else {
            lore = new ArrayList<>();
        }
        lore.add(line);
        ism.setLore(lore);
        is.setItemMeta(ism);
        return this;
    }

    public ItemBuilder removeNBTKey(String key) {
        NBTItem nbti = new NBTItem(is);
        nbti.removeKey(key);
        is = nbti.getItem();
        return this;
    }

    public ItemBuilder setNBTString(String key, String val) {
        NBTItem nbti = new NBTItem(is);
        nbti.setString(key, val);
        is = nbti.getItem();
        return this;
    }

    public ItemBuilder setNBTInteger(String key, int val) {
        NBTItem nbti = new NBTItem(is);
        nbti.setInteger(key, val);
        is = nbti.getItem();
        return this;
    }

    public ItemStack build() {
        return is;
    }
}
