package de.lennektro.decoheads.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.lennektro.decoheads.util.HeadGenerator;

public class HeadEditor implements Listener {

	private static List<UUID> editingOnes = new ArrayList<>();
	
	@EventHandler
	public void onShifClickHead(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
		if(event.getPlayer().getInventory().getItemInMainHand() == null) return;
		if(!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.PLAYER_HEAD)) return;
		if(!event.getPlayer().isSneaking()) return;
		event.setCancelled(true);
		if(!editingOnes.contains(event.getPlayer().getUniqueId())) {
			editingOnes.add(event.getPlayer().getUniqueId());
			event.getPlayer().sendMessage(ChatColor.YELLOW + "Type the texture value into chat");
		} else {
			editingOnes.remove(event.getPlayer().getUniqueId());
			event.getPlayer().sendMessage(ChatColor.RED + "Cancelled input");
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if(!editingOnes.contains(event.getPlayer().getUniqueId())) return;
		event.setCancelled(true);
		int amount = event.getPlayer().getInventory().getItemInMainHand().getAmount();
		ItemStack is = HeadGenerator.getHeadOfTValue("Custom Head", event.getMessage());
		is.setAmount(amount);
		event.getPlayer().getInventory().setItemInMainHand(is);
		event.getPlayer().sendMessage(ChatColor.GREEN + "Apllied custom texture!");
		editingOnes.remove(event.getPlayer().getUniqueId());
	}
}
