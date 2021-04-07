package me.nico.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
			
			String prefix = main.getConfig().getString("Skullblock.Messages.Prefix");
			String reloadMSG = main.getConfig().getString("Skullblock.Messages.Reload");
			Player player = (Player) sender;
			if(player.hasPermission(main.getConfig().getString("Skullblock.Config.Permission"))) {
				main.reloadConfig();
				main.saveDefaultConfig();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + reloadMSG));
				return true;
			}
			return false;
		}else{
			return false;
		}
	}
}
