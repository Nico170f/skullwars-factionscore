package me.nico.core.Events;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

import me.nico.core.Main;
import net.md_5.bungee.api.ChatColor;

public class LmsFix implements Listener {

	private Main main;
	// private Plugin plugin = IPBlocker.getPlugin(IPBlocker.class);

	public LmsFix(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		//Location location = event.getPlayer().getLocation();
		// Bukkit.broadcastMessage("user joined: " + player.getName());

		if(main.getConfig().getBoolean("LMSFix.enabled")) {

			String permission = main.getConfig().getString("LMSFix.bypass_permission");
			if (player.hasPermission(permission)) {
				player.sendMessage(main.getConfig().getString("LMSFix.bypass_message"));
				return;
			} else {

				String loginWorld = main.getConfig().getString("LMSFix.world");
				String sendMsg = main.getConfig().getString("LMSFix.chat_message");
				if (player.getWorld().getName().equalsIgnoreCase(loginWorld)) {
					String cmd = main.getConfig().getString("LMSFix.command");
					BukkitScheduler scheduler1 = main.getServer().getScheduler();
					scheduler1.scheduleSyncDelayedTask(this.main, new Runnable() {
						@Override
						public void run() {
							main.getServer().dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
									cmd.replace("{PLAYER}", player.getName()));
							player.sendMessage(ChatColor.translateAlternateColorCodes('&',
									sendMsg.replace("{WORLD}", loginWorld)));
						}
					}, 1);

				}
			}
		} else {
			return;
		}
	}

}
