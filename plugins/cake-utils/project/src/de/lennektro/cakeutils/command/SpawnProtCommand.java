package de.lennektro.cakeutils.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.lennektro.cakeutils.handler.SpawnHandler;
import de.lennektro.cakeutils.util.LocationUtil;

public class SpawnProtCommand implements CommandExecutor, TabCompleter, Listener {

	private static final String SPROT_PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + "SpawnProt" + ChatColor.GRAY + "] " + ChatColor.RESET;
	
	private static List<UUID> editorsPos1 = new ArrayList<>();
	private static List<UUID> editorsPos2 = new ArrayList<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length != 1) return false;
		switch(args[0]) {
		case "enable":
			SpawnHandler.setEnabled(true);
			sender.sendMessage(SPROT_PREFIX + ChatColor.GREEN + "Spawn protection now active!");
			break;
		case "disable":
			SpawnHandler.setEnabled(false);
			sender.sendMessage(SPROT_PREFIX + ChatColor.YELLOW + "Spawn protection now inactive!");
			break;
		case "pos1":
			if(!(sender instanceof Player)) {
				sender.sendMessage(SPROT_PREFIX + ChatColor.RED + "Only possible as player!");
				return true;
			}
			editorsPos1.add(((Player) sender).getUniqueId());
			sender.sendMessage(SPROT_PREFIX + ChatColor.YELLOW + "Left click the block to set pos1.");
			break;
		case "pos2":
			if(!(sender instanceof Player)) {
				sender.sendMessage(SPROT_PREFIX + ChatColor.RED + "Only possible as player!");
				return true;
			}
			editorsPos2.add(((Player) sender).getUniqueId());
			sender.sendMessage(SPROT_PREFIX + ChatColor.YELLOW + "Left click the block to set pos2.");
			break;
		}
		return true;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(editorsPos1.contains(event.getPlayer().getUniqueId())) {
			event.setCancelled(true);
			SpawnHandler.setPos1(event.getBlock().getLocation());
			event.getPlayer().sendMessage(SPROT_PREFIX + ChatColor.GREEN + "Set pos1 to " + LocationUtil.locToString(event.getBlock().getLocation()));
			editorsPos1.remove(event.getPlayer().getUniqueId());
		}
		if(editorsPos2.contains(event.getPlayer().getUniqueId())) {
			event.setCancelled(true);
			SpawnHandler.setPos2(event.getBlock().getLocation());
			event.getPlayer().sendMessage(SPROT_PREFIX + ChatColor.GREEN + "Set pos2 to " + LocationUtil.locToString(event.getBlock().getLocation()));
			editorsPos2.remove(event.getPlayer().getUniqueId());
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) return Arrays.asList(new String[] {"enable", "disable", "pos1", "pos2"});
		return Collections.emptyList();
	}

}
