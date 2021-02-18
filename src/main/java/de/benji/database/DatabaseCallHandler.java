package de.benji.database;

import de.benji.gungame.Gungame;

import java.sql.Connection;

public class DatabaseCallHandler {

    private static DatabaseCallHandler instance;

    private Gungame pluginInstance;

    private Connection connection;

    private DatabaseCallHandler(Gungame pluginInstance) {
        instance = this;
        this.pluginInstance = pluginInstance;
    }

    public static DatabaseCallHandler getInstance(Gungame pluginInstance) {
        if(instance == null) {
            new DatabaseCallHandler(pluginInstance);
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
