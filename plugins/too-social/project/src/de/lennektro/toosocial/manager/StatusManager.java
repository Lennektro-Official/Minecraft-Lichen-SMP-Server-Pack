package de.lennektro.toosocial.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.lennektro.toosocial.filemanagement.QuickConfig;
import net.md_5.bungee.api.ChatColor;

public class StatusManager {

	private static QuickConfig qconfig = new QuickConfig("status");
	private static FileConfiguration config = qconfig.config;
	
	public static void set(Player player, String status) {
		config.set(player.getUniqueId().toString(), status);
		qconfig.save();
		
		String newName = ChatColor.GRAY + "[" + status + ChatColor.GRAY + "] " + ChatColor.RESET + NameColorManager.getName(player);
		player.setPlayerListName(newName);
		player.setDisplayName(newName);
	}
	
	public static String get(Player player) {
		return config.getString(player.getUniqueId().toString());
	}
	
	public static void reset(Player player) {
		config.set(player.getUniqueId().toString(), null);
		qconfig.save();
		
		String name = NameColorManager.getName(player);
		player.setPlayerListName(name);
		player.setDisplayName(name);
	}
}
