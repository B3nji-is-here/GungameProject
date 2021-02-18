package de.benji.userprofile;

import de.benji.database.DatabaseHandler;
import de.benji.gungame.Gungame;
import de.benji.kitsystem.Kit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter@Setter
public class Userprofile {

    private Connection connection = DatabaseHandler.getInstance().getConnection();

    private Player player;

    private String uuid;
    private String name;

    private int kills;
    private int deaths;
    private int currentkillstreak;
    private int longestKillstreak;
    private Kit currentKit;


    public Userprofile(Player player) {
        this.player = player;
        name = player.getName();
        uuid = player.getUniqueId().toString();
        getDataFromDatabase();
    }

    public void getDataFromDatabase() {
        if(exists()) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT kills, deaths, currentKillstreak, longestKillstreak, currentKit FROM user WHERE uuid=?");
                statement.setString(1, uuid);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    kills = resultSet.getInt("kills");
                    deaths = resultSet.getInt("deaths");
                    currentkillstreak = resultSet.getInt("currentKillstreak");
                    longestKillstreak = resultSet.getInt("longestKillstreak");
                    //TODO: currentKit;
                } else {
                    Bukkit.getLogger().warning("No Dataset found");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            kills = 0;
            deaths = 0;
            currentkillstreak = 0;
            longestKillstreak = 0;
            //TODO: currentKit;
            safeUserprofileToDatabase();
        }
    }

    public void safeUserprofileToDatabase() {
        Bukkit.getScheduler().runTaskAsynchronously(Gungame.getInstance(), () -> {
            //TODO: currentKit
            int kitId = 0;
            if(exists()) {
                try {
                    PreparedStatement statement = connection.prepareStatement("UPDATE user SET name=?, kills=?, deaths=?, currentKillstreak=?, longestKillstreak=?, currentKit=? WHERE uuid=?;");
                    statement.setString(1, name);
                    statement.setInt(2, kills);
                    statement.setInt(3, deaths);
                    statement.setInt(4, currentkillstreak);
                    statement.setInt(5, longestKillstreak);
                    statement.setInt(6, kitId);
                    statement.setString(7, uuid);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    PreparedStatement statement = connection.prepareStatement("INSERT IGNORE INTO user (uuid, name, kills, deaths, currentKillstreak, longestKillstreak, currentKit) VALUES (?, ?, ?, ?, ?, ?, ?);");
                    statement.setString(1, uuid);
                    statement.setString(2, name);
                    statement.setInt(3, kills);
                    statement.setInt(4, deaths);
                    statement.setInt(5, currentkillstreak);
                    statement.setInt(6, longestKillstreak);
                    statement.setInt(7, kitId);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean exists() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE uuid=?;");
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
