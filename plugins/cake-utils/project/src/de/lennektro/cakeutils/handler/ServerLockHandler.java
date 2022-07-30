package de.lennektro.cakeutils.handler;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.lennektro.cakeutils.filemanagement.QuickConfig;

public class ServerLockHandler implements Listener {

	private static QuickConfig qconfig = new QuickConfig("serverlock");
	private static FileConfiguration config = qconfig.config;
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		if(!config.getBoolean("locked")) return;
		if(config.getStringList("allowed").contains(event.getPlayer().getUniqueId().toString())) return;
		String msg = 
				ChatColor.YELLOW + "Server is currently locked.\n"
				+ "   \n"
				+ ChatColor.GOLD + "Reason:\n"
				+ config.getString("reason");
		event.disallow(Result.KICK_OTHER, msg);
	}
	
	public static void initialize() {
		if(!config.isSet("locked")) {
			config.set("locked", false);
			qconfig.save();
		}
		if(!config.isSet("reason")) {
			config.set("reason", "none");
			qconfig.save();
		}
		if(!config.isSet("allowed")) {
			config.set("allowed", Arrays.asList(new String[] {}));
			qconfig.save();
		}
	}
	
	public static void setLocked(boolean value) {
		config.set("locked", value);
		qconfig.save();
		if(!value) return;
		String msg = 
				ChatColor.YELLOW + "Server is currently locked.\n"
				+ "   \n"
				+ ChatColor.GOLD + "Reason:\n"
				+ config.getString("reason");
		Bukkit.getOnlinePlayers().forEach(player -> {
			if(!config.getStringList("allowed").contains(player.getUniqueId().toString()))
				player.kickPlayer(msg);
		});
	}
	
	public static void setReason(String reason) {
		config.set("reason", reason);
		qconfig.save();
	}
	
	public static void addAllowed(OfflinePlayer player) {
		List<String> allowed = config.getStringList("allowed");
		if(allowed.contains(player.getUniqueId().toString())) return;
		allowed.add(player.getUniqueId().toString());
		config.set("allowed", allowed);
		qconfig.save();
	}
	
	public static void removeAllowed(OfflinePlayer player) {
		List<String> allowed = config.getStringList("allowed");
		if(!allowed.contains(player.getUniqueId().toString())) return;
		allowed.remove(player.getUniqueId().toString());
		config.set("allowed", allowed);
		qconfig.save();
	}
}
