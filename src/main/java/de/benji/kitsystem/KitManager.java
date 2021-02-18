package de.benji.kitsystem;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KitManager {

    private static KitManager instance;

    private File kitFile = new File(Bukkit.getServer().getPluginManager().getPlugin("Gungame").getDataFolder(), "kits.yml");
    private YamlConfiguration configuration;

    private ArrayList<Kit> kits = new ArrayList<>();

    private KitManager() {
        instance = this;
        if (!kitFile.exists()) {
            try {
                kitFile.getParentFile().mkdir();
                kitFile.createNewFile();
                configuration = YamlConfiguration.loadConfiguration(kitFile);

                configuration.save(kitFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static KitManager getInstance() {
        if(instance == null) {
            new KitManager();
        }
        return instance;
    }


}
