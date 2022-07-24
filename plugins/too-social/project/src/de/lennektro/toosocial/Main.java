package de.lennektro.toosocial;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.lennektro.toosocial.command.NColorCommand;
import de.lennektro.toosocial.command.StatusCommand;
import de.lennektro.toosocial.filemanagement.MotdsTxt;
import de.lennektro.toosocial.handler.ChatHandler;
import de.lennektro.toosocial.handler.PlayerConHandler;

public class Main extends JavaPlugin implements Listener {

	private static Plugin instance;
	
	
	@Override
	public void onEnable() {
		instance = this;
		
		MotdsTxt.processFile();
		
		FileConfiguration config = getConfig();
		if(!config.isSet("msg.join")) {
			config.set("msg.join", "&a[+] &r%player%");
			saveConfig();
		}
		if(!config.isSet("msg.quit")) {
			config.set("msg.quit", "&c[-] &r%player%");
			saveConfig();
		}
		
		Bukkit.getPluginManager().registerEvents(new PlayerConHandler(), this);
		Bukkit.getPluginManager().registerEvents(new ChatHandler(), this);
		
		getCommand("status").setExecutor(new StatusCommand());
		getCommand("status").setTabCompleter(new StatusCommand());
		
		getCommand("ncolor").setExecutor(new NColorCommand());
		getCommand("ncolor").setTabCompleter(new NColorCommand());
		getCommand("ncolor").setPermission("op.typ");
	}
	
	
	public static Plugin getInstance() {
		return instance;
	}
}
