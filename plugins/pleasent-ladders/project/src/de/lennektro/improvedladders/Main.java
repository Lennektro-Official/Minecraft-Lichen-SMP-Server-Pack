package de.lennektro.improvedladders;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	
	@EventHandler
	public void onLadderInteract(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) 
		|| event.getClickedBlock() == null
		|| event.getItem() == null) 
			return;
		if(!event.getClickedBlock().getType().equals(Material.LADDER)
		|| !event.getItem().getType().equals(Material.LADDER)) 
			return;
		
		event.setCancelled(true);
		
		Location loc = event.getClickedBlock().getLocation();
		while(loc.getBlock().getType().equals(Material.LADDER)) loc.subtract(0, 1, 0);
		
        if(!loc.getBlock().getType().isAir()) return;
        loc.getBlock().setBlockData(event.getClickedBlock().getBlockData());
		loc.getWorld().playSound(loc, Sound.BLOCK_LADDER_PLACE, 1f, 1f);
		if(event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) event.getItem().setAmount(event.getItem().getAmount() - 1);
	}
}
