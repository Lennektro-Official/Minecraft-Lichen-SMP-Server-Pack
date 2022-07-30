package de.lennektro.cakeutils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.lennektro.cakeutils.command.AcmCommand;
import de.lennektro.cakeutils.command.CakeToolsCommand;
import de.lennektro.cakeutils.command.EmptyTab;
import de.lennektro.cakeutils.command.InvSeeCommand;
import de.lennektro.cakeutils.command.ServerLockCommand;
import de.lennektro.cakeutils.command.SpawnProtCommand;
import de.lennektro.cakeutils.handler.CakeToolsHandler;
import de.lennektro.cakeutils.handler.InteractionHandler;
import de.lennektro.cakeutils.handler.ServerLockHandler;
import de.lennektro.cakeutils.handler.SpawnHandler;

public class Main extends JavaPlugin {

	private static Plugin instance;
	
	
	@Override
	public void onEnable() {
		instance = this;
		
		ServerLockHandler.initialize();
		SpawnHandler.initialize();
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ServerLockHandler(), this);
		pm.registerEvents(new SpawnHandler(), this);
		pm.registerEvents(new SpawnProtCommand(), this);
		pm.registerEvents(new InteractionHandler(), this);
		pm.registerEvents(new CakeToolsHandler(), this);
		
		registerCommandWithTab("invsee", new InvSeeCommand());
		registerCommandWithTab("serverlock", new ServerLockCommand());
		registerCommandWithTab("spawnprot", new SpawnProtCommand());
		registerCommandWithEmptyTab("caketools", new CakeToolsCommand());
		registerCommandWithEmptyTab("acm", new AcmCommand());
	}
	
	public void registerCommandWithTab(String command, Object obj) {
		getCommand(command).setExecutor((CommandExecutor) obj);
		getCommand(command).setTabCompleter((TabCompleter) obj);
		getCommand(command).setPermission("op.typ");
	}
	
	public void registerCommandWithEmptyTab(String command, CommandExecutor obj) {
		getCommand(command).setExecutor(obj);
		getCommand(command).setTabCompleter(new EmptyTab());
		getCommand(command).setPermission("op.typ");
	}
	
	
	public static Plugin getInstance() {
		return instance;
	}
}
