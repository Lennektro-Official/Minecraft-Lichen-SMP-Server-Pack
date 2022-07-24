package de.lennektro.demotroll;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class DemoTrollCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length != 1) return false;
		Player target = Bukkit.getPlayerExact(args[0]);
		if(target == null) {
			sender.sendMessage(ChatColor.RED + "There is no such player as " + args[0] + " online");
			return true;
		}
		Main.sendDemoScreen(target);
		sender.sendMessage(ChatColor.GREEN + "Demotrolled " + args[0] + "!");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) return null;
		return Collections.emptyList();
	}

}
