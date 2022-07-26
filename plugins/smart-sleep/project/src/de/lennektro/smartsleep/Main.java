package de.lennektro.smartsleep;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
		FileConfiguration config = getConfig();
		if(!config.isSet("initialized")) {
			config.set("initialized", "---");
			config.set("required_divisor", 2);
			config.set("msg.laydown", "");
			config.set("msg.standup", "");
			config.set("msg.count", "§b%sleeping%§1/§b%required% §esleeping. (§b%online% §eonline)");
			config.set("msg.newmorning", "§aGood Morning!");
			saveConfig();
		}
	}
	
	
	public static int sleeping = 0;
	public static int required = -1;
	public static int online = -1;
	
	@EventHandler
	public void onPlayerLayDown(PlayerBedEnterEvent event) {
		if(!event.getBedEnterResult().equals(BedEnterResult.OK)) return;
		sleeping++;
		update();
		bMessage(getConfig().getString("msg.laydown"), event.getPlayer().getName());
	}
	
	@EventHandler
	public void onPlayerStandUp(PlayerBedLeaveEvent event) {
		sleeping--;
		if(event.getPlayer().getWorld().getTime() < 20) return;
		update();
		bMessage(getConfig().getString("msg.standup"), event.getPlayer().getName());
	}
	
	private static int skipTask;
	public void update() {
		online = Bukkit.getOnlinePlayers().size();
		required = online / getConfig().getInt("required_divisor");
		if(required < 1) required = 1;
		
		bMessage(getConfig().getString("msg.count"));
		
		if(sleeping == 0) Bukkit.getScheduler().cancelTask(skipTask);
		if(!(sleeping >= required)) return;
		
		skipTask = Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				for(World world : Bukkit.getWorlds()) world.setTime(0);
				Bukkit.broadcastMessage(getConfig().getString("msg.newmorning"));
			}
			
		}, 80);
	}
	
	
	public static void bMessage(String msg) {
		if(!msg.equals("")) 
			Bukkit.broadcastMessage(msg
					.replace("%sleeping%", "" + sleeping)
					.replace("%required%", "" + required)
					.replace("%online%", "" + online));
	}
	
	public static void bMessage(String msg, String pname) {
		if(!msg.equals("")) 
			Bukkit.broadcastMessage(msg
					.replace("%player%", pname)
					.replace("%sleeping%", "" + sleeping)
					.replace("%required%", "" + required)
					.replace("%online%", "" + online));
	}
}
