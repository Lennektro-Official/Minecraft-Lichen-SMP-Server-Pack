package de.lennektro.cakeutils.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {

	public static String locToString(Location loc) {
		return loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + loc.getWorld().getName();
	}
	
	public static Location stringToLoc(String str) {
		String[] split = str.split(",");
		if(split.length != 4) return null;
		World world = Bukkit.getWorld(split[3]);
		if(world == null) return null;
		return new Location(world, Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2]));
	}
}
