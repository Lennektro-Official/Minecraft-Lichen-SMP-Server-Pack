package de.lennektro.anyhat;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("hat").setExecutor(new HatCommand());
		getCommand("hat").setTabCompleter(new HatCommand());
	}
}
