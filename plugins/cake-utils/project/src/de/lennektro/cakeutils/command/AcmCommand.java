package de.lennektro.cakeutils.command;

import java.io.File;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.lennektro.cakeutils.filemanagement.QuickConfig;
import de.lennektro.cakeutils.util.LocationUtil;

public class AcmCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return true;
		Player player = (Player) sender;
		if(player.getGameMode().equals(GameMode.SURVIVAL)) {
			savePlayer(player);
			return true;
		} 
		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			loadPlayer(player);
			return true;
		}
		return true;
	}
	
	private static void savePlayer(Player player) {
		QuickConfig qconfig = new QuickConfig("acm" + File.separator + player.getUniqueId().toString());
		FileConfiguration config = qconfig.config;
		setISL(config, "inv", player.getInventory().getContents());
		config.set("loc", LocationUtil.locToString(player.getLocation()));
		qconfig.save();
		player.setGameMode(GameMode.CREATIVE);
		player.getInventory().setContents(loadISL(config, "cinv"));
	}
	
	private static void loadPlayer(Player player) {
		QuickConfig qconfig = new QuickConfig("acm" + File.separator + player.getUniqueId().toString());
		FileConfiguration config = qconfig.config;
		setISL(config, "cinv", player.getInventory().getContents());
		qconfig.save();
		player.getInventory().setContents(loadISL(config, "inv"));
		player.teleport(LocationUtil.stringToLoc(config.getString("loc")));
		player.setGameMode(GameMode.SURVIVAL);
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
