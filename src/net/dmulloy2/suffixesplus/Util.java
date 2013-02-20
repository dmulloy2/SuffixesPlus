package net.dmulloy2.suffixesplus;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Util 
{
	public static Player matchPlayer(String p) {
		List<Player> players = Bukkit.matchPlayer(p);
		
		if (players.size() >= 1)
			return players.get(0);
		
		return null;
	}
}
