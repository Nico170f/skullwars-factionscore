package me.nico.core.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nico.core.Main;
import net.md_5.bungee.api.ChatColor;

public class ReloadCMD implements CommandExecutor {
	
	private Main main;
	
	public ReloadCMD(Main main) {
		this.main = main;
	}

	public String cmd1 = "4freeCore";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			
			String reloadMSG = main.getConfig().getString("Reload");
			Player player = (Player) sender;
			if(player.hasPermission(main.getConfig().getString("Reload_permission"))) {
				
				try {
				main.reloadConfig();
				main.saveDefaultConfig();
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + "ERROR Reloading config");
					return false;
				}
				

				
				
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', reloadMSG));
				return true;
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("No_permission")));
				return true;
			}
		}else{
			return false;
		}
	}
}
