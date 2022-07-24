package de.lennektro.toosocial.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.lennektro.toosocial.manager.NameColorManager;

public class NColorCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length != 2) return false;
		Player target = Bukkit.getPlayerExact(args[0]);
		if(target == null) {
			sender.sendMessage(ChatColor.RED + "There is no such player as " + args[0] + " online!");
			return true;
		}
		if(args[1].equals("clear")) {
			NameColorManager.setColor(target, null);
			sender.sendMessage(ChatColor.GREEN + "Cleared name color of " + args[0]);
			return true;
		}
		ChatColor color = ChatColor.valueOf(args[1].toUpperCase());
		NameColorManager.setColor(target, color);
		sender.sendMessage(ChatColor.GREEN + "Now set to: " + color + args[0]);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> arg1 = new ArrayList<>();
		Bukkit.getOnlinePlayers().forEach(c -> arg1.add(c.getName()));
		if(args.length == 1) return arg1;
		
		List<String> arg2 = new ArrayList<>();
		for(ChatColor c : ChatColor.values()) if(!(
		c.equals(ChatColor.BOLD) 
		|| c.equals(ChatColor.ITALIC)
		|| c.equals(ChatColor.MAGIC)
		|| c.equals(ChatColor.RESET)
		|| c.equals(ChatColor.STRIKETHROUGH)
		|| c.equals(ChatColor.UNDERLINE)
		|| c.equals(ChatColor.BLACK)
		|| c.equals(ChatColor.DARK_GRAY)
		|| c.equals(ChatColor.GRAY))) 
			arg2.add(c.name().toLowerCase());
		arg2.add("clear");
		if(args.length == 2) return arg2;
		
		return Collections.emptyList();
	}

}
