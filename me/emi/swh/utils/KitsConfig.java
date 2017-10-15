package me.emi.swh.utils;

import java.io.File;

import me.emi.swh.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class KitsConfig extends YamlConfiguration {

    private static KitsConfig config;

    public static KitsConfig getConfig() {
        if (config == null) {
            config = new KitsConfig();
        }
        return config;
    }

    private Plugin main() {
        return Main.instance;
    }

    private Plugin plugin;
    private File configFile;


    public KitsConfig() {
        plugin = main();
        configFile = new File(plugin.getDataFolder(), "kits.yml");
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
        plugin.saveResource("kits.yml", false);
    }
}