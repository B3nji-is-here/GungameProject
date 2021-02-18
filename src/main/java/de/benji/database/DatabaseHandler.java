package de.benji.database;

import de.benji.gungame.Gungame;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.security.auth.login.Configuration;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler {

    private static DatabaseHandler instance;

    private File databaseFile = new File(Bukkit.getServer().getPluginManager().getPlugin("Gungame").getDataFolder(), "database.yml");
    private YamlConfiguration configuration;

    private String host = "";
    private String port = "";
    private String databasename = "";
    private String username = "";
    private String password = "";

    private Connection connection;

    private DatabaseHandler() {
        instance = this;
        if (!databaseFile.exists()) {
            try {
                databaseFile.getParentFile().mkdir();
                databaseFile.createNewFile();
                configuration = YamlConfiguration.loadConfiguration(databaseFile);
                configuration.set("host", "localhost");
                configuration.set("port", "3306");
                configuration.set("database", "gungame");
                configuration.set("username", "root");
                configuration.set("password", "gungame123");
                configuration.save(databaseFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getDatabaseLoginDataFromFile();
    }

    public static DatabaseHandler getInstance() {
        if(instance == null) {
            new DatabaseHandler();
        }
        return instance;
    }

    public boolean isConnected() {
        boolean connected = (connection == null ? false : true);
        return connected;
    }

    public void connectToDatabase() throws ClassNotFoundException, SQLException {
        if(!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databasename + "?useSSL=false", username, password);
        }
    }

    public void disconnectFromDatabase() {
        if(isConnected()) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void getDatabaseLoginDataFromFile() {
        configuration = YamlConfiguration.loadConfiguration(databaseFile);
        host = configuration.getString("host");
        port = configuration.getString("port");
        databasename = configuration.getString("database");
        username = configuration.getString("username");
        password = configuration.getString("password");
    }

    public void createTables() {
        if(isConnected()) {
            Bukkit.getScheduler().runTaskAsynchronously(Gungame.getInstance(), () -> {
                try {
                    PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS user (uuid VARCHAR(36) PRIMARY KEY, name VARCHAR(16), kills INT, deaths INT, currentKillstreak INT, longestKillstreak INT, currentKit INT);");
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
