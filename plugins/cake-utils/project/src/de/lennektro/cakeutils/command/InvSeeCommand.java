package de.lennektro.cakeutils.command;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class InvSeeCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length != 1) return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can execute this command!");
			return true;
		}
		Player target = Bukkit.getPlayerExact(args[0]);
		if(target == null) {
			sender.sendMessage(ChatColor.RED + "There is no such player as " + args[0] + " online!");
			return true;
		}
		((Player) sender).openInventory(target.getInventory());
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) return null;
		return Collections.emptyList();
	}

}
