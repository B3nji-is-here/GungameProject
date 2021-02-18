package de.benji.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

    private static DatabaseHandler instance;

    private String host = "localhost";
    private String port = "3306";
    private String databasename = "serversystem";
    private String username = "root";
    private String password = "minecraftadmin14527";

    private Connection connection;

    private DatabaseHandler() {
        instance = this;
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

}
