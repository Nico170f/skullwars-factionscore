package me.nico.core;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.nico.core.Events.IPBlocker;
import me.nico.core.Events.DeathAnnounce;

public class Main extends JavaPlugin {
	private ConfigManager cfgm;
	
	
    public File logsFile;
    private FileConfiguration logsConfig;
    
	public void onEnable() {
		

		createCustomConfig();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        
		LoadConfigManager();
		loadConfig();

		
		getCommand("4freeCore").setExecutor((CommandExecutor) new ReloadCMD(this));
//		getCommand("giveitem").setExecutor((CommandExecutor) new Commands());
//		getCommand(commands.cmd1).setExecutor(commands);
//		getCommand(commands.cmd1).setExecutor(commands);
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "4Free Core has been enabled!");
		
		getServer().getPluginManager().registerEvents(new IPBlocker(this), this);
		getServer().getPluginManager().registerEvents(new DeathAnnounce(this), this);

	}
	
	public void OnDiable() {
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "4Free Core has been disabled!");
		
	}
	
	public void LoadConfigManager() {
		cfgm = new ConfigManager();
		cfgm.setup();
		cfgm.saveLogs();
		cfgm.reloadLogs();
	}
	
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
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
    }
	
    public FileConfiguration getLogsConfig() {
        return this.logsConfig;
    }
	
	
	
	
	
	
	
	
	
}
