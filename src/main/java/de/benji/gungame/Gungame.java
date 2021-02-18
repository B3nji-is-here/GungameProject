package de.benji.gungame;

import de.benji.database.DatabaseHandler;
import de.benji.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Gungame extends JavaPlugin {

    private static Gungame instance;

    private static DatabaseHandler databaseHandler;

    @Override
    public void onEnable() {
        instance = this;
        databaseHandler = DatabaseHandler.getInstance();
        try {
            databaseHandler.connectToDatabase();
            Bukkit.getLogger().info("[Gungame] Database is Connected");
            databaseHandler.createTables();
            Bukkit.getLogger().info("[Gungame] Tables created or loaded");
        } catch (ClassNotFoundException | SQLException exception) {
            Bukkit.getLogger().info("Database is not Connected");
            exception.printStackTrace();
        }
        loadCommandsAndListener();

    }

    @Override
    public void onDisable() {

    }

    public static Gungame getInstance() {
        return instance;
    }

    private void loadCommandsAndListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

}
