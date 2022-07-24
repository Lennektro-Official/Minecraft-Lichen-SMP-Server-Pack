package de.lennektro.gravestoned.handlers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.lennektro.gravestoned.filemanagement.InventoryStorage;

public class PlayerDeathHandler implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(event.getEntity().getInventory().isEmpty()) return;
		
		Location location = event.getEntity().getLocation().getBlock().getLocation();
		while(!location.getBlock().getType().isAir()) location.add(0, 1, 0);
		InventoryStorage.saveInv(event.getEntity(), location);
		GraveStonesHandler.create(location, event.getEntity());
		
		event.getEntity().getInventory().clear();
		event.getDrops().clear();
		
		event.getEntity().sendMessage(ChatColor.YELLOW + "Last Death: " + location.getBlockX() + " | " + location.getBlockY() + " | " + location.getBlockZ());
	}
}
