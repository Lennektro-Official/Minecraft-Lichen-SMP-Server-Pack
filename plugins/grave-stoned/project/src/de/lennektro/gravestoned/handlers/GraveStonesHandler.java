package de.lennektro.gravestoned.handlers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.lennektro.gravestoned.filemanagement.InventoryStorage;
import de.lennektro.gravestoned.filemanagement.LocationMapper;
import de.lennektro.gravestoned.util.GraveStoneModel;

public class GraveStonesHandler implements Listener {

	public static void create(Location location, Player player) {
		LocationMapper.set(location, player.getUniqueId().toString());
		location.getBlock().setType(Material.BARRIER);
		GraveStoneModel.place(location, LocationMapper.convertLoc(location));
	}
	
	public static void destroy(Location location, Player player) {
		LocationMapper.reset(location);
		location.getBlock().setType(Material.AIR);
		GraveStoneModel.remove(LocationMapper.convertLoc(location));
		location.getWorld().playSound(location, Sound.BLOCK_CHEST_OPEN, 1, 2);
		location.getWorld().spawnParticle(Particle.TOTEM, location.getBlockX() + 0.5, location.getBlockY() + 0.5, location.getBlockZ() + 0.5, 20);
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(!event.getClickedBlock().getType().equals(Material.BARRIER)) return;
		Location loc = event.getClickedBlock().getLocation();
		if(LocationMapper.get(loc) == null) return;
		if(!LocationMapper.get(loc).equals(event.getPlayer().getUniqueId().toString())) return;
		event.setCancelled(true);
		destroy(loc, event.getPlayer());
		InventoryStorage.dropInv(loc);
	}
}
