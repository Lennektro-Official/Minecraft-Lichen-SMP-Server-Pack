package de.lennektro.decoheads;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.lennektro.decoheads.handler.HeadEditor;
import de.lennektro.decoheads.handler.PlayerDeathHandler;
import de.lennektro.decoheads.util.HeadRecipe;

public class Main extends JavaPlugin {

	private static Plugin instance;
	
	
	@Override
	public void onEnable() {
		instance = this;
		
		FileConfiguration config = getConfig();
		if(!config.isSet("dropHeadOnPlayerDeath")) {
			config.set("dropHeadOnPlayerDeath", true);
			saveConfig();
		}
		if(!config.isSet("headCraftable")) {
			config.set("headCraftable", true);
			saveConfig();
		}
		
		if(config.getBoolean("headCraftable")) HeadRecipe.register();
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerDeathHandler(), this);
		pm.registerEvents(new HeadEditor(), this);
	}
	
	
	public static Plugin getInstance() {
		return instance;
	}
}
