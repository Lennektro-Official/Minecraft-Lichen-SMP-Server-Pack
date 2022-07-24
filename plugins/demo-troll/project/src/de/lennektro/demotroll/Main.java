package de.lennektro.demotroll;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange.a;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("demotroll").setExecutor(new DemoTrollCommand());
		getCommand("demotroll").setTabCompleter(new DemoTrollCommand());
		getCommand("demotroll").setPermission("op.typ");
	}
	
	public static void sendDemoScreen(Player player) {
		PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(new a(5), 0);
		((CraftPlayer)player).getHandle().b.a(packet);
	}
}
