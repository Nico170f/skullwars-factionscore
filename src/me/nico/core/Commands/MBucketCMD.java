package me.nico.core.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.nico.core.Main;
import net.md_5.bungee.api.ChatColor;

public class MBucketCMD implements CommandExecutor {
	
	private Main main;
	
	public MBucketCMD(Main main) {
		this.main = main;
	}

	public String cmd1 = "mbucket";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage(main.getConfig().getString(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("MegaBucket.messages.console_only"))));
			return true;
		}else{
			if(args.length == 1) {  
				Player player; 
				try {
					player = Bukkit.getServer().getPlayer(args[0]);
				} catch (Exception e) {
					main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "ERR: Finding player");
					return false;
				}
			
				if(player == null) {
					main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Player: " + ChatColor.WHITE + args[0] + ChatColor.RED + " was not found!");
					return false;
				}
				
				PlayerInventory inventory = player.getInventory();
				ItemStack item = new ItemStack(Material.WATER_BUCKET, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("MegaBucket.item.name")));
				ArrayList<String> loreArray = new ArrayList<String>();
				List<String> lores = main.getConfig().getStringList("MegaBucket.item.lore");
				
				for(String l : lores) {
					//loreArray.add(l.replace("{COST}", main.getConfig().getString("MegaBucket.config.cost")));
					loreArray.add(ChatColor.translateAlternateColorCodes('&', l.replace("{COST}", main.getConfig().getString("MegaBucket.config.cost"))));
				}
				//loreArray.forEach((x) -> finalLores.add(x));
		        meta.setLore(loreArray);
		        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		        item.setItemMeta(meta);

				inventory.addItem(item);
				main.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + player.getDisplayName() + ChatColor.GREEN + " was given a Mega Bucket!");
				return true;
				
			}
			main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Correct Syntax: /mbucket <IGN>");
			return true;
		}
	}
}