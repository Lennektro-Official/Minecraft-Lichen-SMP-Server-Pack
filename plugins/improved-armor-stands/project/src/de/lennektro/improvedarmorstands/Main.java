package de.lennektro.improvedarmorstands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent event) {
		if(!event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) return;
		if(!event.getPlayer().isSneaking()) return;
		event.setCancelled(true);
		ArmorStand stand = (ArmorStand) event.getRightClicked();
		openASInv(event.getPlayer(), stand);
	}
	
	private static Map<UUID, ArmorStand> standsIMap = new HashMap<>();
	
	private static final String ASINV_TILTLE = ChatColor.DARK_AQUA + "Armor Stand Inventory";
	
	public void openASInv(Player player, ArmorStand stand) {
		if(standsIMap.containsKey(player.getUniqueId())) standsIMap.replace(player.getUniqueId(), stand);
		else standsIMap.put(player.getUniqueId(), stand);
		Inventory inv = Bukkit.createInventory(null, 9, ASINV_TILTLE);
		inv.setItem(0, stand.getEquipment().getHelmet());
		inv.setItem(1, stand.getEquipment().getChestplate());
		inv.setItem(2, stand.getEquipment().getLeggings());
		inv.setItem(3, stand.getEquipment().getBoots());
		inv.setItem(5, stand.getEquipment().getItemInMainHand());
		inv.setItem(6, stand.getEquipment().getItemInOffHand());
		ItemStack pane = createIS(Material.GRAY_STAINED_GLASS_PANE, " ", "", false);
		inv.setItem(4, pane);
		for(int i = 7; i < 9; i++) inv.setItem(i, pane);
		player.openInventory(inv);
	}
	
	@EventHandler
	public void onASIInvClick(InventoryClickEvent event) {
		if(!event.getView().getTitle().equals(ASINV_TILTLE)) return;
		
		Inventory inv = event.getView().getTopInventory();
		Player player = (Player) event.getWhoClicked();
		ArmorStand stand = standsIMap.get(player.getUniqueId());
		
		if((event.getSlot() >= 7 || event.getSlot() == 4) && event.getClickedInventory().getSize() == 9) event.setCancelled(true);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				stand.getEquipment().setHelmet(inv.getItem(0));
				stand.getEquipment().setChestplate(inv.getItem(1));
				stand.getEquipment().setLeggings(inv.getItem(2));
				stand.getEquipment().setBoots(inv.getItem(3));
				stand.getEquipment().setItemInMainHand(inv.getItem(5));
				stand.getEquipment().setItemInOffHand(inv.getItem(6));
			}
		}.runTaskLater(this, 1);
	}
	
	@EventHandler
	public void onPunch(EntityDamageByEntityEvent event) {
		if(!event.getEntityType().equals(EntityType.ARMOR_STAND)) return;
		if(!event.getDamager().getType().equals(EntityType.PLAYER)) return;
		if(!((Player) event.getDamager()).isSneaking()) return;
		event.setCancelled(true);
		ArmorStand stand = (ArmorStand) event.getEntity();
		openASMenu((Player) event.getDamager(), stand);
	}
	
	private static Map<UUID, ArmorStand> standsMap = new HashMap<>();
	
	private static final String ASMENU_TILTLE = ChatColor.BLUE + "Armor Stand Menu";
	
	public void openASMenu(Player player, ArmorStand stand) {
		if(standsMap.containsKey(player.getUniqueId())) standsMap.replace(player.getUniqueId(), stand);
		else standsMap.put(player.getUniqueId(), stand);
		
		Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, ASMENU_TILTLE);
		inv.setItem(0, createIS(Material.SMOOTH_STONE_SLAB, ChatColor.BLUE + "Toggle Base Plate", "", stand.hasBasePlate()));
		inv.setItem(1, createIS(Material.STICK, ChatColor.BLUE + "Toggle Arms", "", stand.hasArms()));
		inv.setItem(2, createIS(Material.EGG, ChatColor.BLUE + "Toggle Small", "", stand.isSmall()));
		inv.setItem(3, createIS(Material.FEATHER, ChatColor.BLUE + "Toggle Gravity", "", !stand.hasGravity()));
		inv.setItem(4, createIS(Material.ARMOR_STAND, ChatColor.GREEN + "Poses", "", false));
		player.openInventory(inv);
	}
	
	@EventHandler
	public void onASMInvClick(InventoryClickEvent event) {
		if(!event.getView().getTitle().equals(ASMENU_TILTLE)) return;
		if(!event.getClickedInventory().getType().equals(InventoryType.HOPPER)) return;
		event.setCancelled(true);
		
		Inventory inv = event.getClickedInventory();
		Player player = (Player) event.getWhoClicked();
		ArmorStand stand = standsMap.get(player.getUniqueId());
		
		switch(event.getSlot()) {
		case 0:
			stand.setBasePlate(!stand.hasBasePlate());
			inv.setItem(0, setEnchanted(inv.getItem(0), stand.hasBasePlate()));
			break;
		case 1:
			stand.setArms(!stand.hasArms());
			inv.setItem(1, setEnchanted(inv.getItem(1), stand.hasArms()));
			break;
		case 2:
			stand.setSmall(!stand.isSmall());
			inv.setItem(2, setEnchanted(inv.getItem(2), stand.isSmall()));
			break;
		case 3:
			stand.setGravity(!stand.hasGravity());
			inv.setItem(3, setEnchanted(inv.getItem(3), !stand.hasGravity()));
			break;
		case 4:
			openASPMenu(player);
			break;
		}
	}
	
	private static final String ASPMENU_TILTLE = ChatColor.DARK_GREEN + "Armor Stand Poses";
	
	public void openASPMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, ASPMENU_TILTLE);
		inv.setItem(0, createIS(Material.ARMOR_STAND, ChatColor.GREEN + "Default", "", false));
		inv.setItem(1, createIS(Material.ARMOR_STAND, ChatColor.GREEN + "Cheer", "", false));
		inv.setItem(2, createIS(Material.ARMOR_STAND, ChatColor.GREEN + "Block", "", false));
		inv.setItem(3, createIS(Material.ARMOR_STAND, ChatColor.GREEN + "Fight", "", false));
		inv.setItem(4, createIS(Material.ARMOR_STAND, ChatColor.GREEN + "T-Pose", "", false));
		inv.setItem(5, createIS(Material.ARMOR_STAND, ChatColor.GREEN + "Helicopter", "", false));
		inv.setItem(6, createIS(Material.ARMOR_STAND, ChatColor.GREEN + "Dab", "", false));
		inv.setItem(7, createIS(Material.ARMOR_STAND, ChatColor.GREEN + "Chill'n", "", false));
		player.openInventory(inv);
	}
	
	@EventHandler
	public void onASPInvClick(InventoryClickEvent event) {
		if(!event.getView().getTitle().equals(ASPMENU_TILTLE)) return;
		if(event.getClickedInventory().getSize() != 9) return;
		event.setCancelled(true);
		
		Player player = (Player) event.getWhoClicked();
		ArmorStand stand = standsMap.get(player.getUniqueId());
		
		switch(event.getSlot()) {
		case 0:
			stand.setBodyPose(new EulerAngle(0, 0, 0));
			stand.setHeadPose(new EulerAngle(0.015, 0, 0));
			stand.setLeftArmPose(new EulerAngle(-0.18, 0, -0.18));
			stand.setLeftLegPose(new EulerAngle(-0.018, 0, -0.018));
			stand.setRightArmPose(new EulerAngle(-0.26, 0, 0.18));
			stand.setRightLegPose(new EulerAngle(0.018, 0, 0.018));
			break;
		case 1:
			stand.setBodyPose(new EulerAngle(0, 0, 0));
			stand.setHeadPose(new EulerAngle(0.015, 0, 0));
			stand.setLeftArmPose(new EulerAngle(3.4, 0, 0.48));
			stand.setLeftLegPose(new EulerAngle(-0.018, 0, -0.018));
			stand.setRightArmPose(new EulerAngle(3.4, 0, 5.8));
			stand.setRightLegPose(new EulerAngle(0.018, 0, 0.018));
			break;
		case 2:
			stand.setBodyPose(new EulerAngle(0, 0, 0));
			stand.setHeadPose(new EulerAngle(0.015, 0, 0));
			stand.setLeftArmPose(new EulerAngle(4.7, 0, 4));
			stand.setLeftLegPose(new EulerAngle(-0.018, 0, -0.018));
			stand.setRightArmPose(new EulerAngle(4.7, 0, 2.4));
			stand.setRightLegPose(new EulerAngle(0.018, 0, 0.018));
			break;
		case 3:
			stand.setBodyPose(new EulerAngle(0, 0, 0));
			stand.setHeadPose(new EulerAngle(0.015, 0, 0));
			stand.setLeftArmPose(new EulerAngle(4.8, 0.7, 0));
			stand.setLeftLegPose(new EulerAngle(-0.018, 0, -0.018));
			stand.setRightArmPose(new EulerAngle(4.8, 5.7, 0));
			stand.setRightLegPose(new EulerAngle(0.018, 0, 0.018));
			break;
		case 4:
			stand.setBodyPose(new EulerAngle(0, 0, 0));
			stand.setHeadPose(new EulerAngle(0.015, 0, 0));
			stand.setLeftArmPose(new EulerAngle(0, 0, 4.7));
			stand.setLeftLegPose(new EulerAngle(-0.018, 0, -0.018));
			stand.setRightArmPose(new EulerAngle(0, 0, 1.575));
			stand.setRightLegPose(new EulerAngle(0.018, 0, 0.018));
			break;
		case 5:
			stand.setBodyPose(new EulerAngle(0, 0, 0));
			stand.setHeadPose(new EulerAngle(0.015, 0, 0));
			stand.setLeftArmPose(new EulerAngle(0, 0, 4.7));
			stand.setLeftLegPose(new EulerAngle(0, 0, 4.7));
			stand.setRightArmPose(new EulerAngle(0, 0, 1.575));
			stand.setRightLegPose(new EulerAngle(0, 0, 1.575));
			break;
		case 6:
			stand.setBodyPose(new EulerAngle(0, 0, 0));
			stand.setHeadPose(new EulerAngle(0.015, 0, 0));
			stand.setLeftArmPose(new EulerAngle(4.3, 5.4, 0));
			stand.setLeftLegPose(new EulerAngle(-0.018, 0, -0.018));
			stand.setRightArmPose(new EulerAngle(4.3, 5.4, 0));
			stand.setRightLegPose(new EulerAngle(0.018, 0, 0.018));
			break;
		case 7:
			stand.setBodyPose(new EulerAngle(0, 0, 0));
			stand.setHeadPose(new EulerAngle(0.015, 0, 0));
			stand.setLeftArmPose(new EulerAngle(5.8, 0, 0));
			stand.setLeftLegPose(new EulerAngle(-0.018, 0, -0.018));
			stand.setRightArmPose(new EulerAngle(5.85, 5.53, 0.26));
			stand.setRightLegPose(new EulerAngle(0.018, 0, 0.018));
			break;
//		case 8:
//			sendPose("Body", stand.getBodyPose(), player);
//			sendPose("Head", stand.getHeadPose(), player);
//			sendPose("LeftArm", stand.getLeftArmPose(), player);
//			sendPose("LeftLeg", stand.getLeftLegPose(), player);
//			sendPose("RightArm", stand.getRightArmPose(), player);
//			sendPose("RightLeg", stand.getRightLegPose(), player);
//			break;
		}
		
		player.closeInventory();
	}
	
	public void sendPose(String name, EulerAngle angle, Player player) {
		player.sendMessage(name + ": " + angle.getX() + " " + angle.getY() + " " + angle.getZ());
	}
	
	public ItemStack createIS(Material mat, String name, String lore, boolean enchanted) {
		ItemStack is = new ItemStack(mat);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(name);
		if(!lore.equals("")) meta.setLore(Arrays.asList(lore.split("§0")));
		if(enchanted) {
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack setEnchanted(ItemStack is, boolean value) {
		ItemMeta meta = is.getItemMeta();
		if(value) {
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		} else {
			meta.removeEnchant(Enchantment.ARROW_DAMAGE);
			meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		is.setItemMeta(meta);
		return is;
	}
}
