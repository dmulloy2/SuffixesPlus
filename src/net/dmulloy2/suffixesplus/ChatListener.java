package net.dmulloy2.suffixesplus;

import net.dmulloy2.suffixesplus.util.FormatUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener
{
	private final SuffixesPlus plugin;

	public ChatListener(final SuffixesPlus plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		if (!event.getMessage().contains(ChatColor.DARK_GREEN + "[" + ChatColor.AQUA + "LISTENING" + ChatColor.DARK_GREEN + "]"))
		{
			// Only send to players not listening
			for (Player p2 : plugin.listenedToBy.get(event.getPlayer()))
			{
				event.getRecipients().remove(p2);
			}

			if (!plugin.listenedToBy.get(event.getPlayer()).isEmpty())
			{
				event.getPlayer().chat(FormatUtil.format("&6&l[&bLISTENING&6&l] {0}&r", event.getMessage()));
			}
		}
		else
		{
			event.getRecipients().clear();
			for (Player p2 : plugin.listenedToBy.get(event.getPlayer()))
			{
				event.getRecipients().add(p2);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		plugin.createListenedToBy(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		onPlayerDisconnect(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerKick(PlayerKickEvent event)
	{
		if (!event.isCancelled())
		{
			onPlayerDisconnect(event.getPlayer());
		}
	}

	public void onPlayerDisconnect(Player player)
	{
		plugin.removeListenedToBy(player);
	}
}