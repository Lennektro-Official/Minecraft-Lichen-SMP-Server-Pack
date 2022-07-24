package de.lennektro.toosocial.manager;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.lennektro.toosocial.filemanagement.QuickConfig;

public class NameColorManager {

	private static QuickConfig qconfig = new QuickConfig("name_colors");
	private static FileConfiguration config = qconfig.config;
	
	public static void setColor(Player player, ChatColor color) {
		String colorS = null;
		if(color != null) colorS = color.toString();
		config.set(player.getUniqueId().toString(), colorS);
		qconfig.save();
		StatusManager.reset(player);
	}
	
	public static String getName(Player player) {
		String color = config.getString(player.getUniqueId().toString());
		if(color == null) return player.getName();
		return color + player.getName();
	}
}
