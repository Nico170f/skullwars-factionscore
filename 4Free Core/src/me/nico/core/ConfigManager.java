package me.nico.core;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class ConfigManager {
	
	private Main plugin = Main.getPlugin(Main.class);
	
	//Files and File config
	public FileConfiguration logscfg;
	public File logs;
	public FileConfiguration datacfg;
	public File data;
	//-----------------------------------
//	Plugin plugin;
//	Main main;
//	
//	public ConfigManager(Plugin plugin,Main main) {
//		this.plugin = plugin;
//		this.main = main;
//	}
	
	
	
	/*public void setup(){
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
			}
		logs = new File(plugin.getDataFolder(), "logs.yml");
		data = new File(plugin.getDataFolder(), "data.yml");
		
		if(!logs.exists()) {
			try {
				logs.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Created logs.yml file!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create logs.yml file");
				
			}
		}
		if(!data.exists()) {
			try {
				data.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Created data.yml file!");
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create data.yml file");
				
			}
		}
		
		datacfg = YamlConfiguration.loadConfiguration(data);
		logscfg = YamlConfiguration.loadConfiguration(logs);
	}*/
	
	public FileConfiguration getPlayers() {
		return logscfg;
	}
	
	/*public void saveLogs() {
		datacfg = YamlConfiguration.loadConfiguration(data);
		logscfg = YamlConfiguration.loadConfiguration(logs);
		
		
		try {
			logscfg.save(logs);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "logs.yml has been saved!");
		}catch(IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save logs.yml file");
		}
	}
	
	public void saveData() {
		try {
			datacfg.save(data);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "data.yml has been saved!");
		}catch(IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save data.yml file");
		}
	}*/
	
	
	
	/*public void reloadLogs() {
		
		datacfg = YamlConfiguration.loadConfiguration(data);
		logscfg = YamlConfiguration.loadConfiguration(logs);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "logs.yml and data.yml files has been reloaded!");
	}*/

	//public void set(String string, String message) {
		// TODO Auto-generated method stub
		
	//}
}
