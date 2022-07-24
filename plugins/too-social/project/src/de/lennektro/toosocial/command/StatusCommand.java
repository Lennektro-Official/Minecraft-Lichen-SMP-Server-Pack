package de.lennektro.toosocial.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.lennektro.toosocial.manager.StatusManager;

public class StatusCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		if(!(args.length == 1 || args.length == 3)) return false;
		Player player = (Player) sender;
		switch(args[0]) {
		case "reset":
			StatusManager.reset(player);
			player.sendMessage(ChatColor.DARK_GREEN + "Your status is now resetted");
			break;
		case "set":
			StatusManager.set(player, ChatColor.valueOf(args[2].toUpperCase()) + args[1]);
			player.sendMessage(ChatColor.DARK_GREEN + "Your status is now set to " + StatusManager.get(player));
			break;
		default:
			return false;
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) return Arrays.asList(new String[] {"set", "reset"});
		if(args.length >= 2 && args[0].equals("reset")) return Collections.emptyList();
		if(args.length == 2) return Arrays.asList(new String[] {"<status>"});
		List<String> arg3 = new ArrayList<>();
		for(ChatColor c : ChatColor.values()) if(!(
		c.equals(ChatColor.BOLD) 
		|| c.equals(ChatColor.ITALIC)
		|| c.equals(ChatColor.MAGIC)
		|| c.equals(ChatColor.RESET)
		|| c.equals(ChatColor.STRIKETHROUGH)
		|| c.equals(ChatColor.UNDERLINE)
		|| c.equals(ChatColor.WHITE)
		|| c.equals(ChatColor.BLACK)
		|| c.equals(ChatColor.DARK_GRAY)
		|| c.equals(ChatColor.GRAY))) 
			arg3.add(c.name().toLowerCase());
		if(args.length == 3) return arg3;
		return Collections.emptyList();
	}
}
