package me.nico.core.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.nico.core.Main;
import net.md_5.bungee.api.ChatColor;

public class LeaveCMD implements CommandExecutor {
	
	private Main main;
	
	public LeaveCMD(Main main) {
		this.main = main;
	}

	public String cmd1 = "leave";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			
			String world = main.getConfig().getString("LMSLeave.world");
			Player player = (Player) sender;
			
			if(main.getConfig().getBoolean("LMSLeave.enabled")) {
				
				if(player.getWorld().getName().equalsIgnoreCase(world)) {
					
					BukkitScheduler scheduler1 = main.getServer().getScheduler();
					scheduler1.scheduleSyncDelayedTask(this.main, new Runnable() {
						@Override
						public void run() {
							main.getServer().dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
									"kill {PLAYER}".replace("{PLAYER}", player.getName()));
						}
					}, 1);

				player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("LMSLeave.messages.teleported").replace("{WORLD}", world)));
				return true;
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("LMSLeave.messages.wrong-world").replace("{WORLD}", world)));;
					return true;
				}
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("LMSLeave.messages.cmd-disabled").replace("{WORLD}", world)));
				return true;
			}
		}
		return true;
	}
}
