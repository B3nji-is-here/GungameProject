package de.benji.listener;

import de.benji.userprofile.Userprofile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private Userprofile userprofile;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        userprofile = new Userprofile(player);
        userprofile.getDataFromDatabase();
    }
}
