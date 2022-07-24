package de.lennektro.invshulkerbox;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener {

	private FileConfiguration config = getConfig();
	
	@Override
	public void onEnable() {
		if(!config.isSet("RightClickAdding")) {
			config.set("RightClickAdding", true);
			saveConfig();
		}
		if(!config.isSet("RightClickOpening")) {
			config.set("RightClickOpening", false);
			saveConfig();
		}
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	
	@EventHandler
	public void onInvclick(InventoryClickEvent event) {
		onShulkerOpen(event);
		onShulkerAdd(event);
		onShulkerClicked(event);
	}
	
	@SuppressWarnings("deprecation")
	public void onShulkerAdd(InventoryClickEvent event) {
		if(!config.getBoolean("RightClickAdding")) return;
		if(event.getView().getTopInventory().getType().equals(InventoryType.SHULKER_BOX)) return;
		if(!event.getClick().equals(ClickType.RIGHT)) return;
		if(event.getCursor() == null) return;
		if(event.getCursor().getType().equals(Material.AIR)) return;
		if(isShulkerBox(event.getCursor().getType())) return;
		if(event.getCurrentItem() == null) return;
		if(!isShulkerBox(event.getCurrentItem().getType())) return;
		event.setCancelled(true);
		
		BlockStateMeta bsm = (BlockStateMeta) event.getCurrentItem().getItemMeta();
		ShulkerBox sbox = (ShulkerBox) bsm.getBlockState();
		sbox.getInventory().addItem(event.getCursor());
		bsm.setBlockState(sbox);
		event.getCurrentItem().setItemMeta(bsm);
		event.setCursor(null);
	}
	
	private static Map<UUID, Integer> shulkerSlots = new HashMap<>();
	private static Map<UUID, Inventory> shulkerInvs = new HashMap<>();
	
	public void onShulkerOpen(InventoryClickEvent event) {
		if(!config.getBoolean("RightClickOpening")) return;
		if(!event.getClick().equals(ClickType.RIGHT)) return;
		if(!event.getCursor().getType().equals(Material.AIR)) return;
		if(event.getCurrentItem() == null) return;
		if(!isShulkerBox(event.getCurrentItem().getType())) return;
		event.setCancelled(true);
		
		Player player = (Player) event.getWhoClicked();
		
		BlockStateMeta bsm = (BlockStateMeta) event.getCurrentItem().getItemMeta();
		ShulkerBox sbox = (ShulkerBox) bsm.getBlockState();
		
		String title = "Shulker Box";
		if(!event.getCurrentItem().getType().equals(Material.SHULKER_BOX) && !event.getCurrentItem().getItemMeta().hasDisplayName()) {
			String colorNameRaw = sbox.getColor().toString();
			String[] colorNameSplits = colorNameRaw.split("_");
			String colorName = "";
			for(String s : colorNameSplits) colorName = colorName + s.substring(0, 1) + s.substring(1).toLowerCase() + " ";
			title = colorName + title;
		}
		if(event.getCurrentItem().getItemMeta().hasDisplayName()) title = event.getCurrentItem().getItemMeta().getDisplayName();
		
		Inventory inv = Bukkit.createInventory(null, InventoryType.SHULKER_BOX, title);
		inv.setContents(sbox.getInventory().getContents());
		new BukkitRunnable() {
			
			@Override
			public void run() {
				shulkerSlots.put(player.getUniqueId(), event.getSlot());
				shulkerInvs.put(player.getUniqueId(), inv);
				player.openInventory(shulkerInvs.get(player.getUniqueId()));
			}
		}.runTaskLater(this, 1);
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if(!shulkerSlots.containsKey(player.getUniqueId())) return;
		ItemStack is = player.getInventory().getItem(shulkerSlots.get(player.getUniqueId()));
		if(!isShulkerBox(is.getType())) return;
		BlockStateMeta bsm = (BlockStateMeta) is.getItemMeta();
		ShulkerBox sbox = (ShulkerBox) bsm.getBlockState();
		sbox.getInventory().setContents(shulkerInvs.get(player.getUniqueId()).getContents());
		bsm.setBlockState(sbox);
		is.setItemMeta(bsm);
		shulkerSlots.remove(player.getUniqueId());
		shulkerInvs.remove(player.getUniqueId());
	}
	
	public void onShulkerClicked(InventoryClickEvent event) {
		if(!event.getView().getTopInventory().getType().equals(InventoryType.SHULKER_BOX)) return;
		if(event.getCurrentItem() == null) return;
		if(!isShulkerBox(event.getCurrentItem().getType())) return;
		event.setCancelled(true);
	}
	
	private boolean isShulkerBox(Material mat) {
		switch (mat) {
		    case SHULKER_BOX:
			case LIGHT_GRAY_SHULKER_BOX:
			case BLACK_SHULKER_BOX:
			case BLUE_SHULKER_BOX:
			case BROWN_SHULKER_BOX:
			case CYAN_SHULKER_BOX:
			case GRAY_SHULKER_BOX:
			case GREEN_SHULKER_BOX:
			case LIGHT_BLUE_SHULKER_BOX:
			case LIME_SHULKER_BOX:
			case MAGENTA_SHULKER_BOX:
			case ORANGE_SHULKER_BOX:
			case PINK_SHULKER_BOX:
			case PURPLE_SHULKER_BOX:
			case RED_SHULKER_BOX:
			case WHITE_SHULKER_BOX:
			case YELLOW_SHULKER_BOX:
				return true;
			default:
				return false;
		}
	}
}
