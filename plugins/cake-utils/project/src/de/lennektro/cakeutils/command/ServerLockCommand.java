package de.lennektro.cakeutils.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.lennektro.cakeutils.handler.ServerLockHandler;

public class ServerLockCommand implements CommandExecutor, TabCompleter {

	private static final String SLOCK_PREFIX = ChatColor.GRAY + "[" + ChatColor.YELLOW + "ServerLock" + ChatColor.GRAY + "] " + ChatColor.RESET;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) return false;
		switch(args[0]) {
		case "lock":
			if(sender instanceof Player) ServerLockHandler.addAllowed((Player) sender);
			ServerLockHandler.setLocked(true);
			sender.sendMessage(SLOCK_PREFIX + ChatColor.YELLOW + "Server now locked!");
			break;
		case "unlock":
			ServerLockHandler.setLocked(false);
			sender.sendMessage(SLOCK_PREFIX + ChatColor.GREEN + "Server now unlocked!");
			break;
		case "allow":
			if(args.length != 2) return false;
			OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
			if(player == null) {
				sender.sendMessage(SLOCK_PREFIX + ChatColor.RED + "There is no such player as " + args[1] + " registered on this server.");
				return true;
			}
			ServerLockHandler.addAllowed(player);
			sender.sendMessage(SLOCK_PREFIX + ChatColor.GREEN + "Added " + args[1] + " to allowed players.");
			break;
		case "disallow":
			if(args.length != 2) return false;
			OfflinePlayer player2 = Bukkit.getOfflinePlayer(args[1]);
			if(player2 == null) {
				sender.sendMessage(SLOCK_PREFIX + ChatColor.RED + "There is no such player as " + args[1] + " registered on this server.");
				return true;
			}
			ServerLockHandler.removeAllowed(player2);
			sender.sendMessage(SLOCK_PREFIX + ChatColor.YELLOW+ "Removed " + args[1] + " from allowed players.");
			break;
		case "setreason":
			if(args.length < 2) return false;
			String reason = "";
			for(int i = 1; i < args.length; i++) reason = reason + args[i] + " ";
			reason = ChatColor.translateAlternateColorCodes('&', reason).trim();
			ServerLockHandler.setReason(reason);
			sender.sendMessage(SLOCK_PREFIX + ChatColor.YELLOW + "Set reason to " + ChatColor.RESET + reason);
			break;
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) return Arrays.asList(new String[] {"lock", "unlock", "allow", "disallow", "setreason"});
		if(args.length == 2 && (args[0].equals("allow") || args[0].equals("disallow"))) return null;
		return Collections.emptyList();
	}

}
