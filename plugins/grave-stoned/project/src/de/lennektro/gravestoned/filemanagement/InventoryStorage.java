package de.lennektro.gravestoned.filemanagement;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryStorage {

	public static void saveInv(Player player, Location location) {
		QuickConfig qconf = new QuickConfig("invs" + File.separator + LocationMapper.convertLoc(location));
		qconf.delete();
		setISL(qconf.config, "contents", player.getInventory().getContents());
		qconf.save();
	}
	
	public static void dropInv(Location location) {
		QuickConfig qconf = new QuickConfig("invs" + File.separator + LocationMapper.convertLoc(location));
		for(ItemStack is : loadISL(qconf.config, "contents")) if(is != null) location.getWorld().dropItemNaturally(location, is);
		qconf.delete();
	}
	
	
	private static void setISL(FileConfiguration config, String path, ItemStack[] items) {
		config.set(path + ".length", items.length);
		for(int i = 0; i < items.length; i++) {
			config.set(path + "." + i, items[i]);
		}
	}
	
	private static ItemStack[] loadISL(FileConfiguration config, String path) {
		int length = config.getInt(path + ".length");
		ItemStack[] items = new ItemStack[length];
		for(int i = 0; i < length; i++) {
			items[i] = config.getItemStack(path + "." + i);
		}
		return items;
	}
}
