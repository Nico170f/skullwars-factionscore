package me.nico.core.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.nico.core.Main;
import net.md_5.bungee.api.ChatColor;

public class ReclaimCMD implements CommandExecutor {
	
	private Main main;
	
	public ReclaimCMD(Main main) {
		this.main = main;
	}

	public String cmd1 = "reclaim";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;

			if(player.isOp()) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Reclaim.Messages.is_op")));
				return true;
			} 
			if(player.hasPermission("*")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Reclaim.Messages.has_*")));
				return true;
			}
			
			if(main.getDataConfig().contains("Users." + player.getUniqueId())) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Reclaim.Messages.already_claimed")));
				return true;
			}
			
			
			String rank;
			List<String> commands;
			if(player.hasPermission(main.getConfig().getString("Reclaim.Ranks.Sailor.permission"))) {
				rank = "Sailor";
				commands = main.getConfig().getStringList("Reclaim.Ranks.Sailor.commands");
			} else if(player.hasPermission(main.getConfig().getString("Reclaim.Ranks.Raider.permission"))) {
				rank = "Raider";
				commands = main.getConfig().getStringList("Reclaim.Ranks.Raider.commands");
			} else if(player.hasPermission(main.getConfig().getString("Reclaim.Ranks.Brute.permission"))) {
				rank = "Brute";
				commands = main.getConfig().getStringList("Reclaim.Ranks.Brute.commands");
			} else if(player.hasPermission(main.getConfig().getString("Reclaim.Ranks.Captain.permission"))) {
				rank = "Captain";
				commands = main.getConfig().getStringList("Reclaim.Ranks.Captain.commands");
			} else if(player.hasPermission(main.getConfig().getString("Reclaim.Ranks.Admiral.permission"))) {
				rank = "Admiral";
				commands = main.getConfig().getStringList("Reclaim.Ranks.Admiral.commands");
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Reclaim.Messages.not_ranked")));
				return true;
			}
			
			main.getDataConfig().set("Users." + player.getUniqueId(), true);
			try {
				main.getDataConfig().save(main.dataFile);
				
			} catch (Exception e) {
			player.sendMessage(ChatColor.RED + "An error has occured. Please report to staff");
			return false;
			}
			
			ArrayList<String> cmdArray = new ArrayList<String>();
			for (String s : commands) {
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
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Reclaim.Messages.success_claimed").replace("{RANK}", rank)));
				
				}
				
			}, 1);
			return true;
			
		}else{
			return false;
		}
	}
}
