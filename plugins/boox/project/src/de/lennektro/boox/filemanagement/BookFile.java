package de.lennektro.boox.filemanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.lennektro.boox.Main;
import de.lennektro.boox.util.BookInterface;

public class BookFile {

	public static void ensureFile(String filename) {
		File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator + "texts" + File.separator + filename);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void openBookFromFile(Player player, String filename) {
		BookInterface binterface = new BookInterface();
		
		File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator + "texts" + File.separator + filename);
		
		try(Scanner scanner = new Scanner(file)) {
			StringBuilder lines = new StringBuilder();
			
			int i = 0;
			while(scanner.hasNext()) {
				lines.append(ChatColor.translateAlternateColorCodes('&', scanner.nextLine()) + "\n");
				i++;
				if(i == 14) {
					binterface.writePage(lines.toString());
					lines = new StringBuilder();
					i = 0;
				}
			}
			
			if(i != 0) {
				binterface.writePage(lines.toString());
			}
			
			scanner.close();
		} catch (FileNotFoundException e) {
			binterface.writePage("§cCould not read file!");
		}
		
		binterface.openBook(player);
	}
}
