package me.emi.swh.game.inventoryes;

import me.emi.swh.Main;
import me.emi.swh.game.Arena;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinInventory {

    public static void itemsOnJoin(Player p){

        ItemStack ki = new ItemStack(Material.CHEST);
        ItemMeta kim = ki.getItemMeta();
        kim.setDisplayName("§m__§6Kits§f§m__");
        ki.setItemMeta(kim);

        p.getInventory().clear();
        p.getInventory().setItem(0, ki);
        p.updateInventory();

    }



}
