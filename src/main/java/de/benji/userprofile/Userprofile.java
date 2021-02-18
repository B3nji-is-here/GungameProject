package de.benji.userprofile;

import de.benji.kitsystem.Kit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter@Setter
public class Userprofile {

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

    }

    public void exists() {

    }
}
