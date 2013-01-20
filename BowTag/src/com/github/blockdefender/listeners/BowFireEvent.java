package com.github.blockdefender.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import com.github.blockdefender.BowTag;

public class BowFireEvent implements Listener {
	
	@SuppressWarnings("unused")
	private BowTag plugin;
	
	public BowFireEvent(BowTag plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onFire(ProjectileLaunchEvent ev) {
		//Do stuff
	}
	
}
