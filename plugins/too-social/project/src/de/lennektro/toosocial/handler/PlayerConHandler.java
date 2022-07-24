package de.lennektro.toosocial.handler;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import de.lennektro.toosocial.Main;
import de.lennektro.toosocial.filemanagement.MotdsTxt;
import de.lennektro.toosocial.manager.NameColorManager;
import de.lennektro.toosocial.manager.StatusManager;

public class PlayerConHandler implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("msg.join"))
				.replace("%player%", NameColorManager.getName(event.getPlayer())));
		if(StatusManager.get(event.getPlayer()) == null) {
			StatusManager.reset(event.getPlayer());
			return;
		}
		StatusManager.set(event.getPlayer(), StatusManager.get(event.getPlayer()));
		event.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Your status is set to " + StatusManager.get(event.getPlayer()));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("msg.quit"))
				.replace("%player%", NameColorManager.getName(event.getPlayer())));
	}
	
	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		if(MotdsTxt.motds.isEmpty()) return;
		event.setMotd(MotdsTxt.motds.get(new Random().nextInt(MotdsTxt.motds.size())));
	}
}
