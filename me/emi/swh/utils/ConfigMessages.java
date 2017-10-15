package me.emi.swh.utils;

import java.io.File;

import me.emi.swh.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigMessages extends YamlConfiguration {

    private static ConfigMessages config;

    public static ConfigMessages getConfig() {
        if (config == null) {
            config = new ConfigMessages();
        }
        return config;
    }

    private Plugin main() {
        return Main.instance;
    }

    private Plugin plugin;
    private File configFile;


    public ConfigMessages() {
        plugin = main();
        configFile = new File(plugin.getDataFolder(), "messages.yml");
        saveDefault();
        reload();
    }

    // this method is alternative to the super method to avoid having to place try/catches in your code.
    public void reload() {
        try {
            super.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this method is alternative to the super method to avoid having to place try/catches in your code.
    public void save() {
        try {
            super.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this method works in the same way plugin.saveDefaultConfig() does
    public void saveDefault() {
        plugin.saveResource("messages.yml", false);
    }
}