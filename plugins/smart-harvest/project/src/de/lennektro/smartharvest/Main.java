package de.lennektro.smartharvest;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
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
	public void onClickCrop(PlayerInteractEvent event) {
		if(!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) 
		&& !event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)
		&& !event.getPlayer().getGameMode().equals(GameMode.ADVENTURE))) 
			return;
		
		Block block = event.getClickedBlock();
		if(block.getBlockData().getAsString().equals("minecraft:wheat[age=7]")) smartHarvest(block, "minecraft:wheat[age=0]");
		if(block.getBlockData().getAsString().equals("minecraft:beetroots[age=3]")) smartHarvest(block, "minecraft:beetroots[age=0]");
		if(block.getBlockData().getAsString().equals("minecraft:carrots[age=7]")) smartHarvest(block, "minecraft:carrots[age=0]");
		if(block.getBlockData().getAsString().equals("minecraft:potatoes[age=7]")) smartHarvest(block, "minecraft:potatoes[age=0]");
	}
	
	public static void smartHarvest(Block block, String toset) {
		block.breakNaturally();
		block.getLocation().getWorld().playSound(block.getLocation(), Sound.BLOCK_GRASS_BREAK, 0.8f, 1F);
		block.setBlockData(Bukkit.createBlockData(toset));
	}
}
