package com.github.blockdefender;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.github.blockdefender.Team.Color;

/**
 * Represents a BowTag game.
 * 
 * @author thebiologist13
 */
public class Arena {
	
	private BowTag plugin;
	private Lobby lobby;
	private boolean inGame;
	private boolean countingDown;
	private List<Team> teams;
	private SLocation[] positions;

	public Arena(BowTag plugin) {
		this.plugin = plugin;
		lobby = new Lobby();
		inGame = false;
		teams = new ArrayList<Team>();
		positions = new SLocation[2];
	}
	
	//TODO Add thing to add a player to lobby/game
	
	public void startGame() {
		
		//Divides players in lobby into teams
		List<Player> players = lobby.getPlayers();
		int playerCount = players.size();
		int teamCount = teams.size();
		int remaining = playerCount % teamCount;
		if(remaining != 0) {
			int divisibleCount = playerCount - remaining;
			dividePlayers(players, divisibleCount, teamCount);
			
			int teamIndex = 0;
			for(int i = 0; i < remaining; i++) {
				teams.get(teamIndex).addMember(players.get(divisibleCount + i));
			}
			
		} else {
			dividePlayers(players, playerCount, teamCount);
		}
		
		//Schedules countdown
		
		
	}
	
	public void addPosition(Location loc, byte id) {
		addPosition(new SLocation(loc), id);
	}

	public void addPosition(SLocation loc, byte id) {
		Validate.notNull(loc, "Cannot have a null location.");
		
		if(id != 1 || id != 2) {
			throw new IllegalArgumentException("Cannot set position " + id);
		}
		
		this.positions[id - 1] = loc; 
	}
	
	/**
	 * Add a team to the game.
	 * 
	 * @param team
	 */
	public void addTeam(Team team) {
		
		Validate.notNull(team, "Cannot add null team.");
		
		for(Team t : this.teams) {
			if(team.getTeamColor().equals(t.getTeamColor())) {
				throw new IllegalArgumentException("Cannot add a team of the same color!");
			}
		}
	}
	
	public void addTeamScore(Color team, int amount) {
		
		Validate.notNull(team, "Cannot add to null team.");
		
		Team t = getTeam(team);
		t.addScore(amount);
	}
	
	public void addTeamScore(Team team, int amount) {
		
		Validate.notNull(team, "Cannot add to null team.");
		
		Team t = getTeam(team.getTeamColor());
		t.addScore(amount);
	}
	
	public Lobby getLobby() {
		return lobby;
	}
	
	public SLocation[] getPositions() {
		return positions;
	}
	
	public Team getTeam(Color color) {
		for(Team t : teams) {
			if(t.getTeamColor().equals(color)) {
				return t;
			}
		}
		return null;
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
	public boolean isInGame() {
		return inGame;
	}
	
	public void removeTeamScore(Color team, int amount) {
		addTeamScore(team, amount * -1);
	}
	
	public void removeTeamScore(Team team, int amount) {
		addTeamScore(team, amount * -1);
	}
	
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}
	
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
	
	public void setPositions(SLocation[] positions) {
		this.positions = positions;
	}
	
	/**
	 * Set the teams in the game. Make sure they are not the same colors!
	 * 
	 * @param teams List of teams for game.
	 */
	public void setTeams(List<Team> teams) {
		
		if(teams.size() > 4) {
			throw new IllegalArgumentException("Cannot use more than 4 teams!");
		}
		
		for(Team t : teams) {
			addTeam(t);
		}
	}
	
	private void dividePlayers(List<Player> players, int playerCount, int teamCount) {
		
		int fraction = playerCount / teamCount;
		
		for(int j = 0; j < teamCount; j++) {
			
			int startIndex = j * fraction;
			
			for(int i = 0; i < fraction; i++) {
				teams.get(j).addMember(players.get(startIndex + i));
			}
			
		}
		
	}
	
	private void countdown(final int length) {
		
		plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			
			int time = length;
			
			@Override
			public void run() {
				time--;
				for(Player p : lobby.getPlayers()) {
					if(time == 0) {
						plugin.getUtil().sendMessage(p, ChatColor.RED + "START!");
					} else if(time > 5) {
						plugin.getUtil().sendMessage(p, ChatColor.AQUA + "Game starts in " + 
								ChatColor.RED + time + ChatColor.AQUA + "...");
					} else {
						plugin.getUtil().sendMessage(p, ChatColor.RED + "" + time + ChatColor.AQUA + "...");
					}
				}
			}
			
		}, 0, 20);
	}
	
}
