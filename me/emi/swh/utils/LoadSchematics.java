package me.emi.swh.utils;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.world.DataException;
import me.emi.swh.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class LoadSchematics {

    public static void onLoadSchematic(Player p, String schematicname, boolean value){
        Location loc = p.getLocation();
        WorldEditPlugin worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        File schematic = new File(Main.getInstance().getDataFolder() +File.separator +"/schematics/"+schematicname+".schematic");
        EditSession session = worldEditPlugin.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()),999999999);
       if(worldEditPlugin != null){
           if(schematic != null){
               if(session != null){
                   try {
                       CuboidClipboard clipboard = MCEditSchematicFormat.getFormat(schematic).load(schematic);
                       clipboard.paste(session, new Vector(loc.getX(), loc.getY(), loc.getZ()), value);

                   }catch (MaxChangedBlocksException | DataException | IOException e){
                       e.printStackTrace();
                   }
               }else{
                   p.sendMessage(Main.getInstance().getConfig().getString("messages.errormessage").replace("&", "§").replace("{error}",  "Session doest exist"));
               }
           }else{
               p.sendMessage(Main.getInstance().getConfig().getString("messages.errormessage").replace("&", "§").replace("{error}", schematicname + " doest exist"));
           }
       }else{
           p.sendMessage(Main.getInstance().getConfig().getString("messages.errormessage").replace("&", "§").replace("{error}",  "Load WorldEditPlugin"));
       }
    }


}
