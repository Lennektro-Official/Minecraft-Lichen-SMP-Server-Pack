package de.lennektro.gravestoned;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.lennektro.gravestoned.handlers.GraveStonesHandler;
import de.lennektro.gravestoned.handlers.PlayerDeathHandler;

public class Main extends JavaPlugin {

	private static Plugin instance;
	
	
	@Override
	public void onEnable() {
		instance = this;
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerDeathHandler(), this);
		pm.registerEvents(new GraveStonesHandler(), this);
	}
	
	
	public static Plugin getInstance() {
		return instance;
	}
}
