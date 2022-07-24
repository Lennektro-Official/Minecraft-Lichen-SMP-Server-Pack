package de.lennektro.cakeutils.handler;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import de.lennektro.cakeutils.filemanagement.QuickConfig;
import de.lennektro.cakeutils.util.LocationUtil;

public class SpawnHandler implements Listener {

	private static QuickConfig qconfig = new QuickConfig("spawnarea");
	private static FileConfiguration config = qconfig.config;
	
	@EventHandler
	public static void onBreakBlock(BlockBreakEvent event) {
		if(!config.getBoolean("enabled")) return;
		if(!isInArea(event.getBlock().getLocation())) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public static void onPlaceBlock(BlockPlaceEvent event) {
		if(!config.getBoolean("enabled")) return;
		if(!isInArea(event.getBlock().getLocation())) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public static void onPlayerDamage(EntityDamageEvent event) {
		if(!config.getBoolean("enabled")) return;
		if(!event.getEntityType().equals(EntityType.PLAYER)) return;
		if(!isInArea(event.getEntity().getLocation())) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public static void onEntityExplode(EntityExplodeEvent event) {
		if(!config.getBoolean("enabled")) return;
		for(Block block : event.blockList()) if(isInArea(block.getLocation())) {
			event.setCancelled(true);
			break;
		}
	}
	
	public static void initialize() {
		if(!config.isSet("enabled")) {
			config.set("enabled", false);
		}
	}
	
	public static void setPos1(Location loc) {
		config.set("pos1", LocationUtil.locToString(loc));
		qconfig.save();
	}
	
	public static void setPos2(Location loc) {
		config.set("pos2", LocationUtil.locToString(loc));
		qconfig.save();
	}
	
	public static void setEnabled(boolean value) {
		config.set("enabled", value);
		qconfig.save();
	}
	
	public static boolean isInArea(Location loc) {
		Location loc1 = LocationUtil.stringToLoc(config.getString("pos1"));
		Location loc2 = LocationUtil.stringToLoc(config.getString("pos2"));
		World world = loc1.getWorld();
		if(!loc.getWorld().getUID().equals(world.getUID())) return false;
		Location locMin = new Location(world, 
				Math.min(loc1.getBlockX(), loc2.getBlockX()), 
				Math.min(loc1.getBlockY(), loc2.getBlockY()), 
				Math.min(loc1.getBlockZ(), loc2.getBlockZ()));
		Location locMax= new Location(world, 
				Math.max(loc1.getBlockX(), loc2.getBlockX()), 
				Math.max(loc1.getBlockY(), loc2.getBlockY()), 
				Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
		return (locMin.getBlockX() <= loc.getBlockX()
				&& locMax.getBlockX() >= loc.getBlockX()
				&& locMin.getBlockY() <= loc.getBlockY()
				&& locMax.getBlockY() >= loc.getBlockY()
				&& locMin.getBlockZ() <= loc.getBlockZ()
				&& locMax.getBlockZ() >= loc.getBlockZ());
	}
}
