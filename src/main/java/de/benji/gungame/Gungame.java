package de.benji.gungame;

import de.benji.database.DatabaseHandler;
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
            Bukkit.getLogger().info("Database is Connected");
        } catch (ClassNotFoundException | SQLException exception) {
            Bukkit.getLogger().info("Database is not Connected");
            exception.printStackTrace();
        }
    }

    @Override
    public void onDisable() {

    }

    public static Gungame getInstance() {
        return instance;
    }

}
