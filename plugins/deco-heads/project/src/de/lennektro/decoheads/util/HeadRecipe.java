package de.lennektro.decoheads.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import de.lennektro.decoheads.Main;

public class HeadRecipe {

	public static void register() {
		NamespacedKey key = new NamespacedKey(Main.getInstance(), "fake_player_head");
		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.PLAYER_HEAD));
		recipe.shape("TTT", "TDT", "TTT");
		recipe.setIngredient('T', Material.TERRACOTTA);
		recipe.setIngredient('D', Material.DIAMOND);
		Bukkit.getServer().addRecipe(recipe);
	}
}
