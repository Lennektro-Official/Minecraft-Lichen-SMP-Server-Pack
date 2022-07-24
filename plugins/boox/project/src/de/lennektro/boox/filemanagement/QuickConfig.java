package de.lennektro.boox.filemanagement;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.lennektro.boox.Main;

public class QuickConfig {

	public File file;
	public FileConfiguration config;
	
	public QuickConfig(String name) {
		this.file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator + name + ".yml");
		
		if(!this.file.exists()) {
			this.file.getParentFile().mkdirs();
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.config = new YamlConfiguration();
		
		try {
			this.config.load(this.file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delete() {
		if(this.file.exists()) this.file.delete();
	}
}