package de.lennektro.cakeutils.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.lennektro.cakeutils.handler.CakeToolsHandler;

public class CakeToolsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return true;
		Player player = (Player) sender;
		player.getInventory().addItem(
				CakeToolsHandler.getIntBlockerItem(),
				CakeToolsHandler.getIntEnablerItem(),
				CakeToolsHandler.getBookSetterItem(),
				CakeToolsHandler.getBookRemoverItem());
		return true;
	}
}
