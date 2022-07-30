package de.lennektro.cakeutils.handler;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.lennektro.cakeutils.filemanagement.BookFile;
import de.lennektro.cakeutils.filemanagement.QuickConfig;
import de.lennektro.cakeutils.util.LocationUtil;

public class InteractionHandler implements Listener {

	private static QuickConfig qconfig = new QuickConfig("interaction_rules");
	private static FileConfiguration config = qconfig.config;
	
	@EventHandler
	public static void onInteract(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		checkAllowed(event);
		checkSBook(event);
	}
	
	private static void checkAllowed(PlayerInteractEvent event) {
		String sLoc = LocationUtil.locToString(event.getClickedBlock().getLocation());
		if(!config.isSet("nonInteractables." + sLoc)) return;
		event.setCancelled(true);
	}
	
	private static void checkSBook(PlayerInteractEvent event) {
		String sLoc = LocationUtil.locToString(event.getClickedBlock().getLocation());
		if(!config.isSet("sBooks." + sLoc)) return;
		event.setCancelled(true);
		BookFile.ensureFile(sLoc + ".txt");
		BookFile.openBookFromFile(event.getPlayer(), sLoc + ".txt");
	}
	
	public static void setAllowed(Location loc, boolean allowed) {
		if(!allowed) config.set("nonInteractables." + LocationUtil.locToString(loc), 0);
		else config.set("nonInteractables." + LocationUtil.locToString(loc), null);
		qconfig.save();
	}
	
	public static void setSBook(Location loc, boolean bool) {
		if(bool) config.set("sBooks." + LocationUtil.locToString(loc), 0);
		else config.set("sBooks." + LocationUtil.locToString(loc), null);
		qconfig.save();
	}
}