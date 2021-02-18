package de.benji.database;

import org.bukkit.configuration.file.YamlConfiguration;

import javax.security.auth.login.Configuration;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

    private static DatabaseHandler instance;

    private File databaseFile = new File("plugins//Gungame//database.yml");
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

}
