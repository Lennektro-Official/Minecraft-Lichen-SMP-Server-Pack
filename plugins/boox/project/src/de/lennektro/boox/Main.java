package de.lennektro.boox;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.lennektro.boox.command.EmptyTab;
import de.lennektro.boox.command.NewsCommand;
import de.lennektro.boox.command.RulesCommand;
import de.lennektro.boox.filemanagement.BookFile;
import de.lennektro.boox.handler.PlayerJoinHandler;

public class Main extends JavaPlugin {

	private static Plugin instance;
	
	
	@Override
	public void onEnable() {
		instance = this;
		
		if(!getDataFolder().exists()) getDataFolder().mkdirs();
		File textsDir = new File(getDataFolder().getAbsoluteFile() + File.separator + "texts");
		if(!textsDir.exists()) textsDir.mkdirs();
		BookFile.ensureFile("rules.txt");
		BookFile.ensureFile("news.txt");
		
		FileConfiguration config = getConfig();
		if(!config.isSet("onJoin.showRules")) {
			config.set("onJoin.showRules", false);
			saveConfig();
		}
		if(!config.isSet("onJoin.showNews")) {
			config.set("onJoin.showNews", false);
			saveConfig();
		}
		
		Bukkit.getPluginManager().registerEvents(new PlayerJoinHandler(), this);
		
		getCommand("rules").setExecutor(new RulesCommand());
		getCommand("rules").setTabCompleter(new EmptyTab());
		getCommand("news").setExecutor(new NewsCommand());
		getCommand("news").setTabCompleter(new EmptyTab());
	}
	
	
	public static Plugin getInstance() {
		return instance;
	}
}
