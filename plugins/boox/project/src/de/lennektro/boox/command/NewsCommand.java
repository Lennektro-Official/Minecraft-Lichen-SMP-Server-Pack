package de.lennektro.boox.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.lennektro.boox.filemanagement.BookFile;
import de.lennektro.boox.handler.SeenTracker;

public class NewsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		BookFile.openBookFromFile((Player) sender, "news.txt");
		SeenTracker.markAndCheckSeenNews((Player) sender);
		return true;
	}

}
