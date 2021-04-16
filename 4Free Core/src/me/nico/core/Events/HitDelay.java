package me.nico.core.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.nico.core.Main;

public class HitDelay implements Listener {

	private Main main;

	public HitDelay(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onDamage(final EntityDamageByEntityEvent event) {

		if (main.getConfig().getBoolean("HitDelay.enabled") == false) {
			return;
		} else {
			Entity e = event.getEntity();
			if (e instanceof Player) {
				Player player = (Player) e;
				player.setMaximumNoDamageTicks(main.getConfig().getInt("HitDelay.delay"));
			}
		}

	}

}
