package com.github.blockdefender;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {
	
	private final BowTag PLUGIN;
	
	private final Logger LOG;

	public Util(BowTag plugin) {
		this.PLUGIN = plugin;
		this.LOG = plugin.getLogger();
	}
	
	public void sendMessage(CommandSender sender, String message) {

		if(sender == null) 
			return;

		Player p = null;

		if(sender instanceof Player)
			p = (Player) sender;

		if(p == null) {
			message = "[BowTag] " + ChatColor.stripColor(message);
			LOG.info(message);
		} else {
			p.sendMessage(message);
		}

	}

	public void sendMessage(CommandSender sender, String[] message) {

		if(sender == null) 
			return;

		Player p = null;

		if(sender instanceof Player)
			p = (Player) sender;

		if(p == null) {

			for(String s : message) {
				s = "[BowTag] " + ChatColor.stripColor(s);
				LOG.info(s);
			}

		} else {
			p.sendMessage(message);
		}

	}
	
	public void printDebugMessage(String message) {
		if(PLUGIN.getDebug()) {
			LOG.info("[BT_DEBUG] " + message);
		}

	}
	
	public void printDebugMessage(String message, Class<?> clazz) {
		if(PLUGIN.getDebug()) {
			if(clazz != null) {
				LOG.info("[BT_DEBUG] " + clazz.getName() + ": " + message);
			} else {
				LOG.info("[BT_DEBUG] " + message);
			}

		}

	}

	public void printDebugMessage(String[] message) {
		if(PLUGIN.getDebug()) {
			for(String s : message) {
				printDebugMessage(s);
			}
		}
	}

	public void printDebugMessage(String[] message, Class<?> clazz) {
		if(PLUGIN.getDebug()) {
			for(String s : message) {
				printDebugMessage(s, clazz);
			}
		}
	}
	
	public void printDebugTrace(Exception e) {
		if(PLUGIN.getDebug()) {
			LOG.severe("[BT_DEBUG] " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void log(String message) {
		LOG.info("[BowTag] " + message);
	}

}
