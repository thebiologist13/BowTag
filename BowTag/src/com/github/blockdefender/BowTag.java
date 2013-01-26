package com.github.blockdefender;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BowTag extends JavaPlugin {
	
	private boolean debug; 
	
	private FileConfiguration config;
	
	private File configFile;
	
	private Logger log;
	
	private Util util;
	
	public void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			log.severe("Could not copy config from jar!");
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getCustomConfig() {
		if (config == null) {
			reloadCustomConfig();
		}
		return config;
	}
	
	public void onDisable() {
		util.log("BowTag has been disabled!");
	}
	
	public void onEnable() {
		
		log = getLogger();
		
		util = new Util(this);
		
		config = getCustomConfig();
		
		debug = config.getBoolean("debug", false);
		
		List<String> authors = this.getDescription().getAuthors();
		String aStr = "";
		for(int i = 0; i < authors.size(); i++) {
			if(i == 0)
				aStr += authors.get(0);
			else
				aStr += ", " + authors.get(i);
		}
		util.log("BowTag by " + aStr + " has been enabled!");
		
	}
	
	public void reloadCustomConfig() {
		if (configFile == null) {
			configFile = new File(getDataFolder(), "config.yml");

			if(!configFile.exists()){
				configFile.getParentFile().mkdirs();
				copy(getResource("config.yml"), configFile);
			}

		}

		config = YamlConfiguration.loadConfiguration(configFile);

		// Look for defaults in the jar
		InputStream defConfigStream = this.getResource("config.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			config.options().copyDefaults(true);
			config.setDefaults(defConfig);
		}

	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("bowtag")){
			player.sendMessage(ChatColor.RED + "Play Bow Tag on The Block Defender!");
		}
		
		
		return false;
		
	}
	
	public boolean getDebug() {
		return this.debug;
	}
	
	public Util getUtil() {
		return this.util;
	}

}
