package de.lennektro.boox.handler;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.lennektro.boox.filemanagement.QuickConfig;

public class SeenTracker {

	private static QuickConfig rules_qconfig = new QuickConfig("seen" + File.separator + "rules");
	private static FileConfiguration rules_config = rules_qconfig.config;
	
	private static QuickConfig news_qconfig = new QuickConfig("seen" + File.separator + "news");
	private static FileConfiguration news_config = news_qconfig.config;
	
	public static boolean markAndCheckSeenRules(Player player) {
		if(rules_config.isSet(player.getUniqueId().toString())) return true;
		rules_config.set(player.getUniqueId().toString(), 0);
		rules_qconfig.save();
		return false;
	}
	
	public static boolean markAndCheckSeenNews(Player player) {
		if(news_config.isSet(player.getUniqueId().toString())) return true;
		news_config.set(player.getUniqueId().toString(), 0);
		news_qconfig.save();
		return false;
	}
}
