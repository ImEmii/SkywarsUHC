package me.emi.swh.game;

import me.emi.swh.utils.KitsConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class KitsSetting {

    public static Map<String, ItemStack> kits;


    public static void addKits(String name){

        ItemStack itemStack = new ItemStack(Material.getMaterial(KitsConfig.getConfig().getString("Kits."+name+".material")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(KitsConfig.getConfig().getString("Kits."+name+".name").replace("&", "§"));
        itemStack.setItemMeta(itemMeta);

        kits.put(name, itemStack);

    }

    public static Map<String, ItemStack> getKits(){
        return kits;
    }
}
