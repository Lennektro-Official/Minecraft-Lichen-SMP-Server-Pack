package de.lennektro.decoheads.util;

import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class HeadGenerator {

	public static ItemStack getHeadOfPlayer(Player player) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setDisplayName("§e" + player.getName() + "'s Head");
        skullMeta.setOwnerProfile(player.getPlayerProfile());
        head.setItemMeta(skullMeta);
        return head;
	}
	
	public static ItemStack getHeadOfTValue(String name, String value) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setDisplayName("§e" + name);
        
        GameProfile prof = new GameProfile(UUID.randomUUID(), null);
        prof.getProperties().put("textures", new Property("textures", value));
        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, prof);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        head.setItemMeta(skullMeta);
        return head;
	}
}
