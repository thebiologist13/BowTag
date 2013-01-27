package com.github.blockdefender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Represents a team on BowTag.
 * 
 * @author thebiologist13
 */
public class Team {

	public enum Color {
		RED("Red", 0),
		BLUE("Blue", 1),
		GREEN("Green", 2),
		YELLOW("Yellow", 3);
		
		private short id;
		private String name; 
		
		private static final Map<String, Color> NAME_MAP = new HashMap<String, Color>();
		private static final Map<Short, Color> ID_MAP = new HashMap<Short, Color>();
		
		static {
			for(Color c : values()) {
				NAME_MAP.put(c.name.toLowerCase(), c);
				ID_MAP.put(c.id, c);
			}
		}
		
		private Color(String name, int id) {
			this.id = (short) id;
			this.name = name;
		}
		
		public Color fromId(short id) {
			
			if(id > Short.MAX_VALUE)
				return null;
			
			return ID_MAP.get(id);
		}
		
		public Color fromName(String name) {
			
			if(name == null)
				return null;
			
			return NAME_MAP.get(name);
		}
		
		public short getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
	}

	private final Arena game;
	private Color teamColor;
	private List<Player> members = new ArrayList<Player>();
	private int totalScore;
	private short maxPlayers;
	
	public Team(Arena game, Color color) {
		this(game, color, (short) 4, new ArrayList<Player>());
	}
	
	public Team(Arena game, Color color, short maxPlayers) {
		this(game, color, maxPlayers, new ArrayList<Player>());
	}
	
	public Team(Arena game, Color color, short maxPlayers, List<Player> members) {
		this.game = game;
		this.teamColor = color;
		this.members = members;
		this.totalScore = 0;
		this.setMaxPlayers(maxPlayers);
	}

	public void addMember(Player p) {
		members.add(p);
	}
	
	public void addScore(int amount) {
		
		if(amount < 0) {
			removeScore(amount * -1);
		} else {
			totalScore += amount;
		}
		
	}
	
	public Arena getGame() {
		return game;
	}

	public short getMaxPlayers() {
		return maxPlayers;
	}

	public List<Player> getMembers() {
		return members;
	}

	public Color getTeamColor() {
		return teamColor;
	}
	
	public int getTotalScore() {
		return totalScore;
	}
	
	public boolean removeMember(Player p) {
		return members.remove(p);
	}
	
	public boolean removeMember(String name) {
		Player p = Bukkit.getPlayer(name);
		return members.remove(p);
	}

	public void removeScore(int amount) {
		int diff = totalScore - amount;
		if(diff < 0)
			totalScore = 0;
		else
			totalScore = diff;
	}

	public void setMaxPlayers(short maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public void setMembers(List<Player> members) {
		this.members = members;
	}

	public void setTeamColor(Color teamColor) {
		this.teamColor = teamColor;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	
}
