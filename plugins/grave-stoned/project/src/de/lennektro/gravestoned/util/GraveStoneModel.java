package de.lennektro.gravestoned.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class GraveStoneModel {

	private static final String ID_PREFIX = "293gpd34s";
	
	public static void place(Location location, String id) {
		World world = location.getWorld();
		Location blockPos = location.getBlock().getLocation();
		
		ArmorStand base = (ArmorStand) world.spawnEntity(blockPos.clone().add(0.55, -1.75, 0.5), EntityType.ARMOR_STAND);
		setupStand(base);
		base.addScoreboardTag(ID_PREFIX + id);
		base.getEquipment().setHelmet(new ItemStack(Material.COARSE_DIRT));
		
		ArmorStand stone = (ArmorStand) world.spawnEntity(blockPos.clone().add(0.25, -1.25, 0.5), EntityType.ARMOR_STAND);
		setupStand(stone);
		stone.addScoreboardTag(ID_PREFIX + id);
		stone.getEquipment().setHelmet(new ItemStack(Material.COBBLESTONE_WALL));
	}
	
	public static boolean remove(String id) {
		boolean feedback = false;
		for(World world : Bukkit.getWorlds()) {
			for(Entity entity : world.getEntities()) {
				if(!(entity instanceof ArmorStand)) continue;
				if(!entity.getScoreboardTags().contains(ID_PREFIX + id)) continue;
				entity.remove();
				feedback = true;
			}
		}
		return feedback;
	}
	
	
	private static void setupStand(ArmorStand stand) {
		stand.setInvisible(true);
		stand.setMarker(true);
		stand.setSilent(true);
		stand.setBasePlate(false);
		stand.setInvulnerable(true);
		stand.setGravity(false);
	}
}
