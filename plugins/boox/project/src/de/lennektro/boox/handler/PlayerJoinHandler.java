package de.lennektro.boox.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.lennektro.boox.Main;
import de.lennektro.boox.filemanagement.BookFile;

public class PlayerJoinHandler implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(Main.getInstance().getConfig().getBoolean("onJoin.showRules")) {
			if(!SeenTracker.markAndCheckSeenRules(event.getPlayer())) {
				BookFile.openBookFromFile(event.getPlayer(), "rules.txt");
				return;
			}
		}
		if(Main.getInstance().getConfig().getBoolean("onJoin.showNews")) {
			if(!SeenTracker.markAndCheckSeenNews(event.getPlayer())) {
				BookFile.openBookFromFile(event.getPlayer(), "news.txt");
				return;
			}
		}
	}
}
