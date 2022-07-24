package de.lennektro.toosocial.filemanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.ChatColor;

import de.lennektro.toosocial.Main;

public class MotdsTxt {

	public static List<String> motds = new ArrayList<>();
	
	public static void processFile() {
		File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator + "motds.txt");
		
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write("//Leave this file blank if you don't want to have randomized motds\n");
				writer.write("//Use & for formatting codes");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try(Scanner scanner = new Scanner(file)) {
			while(scanner.hasNext()) {
				String line = scanner.nextLine();
				if(line.startsWith("//")) continue;
				motds.add(ChatColor.translateAlternateColorCodes('&', line).replace("\\n", "\n"));
			}
			
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}