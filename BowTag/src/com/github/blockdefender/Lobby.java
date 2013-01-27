package com.github.blockdefender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Lobby for players while they wait to join a game.
 * 
 * @author thebiologist13
 */
public class Lobby implements Serializable {
	
	private static final long serialVersionUID = -6460820065392217577L;
	
	private List<Player> players = new ArrayList<Player>();
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public void removePlayer(Player p) {
		players.remove(p);
	}
	
	public void removePlayer(String playerName) {
		Player p = Bukkit.getServer().getPlayer(playerName);
		players.remove(p);
	}
	
}
