package me.nico.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.nico.core.Events.BucketEmpty;
import me.nico.core.Events.DeathAnnounce;
import me.nico.core.Events.IPBlocker;
import me.nico.core.Events.LmsFix;
import me.nico.core.Events.PlayerDamageEvent;
import me.nico.core.Events.Testing;

public class Main extends JavaPlugin {
	private ConfigManager cfgm;

	public File logsFile;
	private FileConfiguration logsConfig;
	public File dataFile;
	private FileConfiguration dataConfig;

	
	
	public List<Integer[]> ints = new ArrayList<>();

	@Override
	public void onEnable() {

		Integer[] l1 = new Integer[]{ -1, 0, 0}; 
		Integer[] l2 = new Integer[]{ 0, 0, -1};
		Integer[] l3 = new Integer[]{ 1, 0, 0};
		Integer[] l4 = new Integer[]{ 0, 0, 1};
		Integer[] l5 = new Integer[]{ 0, -1, 0}; //down
		Integer[] l6 = new Integer[]{ 0, 1, 0}; //up
		ints.add(l1);
		ints.add(l2);
		ints.add(l3);
		ints.add(l4);
		ints.add(l5);
		ints.add(l6);
		

		createCustomConfig();
		
		
		LoadConfigManager();
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();

		
		
		
		
		loadConfig();
		loadData(); //PlayerDamageEvent

		getCommand("mbucket").setExecutor((CommandExecutor) new MBucketCMD(this));
		getCommand("4freeCore").setExecutor((CommandExecutor) new ReloadCMD(this));
		getCommand("leave").setExecutor((CommandExecutor) new LeaveCMD(this));
		getServer().getPluginManager().registerEvents(new IPBlocker(this), this);
		getServer().getPluginManager().registerEvents(new DeathAnnounce(this), this);
		getServer().getPluginManager().registerEvents(new BucketEmpty(this), this);
		getServer().getPluginManager().registerEvents(new LmsFix(this), this);
		getServer().getPluginManager().registerEvents(new Testing(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDamageEvent(this), this);


		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "4Free Core has been enabled!");

	}
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "4Free Core has been disabled!");

	}

	public void LoadConfigManager() {
		cfgm = new ConfigManager();
		//cfgm.setup();
		//cfgm.saveLogs();
		//cfgm.reloadLogs();
		
		//cfgm.saveData();
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		
	}
	
	public void loadData() {
		getDataConfig().options().copyDefaults(true);
		//cfgm.saveData();
	}

	private void createCustomConfig() {
		logsFile = new File(getDataFolder(), "logs.yml");
		if (!logsFile.exists()) {
			logsFile.getParentFile().mkdirs();
			saveResource("logs.yml", false);
		}

		logsConfig = new YamlConfiguration();
		try {
			logsConfig.load(logsFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

	
		dataFile = new File(getDataFolder(), "data.yml");
		if (!dataFile.exists()) {
			dataFile.getParentFile().mkdirs();
			saveResource("data.yml", false);
		}

		dataConfig = new YamlConfiguration();
		try {
			dataConfig.load(dataFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	
		
	}
	
//	private void createCustomData() {
//		dataFile = new File(getDataFolder(), "data.yml");
//		if (!dataFile.exists()) {
//			dataFile.getParentFile().mkdirs();
//			saveResource("data.yml", false);
//		}
//
//		dataConfig = new YamlConfiguration();
//		try {
//			dataConfig.load(logsFile);
//		} catch (IOException | InvalidConfigurationException e) {
//			e.printStackTrace();
//		}
//	}
	

	public FileConfiguration getLogsConfig() {
		return this.logsConfig;
	}
	public FileConfiguration getDataConfig() {
		return this.dataConfig;
	}

}
