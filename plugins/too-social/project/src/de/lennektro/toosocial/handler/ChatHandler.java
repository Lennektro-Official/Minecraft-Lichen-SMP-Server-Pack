package de.lennektro.toosocial.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.lennektro.toosocial.manager.NameColorManager;
import de.lennektro.toosocial.manager.StatusManager;
import net.md_5.bungee.api.ChatColor;

public class ChatHandler implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		String[] msgSplit = event.getMessage().split(" ");
		Player dmPlayer = Bukkit.getPlayerExact(msgSplit[0]);
		if(dmPlayer != null && !event.getMessage().startsWith("!")) {
			event.setCancelled(true);
			String dmsg = " >> " + event.getMessage().substring(msgSplit[0].length() + 1);
			String dmPrefix = ChatColor.GRAY + "(" + ChatColor.YELLOW + "DM" + ChatColor.GRAY + ") ";
			dmPlayer.sendMessage(dmPrefix + "from " + ChatColor.RESET + NameColorManager.getName(event.getPlayer()) + ChatColor.RESET + dmsg);
			event.getPlayer().sendMessage(dmPrefix + "to " + ChatColor.RESET + NameColorManager.getName(dmPlayer) + ChatColor.RESET + dmsg);
		}
		if(event.getMessage().startsWith("!")) event.setMessage(event.getMessage().substring(1));
		event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
		if(StatusManager.get(event.getPlayer()) == null) {
			event.setFormat(ChatColor.RESET + "<" + NameColorManager.getName(event.getPlayer()) + ChatColor.RESET + "> %2$s");
			return;
		}
		event.setFormat(ChatColor.GRAY + "[" + StatusManager.get(event.getPlayer()) + ChatColor.GRAY + "] " 
		+ ChatColor.RESET + "<" + NameColorManager.getName(event.getPlayer()) + ChatColor.RESET + "> %2$s");
	}
}
