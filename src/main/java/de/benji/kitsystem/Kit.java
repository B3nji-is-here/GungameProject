package de.benji.kitsystem;

import de.benji.userprofile.Userprofile;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;


public class Kit {

    @Getter
    private int kitId;
    private ArrayList<ItemStack> kitItems = new ArrayList<>();

    Userprofile userprofile;

    public Kit(int kitId) {
        this.kitId = kitId;
    }

    public void applyToPlayer(Player player) {
        userprofile = new Userprofile(player);
        userprofile.setCurrentKit(this);

        player.getInventory().setHelmet(kitItems.get(0));
        player.getInventory().setChestplate(kitItems.get(1));
        player.getInventory().setLeggings(kitItems.get(2));
        player.getInventory().setBoots(kitItems.get(3));
        player.getInventory().setItem(0, kitItems.get(4));
        player.getInventory().setItem(EquipmentSlot.HAND, kitItems.get(5));
        for(int i = 6; i < kitItems.size(); i++) {
            player.getInventory().setItem(i - 5, kitItems.get(i));
        }
    }
}
