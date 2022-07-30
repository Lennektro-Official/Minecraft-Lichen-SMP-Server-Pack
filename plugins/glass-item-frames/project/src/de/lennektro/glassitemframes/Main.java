package de.lennektro.glassitemframes;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener {

	public static ItemStack glass_frame;
	public static NamespacedKey glassFrameKey;
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
		FileConfiguration config = getConfig();
		if(!config.isSet("recipe")) {
			config.set("recipe.keys", Arrays.asList(new String[] {"G", "I"}));
			config.set("recipe.G", "GLASS_PANE");
			config.set("recipe.I", "ITEM_FRAME");
			config.set("recipe.row1", "GGG");
			config.set("recipe.row2", "GIG");
			config.set("recipe.row3", "GGG");
			saveConfig();
		}
		if(!config.isSet("modeldata")) {
			config.set("modeldata", 1);
			saveConfig();
		}
		
		glassFrameKey = new NamespacedKey(this, "glass_frame");
		
		glass_frame = new ItemStack(Material.ITEM_FRAME);
		ItemMeta meta = glass_frame.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setDisplayName("§fGlass Item Frame");
		meta.getPersistentDataContainer().set(glassFrameKey, PersistentDataType.BYTE, (byte) 1);
		meta.setCustomModelData(config.getInt("modeldata"));
		glass_frame.setItemMeta(meta);
		
		NamespacedKey key = new NamespacedKey(this, "glass_item_frame");
		ShapedRecipe recipe = new ShapedRecipe(key, glass_frame);
		recipe.shape(config.getString("recipe.row1"), config.getString("recipe.row2"), config.getString("recipe.row3"));
		for(String k : config.getStringList("recipe.keys")) recipe.setIngredient(k.toCharArray()[0], Material.valueOf(config.getString("recipe." + k)));
		Bukkit.getServer().addRecipe(recipe);
	}
	
	@EventHandler
	public void onPlaceFrame(HangingPlaceEvent event) {
		Player player = event.getPlayer();
		ItemStack item;
		
		if(player.getInventory().getItemInMainHand() != null) {
			item = player.getInventory().getItemInMainHand();
		} else if(player.getInventory().getItemInOffHand() != null) {
			item = player.getInventory().getItemInOffHand();
		} else {
			item = null;
		}
		
		if(!(event.getEntity().getType().equals(EntityType.ITEM_FRAME) && item != null)) 
			return;
		if(!(item.getType().equals(Material.ITEM_FRAME) 
		&& item.getItemMeta().getPersistentDataContainer().has(glassFrameKey, PersistentDataType.BYTE))) 
			return;
		
		ItemFrame itemFrame = (ItemFrame) event.getEntity();
		itemFrame.getPersistentDataContainer().set(glassFrameKey, PersistentDataType.BYTE, (byte) 1);
		itemFrame.setCustomName("glass_item_frame");
		itemFrame.setGlowing(true);
		itemFrame.setVisible(true);
	}
	
	@EventHandler
	public void onBreakFrame(HangingBreakEvent event) {
		if(!(event.getEntity().getType().equals(EntityType.ITEM_FRAME) 
		&& event.getEntity().getPersistentDataContainer().has(glassFrameKey, PersistentDataType.BYTE))) 
			return;
		
		event.setCancelled(true);
		event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation().add(event.getEntity().getFacing().getDirection().multiply(0.25D)), glass_frame);
		event.getEntity().remove();
	}
	
	@EventHandler
	public void onPutItem(PlayerInteractEntityEvent event) {
		if(!(event.getRightClicked().getType().equals(EntityType.ITEM_FRAME) 
		&& event.getRightClicked().getPersistentDataContainer().has(glassFrameKey, PersistentDataType.BYTE))) 
			return;
		
		ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
		new BukkitRunnable() {

			@Override
			public void run() {
				if(itemFrame.getItem() != null && !itemFrame.getItem().getType().equals(Material.AIR)) {
					itemFrame.setGlowing(false);
					itemFrame.setVisible(false);
				}
			}
			
		}.runTaskLater(this, 2);
	}
	
	@EventHandler
	public void onTakeItem(EntityDamageByEntityEvent event) {
		if(!(event.getEntity().getType().equals(EntityType.ITEM_FRAME) 
		&& event.getEntity().getPersistentDataContainer().has(glassFrameKey, PersistentDataType.BYTE))) 
			return;
		
		ItemFrame itemFrame = (ItemFrame) event.getEntity();
		new BukkitRunnable() {

			@Override
			public void run() {
				if(itemFrame.getItem() == null || itemFrame.getItem().getType().equals(Material.AIR)) {
					itemFrame.setGlowing(true);
					itemFrame.setVisible(true);
				}
			}
			
		}.runTaskLater(this, 2);
	}
}
