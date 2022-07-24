package de.lennektro.pltext;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.MinecraftServer;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
		FileConfiguration config = getConfig();
		if(!config.isSet("header")) {
			config.set("header", Arrays.asList(new String[] {"   ", "Example Header", "   "}));
			saveConfig();
		}
		if(!config.isSet("footer")) {
			config.set("footer", Arrays.asList(new String[] {"   ", "TPS: %tps% | Players: %players_online%/%max_players%"}));
			saveConfig();
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach(c -> setFor(c));
			}
			
		}, 0, 60);
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		setFor(event.getPlayer());
	}
	
	private void setFor(Player player) {
		FileConfiguration config = getConfig();
		player.setPlayerListHeaderFooter(
			listToMLString(config.getStringList("header")), 
			listToMLString(config.getStringList("footer"))
		);
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	private String listToMLString(List<String> list) {
		String value = "";
		for(String s : list) value = value + s + "\n";
		value = value.substring(0, value.length() - 1).replace("\\ue400", "\ue400");
		String tps = String.valueOf(MinecraftServer.getServer().recentTps[0]).substring(0, 5);
		Double tpsD = Double.valueOf(tps);
		ChatColor tpsColor = ChatColor.GREEN;
		if(tpsD <= 16) tpsColor = ChatColor.YELLOW;
		if(tpsD <= 12) tpsColor = ChatColor.RED;
		value = value
				.replace("%players_online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
				.replace("%max_players%", String.valueOf(Bukkit.getMaxPlayers()))
				.replace("%tps%", tps)
				.replace("%tps_colored%", tpsColor + tps + ChatColor.RESET);
		value = ChatColor.translateAlternateColorCodes('&', value);
		return value;
	}
}
