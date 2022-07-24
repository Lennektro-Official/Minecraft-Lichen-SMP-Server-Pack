package de.lennektro.anyhat;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class HatCommand implements CommandExecutor,TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		if(player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
			player.sendMessage(ChatColor.RED + "No item in main hand!");
			return true;
		}
		if(player.getInventory().getHelmet() != null) {
			player.sendMessage(ChatColor.RED + "You are wearing a hat item!");
			return true;
		}
		player.getEquipment().setHelmet(player.getInventory().getItemInMainHand());
		player.getInventory().setItemInMainHand(null);
		player.sendMessage(ChatColor.GREEN + "Enjoy your hat!");
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return Collections.emptyList();
	}
}
