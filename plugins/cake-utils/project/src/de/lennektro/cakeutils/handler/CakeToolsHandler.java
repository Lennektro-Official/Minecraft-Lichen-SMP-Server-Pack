package de.lennektro.cakeutils.handler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.lennektro.cakeutils.filemanagement.BookFile;
import de.lennektro.cakeutils.util.LocationUtil;

public class CakeToolsHandler implements Listener {

	public static ItemStack getIntBlockerItem() {
		ItemStack is = new ItemStack(Material.BARRIER);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Interaction Blocker");
		is.setItemMeta(meta);
		return is;
	}
	
	public static ItemStack getIntEnablerItem() {
		ItemStack is = new ItemStack(Material.LIME_DYE);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Interaction Unblocker");
		is.setItemMeta(meta);
		return is;
	}
	
	public static ItemStack getBookSetterItem() {
		ItemStack is = new ItemStack(Material.KNOWLEDGE_BOOK);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "SBook Marker");
		is.setItemMeta(meta);
		return is;
	}
	
	public static ItemStack getBookRemoverItem() {
		ItemStack is = new ItemStack(Material.BOOK);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "SBook Mark Remover");
		is.setItemMeta(meta);
		return is;
	}
	
	@EventHandler
	public static void onInteract(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(event.getItem() == null) return;
		if(!event.getItem().hasItemMeta()) return;
		if(!event.getItem().getItemMeta().hasDisplayName()) return;
		String name = event.getItem().getItemMeta().getDisplayName();
		Location loc = event.getClickedBlock().getLocation();
		String sLoc = LocationUtil.locToString(loc);
		Player player = event.getPlayer();
		if(name.equals(ChatColor.RED + "" + ChatColor.BOLD + "Interaction Blocker")) {
			event.setCancelled(true);
			InteractionHandler.setAllowed(loc, false);
			player.sendMessage(ChatColor.YELLOW + "Blocked " + sLoc);
			return;
		}
		if(name.equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Interaction Unblocker")) {
			event.setCancelled(true);
			InteractionHandler.setAllowed(loc, true);
			player.sendMessage(ChatColor.YELLOW + "Unblocked " + sLoc);
			return;
		}
		if(name.equals(ChatColor.GREEN + "" + ChatColor.BOLD + "SBook Marker")) {
			event.setCancelled(true);
			InteractionHandler.setSBook(loc, true);
			player.sendMessage(ChatColor.YELLOW + "Made " + sLoc + " to an SBook, interact to generate file");
			return;
		}
		if(name.equals(ChatColor.RED + "" + ChatColor.BOLD + "SBook Mark Remover")) {
			event.setCancelled(true);
			InteractionHandler.setSBook(loc, false);
			BookFile.delete(sLoc + ".txt");
			player.sendMessage(ChatColor.YELLOW + "Made " + sLoc + " no longer an SBook");
			return;
		}
	}
}
