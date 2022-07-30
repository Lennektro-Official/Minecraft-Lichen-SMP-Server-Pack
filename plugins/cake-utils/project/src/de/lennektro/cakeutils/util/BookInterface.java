package de.lennektro.cakeutils.util;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import net.minecraft.network.protocol.game.PacketPlayOutOpenBook;
import net.minecraft.world.EnumHand;

public class BookInterface {

	private ItemStack book;
	private BookMeta meta;
	
	public BookInterface() {
		book = new ItemStack(Material.WRITTEN_BOOK);
		meta = (BookMeta) book.getItemMeta();
		meta.setTitle("BookInterface");
		meta.setAuthor("BookInterfaceAPI");
	}
	
	public BookInterface writePage(String content) {
		meta.addPage(content);
		return this;
	}
	
	public void openBook(Player player) {
		book.setItemMeta(meta);
		
		int slot = player.getInventory().getHeldItemSlot();
        ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, book);
		((CraftPlayer) player).getHandle().b.a(new PacketPlayOutOpenBook(EnumHand.a));
		player.getInventory().setItem(slot, old);
	}
}
