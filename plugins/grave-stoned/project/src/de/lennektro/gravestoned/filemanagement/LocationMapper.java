package de.lennektro.gravestoned.filemanagement;

import org.bukkit.Location;

public class LocationMapper {

	public static void set(Location location, String uuid) {
		QuickConfig qconf = new QuickConfig("locs");
		qconf.config.set(convertLoc(location), uuid);
		qconf.save();
	}
	
	public static String get(Location location) {
		QuickConfig qconf = new QuickConfig("locs");
		return qconf.config.getString(convertLoc(location));
	}
	
	public static void reset(Location location) {
		QuickConfig qconf = new QuickConfig("locs");
		qconf.config.set(convertLoc(location), null);
		qconf.save();
	}
	
	
	public static String convertLoc(Location loc) {
		return loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + loc.getWorld().getName();
	}
}
