package me.emi.swh;

import me.emi.swh.commands.SwhCommand;
import me.emi.swh.game.Arena;
import me.emi.swh.game.ArenaManager;
import me.emi.swh.game.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

    public static Main instance;

    private ArenaManager arenaManager;

    private File configFile;
    private FileConfiguration config;

    @Override
    public void onEnable() {

        arenaManager = new ArenaManager();
        instance = this;
        loadConfig();

        Bukkit.getConsoleSender().sendMessage("§a==============");
        Bukkit.getConsoleSender().sendMessage("§6   Sky Wars");
        Bukkit.getConsoleSender().sendMessage("§6Ultra HardCore");
        Bukkit.getConsoleSender().sendMessage("§a==============");

        registerListener(new PlayerInteract(this), new SignChange(), new PlayerDamage(),
                new PlayerFood(), new PlayerDeath(), new PlayerDrop(),
                new PlayerPickup(), new PlayerBreak(), new PlayerPlace());
        getCommand("skywarshardcore").setExecutor(new SwhCommand());

    }

    private void loadConfig() {
        if (!getDataFolder().exists()) {

            getDataFolder().mkdirs();
        }
        configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        if (!config.contains("arenas")) {
            config.set("arenas", "");
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadArena();
    }

    private void loadArena(){

        if(!config.getString("arenas").equals("")){
            for(String arena : config.getConfigurationSection("arenas").getKeys(false)){
                new Arena(arena, unserializeLocation(config.getString("arenas."+arena+".lobbyspawn")),
                        unserializeLocation(config.getString("arenas."+arena+".lobbyspawn")),
                        listStrToLocs(config.getStringList("arenas."+arena+".spawns"))
                        , config.getInt("arenas."+arena+".maxplayers"),
                        config.getString("arenas."+arena+".sign").equals("NOEXIST") ? null : (Sign)unserializeLocation(config.getString("arenas."+arena+".sign"))
                                .getWorld().getBlockAt(unserializeLocation(config.getString("arenas."+arena+".sign"))).getState());
            }

        }
    }

    public void registerListener(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public String chatColor(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public String serializeLocation(Location loc){
        return loc.getWorld().getName()+"="+loc.getX()+"="+loc.getY()+"="+loc.getZ()+"="+loc.getPitch()+"="+loc.getYaw();
    }
    public Location unserializeLocation(String loc){
        if(loc.equals("NOEXIST")){
            return unserializeLocation(loc);
        }
            String[] split = loc.split("=");
            Location location = new Location(getServer().getWorld(split[0]), Double.parseDouble(split[1]),
                    Double.parseDouble(split[2]), Double.parseDouble(split[3]));
            location.setPitch(Float.parseFloat(split[4]));
            location.setYaw(Float.parseFloat(split[5]));
            return location;
    }

    @Override
    public void onDisable() {

        Bukkit.getConsoleSender().sendMessage("§4==============");
        Bukkit.getConsoleSender().sendMessage("§6   Sky Wars");
        Bukkit.getConsoleSender().sendMessage("§6Ultra HardCore");
        Bukkit.getConsoleSender().sendMessage("§4==============");


    }
    @Override
    public FileConfiguration getConfig(){
        return config;
    }

    @Override
    public void saveConfig(){
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public List<Location> listStrToLocs(List<String> locs){
        List<Location> toReturn = new ArrayList<>();

        locs.stream().forEach(loc -> toReturn.add(unserializeLocation(loc)));

        return toReturn;
    }
}
