package de.lennektro.decoheads.handler;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.lennektro.decoheads.Main;
import de.lennektro.decoheads.util.HeadGenerator;

public class PlayerDeathHandler implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(!Main.getInstance().getConfig().getBoolean("dropHeadOnPlayerDeath")) return;
		Location loc = event.getEntity().getLocation();
		loc.getWorld().dropItemNaturally(loc, HeadGenerator.getHeadOfPlayer(event.getEntity()));
	}
}
