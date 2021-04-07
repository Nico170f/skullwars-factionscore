package me.nico.core.Events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitScheduler;

import me.nico.core.Main;
import net.md_5.bungee.api.ChatColor;

public class IPBlocker implements Listener {

	private Main main;

	public IPBlocker(Main main) {
		this.main = main;
	}

	@EventHandler
	public void PlayerChatEvent(AsyncPlayerChatEvent event) {

		if (main.getConfig().getString("Skullblock.enabled") == "false") {
			return;
		} else {

			Player player = event.getPlayer();
			String message = event.getMessage().toString();
			String permission = main.getConfig().getString("Skullblock.Config.Permission");

			char charA = '.';
			char charB = ',';
			char charC = '-';
			int dotCount = 0;
			int comCount = 0;
			int dashCount = 0;
			int nbrCount = 0;

			for (int i = 0; i < message.length(); i++) {
				if (message.charAt(i) == charA) {
					dotCount++;
				}
			}
			for (int i1 = 0; i1 < message.length(); i1++) {
				if (message.charAt(i1) == charB) {
					comCount++;
				}
			}
			for (int i2 = 0; i2 < message.length(); i2++) {
				if (message.charAt(i2) == charC) {
					dashCount++;
				}
			}
			for (int i3 = 0; i3 < message.length(); i3++) {
				if (Character.isDigit(message.charAt(i3))) {
					nbrCount++;
				}
			}

			if (nbrCount >= 8 && dotCount < 3 && comCount < 3 && dashCount < 3) {

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd HH:mm");
				LocalDateTime now = LocalDateTime.now();

				main.getLogsConfig().set("Users." + player.getUniqueId() + ".Suspicious." + dtf.format(now),
						event.getMessage());// event.getMessage()
				try {
					main.getLogsConfig().save(main.logsFile);
				} catch (Exception e) {
				}

				String sus = main.getConfig().getString("Skullblock.Messages.Suspicious");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', sus));
				event.setCancelled(true);

				for (Player all : Bukkit.getServer().getOnlinePlayers()) {
					if (all.hasPermission(permission)) {

						String s1 = main.getConfig().getString("Skullblock.Messages.Prefix");
						String s2 = main.getConfig().getString("Skullblock.Messages.SusBlocked");
						all.sendMessage(ChatColor.translateAlternateColorCodes('&',
								s1 + s2.replace("{PLAYER}", player.getName())));

						String option1 = main.getConfig().getString("Skullblock.Config.Log_message_to_chat"); // .getString("Messages.Chatlog");
						if (option1 == "true") {
							String s3 = main.getConfig().getString("Skullblock.Messages.Output");
							all.sendMessage(ChatColor.translateAlternateColorCodes('&', s3 + message));
						}
					}
				}

			} else if ((dotCount >= 3 && nbrCount >= 5) || (comCount >= 3 && nbrCount >= 5)
					|| (dashCount >= 3 && nbrCount >= 5)) {

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd HH:mm");
				LocalDateTime now = LocalDateTime.now();

				main.getLogsConfig().set("Users." + player.getUniqueId() + ".Blocked." + dtf.format(now),
						event.getMessage());// event.getMessage()
				try {
					main.getLogsConfig().save(main.logsFile);
				} catch (Exception e) {
				}

				event.setCancelled(true);
				ArrayList<String> hasPerm = new ArrayList<String>();
				for (Player all : Bukkit.getServer().getOnlinePlayers()) {
					if (all.hasPermission(permission)) {

						String pName = all.getName();
						hasPerm.add(pName);

						String s1 = main.getConfig().getString("Skullblock.Messages.Prefix");
						String s2 = main.getConfig().getString("Skullblock.Messages.Blocked");
						all.sendMessage(ChatColor.translateAlternateColorCodes('&',
								s1 + s2.replace("{PLAYER}", player.getName())));

						String messageLog = main.getConfig().getString("Skullblock.Config.Log_message_to_chat");
						if (messageLog == "true") {
							String s3 = main.getConfig().getString("Skullblock.Messages.Output");
							all.sendMessage(ChatColor.translateAlternateColorCodes('&', s3 + message));
						}
						if (main.getConfig().getString("Skullblock.Config.Notify_with_sound") == "true") {
							all.playSound(all.getLocation(), Sound.ORB_PICKUP, 1, 1);
						}
					}
				}

				// Running commands from config in console
				String consoleEnabled = main.getConfig().getString("Skullblock.Console_commands.enabled");
				if (consoleEnabled == "true") {
					ArrayList<String> cmdArray = new ArrayList<String>();
					List<String> cmds = main.getConfig().getStringList("Skullblock.Console_commands.list");
					for (String s : cmds) {
						cmdArray.add(s);
					}

					BukkitScheduler scheduler1 = main.getServer().getScheduler();
					scheduler1.scheduleSyncDelayedTask(this.main, new Runnable() {
						@Override
						public void run() {
							// player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
							cmdArray.forEach(
									(n) -> main.getServer().dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
											n.replace("{PLAYER}", player.getName())));
						}
					}, 1);
				}
			}
		}
	}
}
