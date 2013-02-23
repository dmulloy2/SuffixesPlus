package net.dmulloy2.suffixesplus.listeners;

import net.dmulloy2.suffixesplus.SuffixesPlus;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

/**
 * @author dmulloy2
 */

public class TagListener implements Listener {
	
	public SuffixesPlus plugin;
	
	public TagListener(SuffixesPlus plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onNameTag(PlayerReceiveNameTagEvent event) {
		if (event.getNamedPlayer().getName().equals("dmulloy2")) {
			event.setTag(ChatColor.AQUA + "dmulloy2");
		}
		if (event.getNamedPlayer().getName().equals("minermac8521")) {
			event.setTag(ChatColor.GREEN + "minermac8521");
		}
		if (event.getNamedPlayer().getName().equals("irene325")) {
			event.setTag(ChatColor.DARK_RED + "irene325");
		}
		if (event.getNamedPlayer().getName().equals("shortbandit")) {
			event.setTag(ChatColor.AQUA + "short" + ChatColor.DARK_BLUE + "bandit");
		}
		if (event.getNamedPlayer().getName().equals("vinnie109")) {
			event.setTag(ChatColor.DARK_BLUE + "vinn" + ChatColor.YELLOW + "ie109");
		}
	}
}
