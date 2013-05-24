/**
 * (c) 2013 dmulloy2
 */
package net.dmulloy2.suffixesplus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import net.dmulloy2.suffixesplus.commands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author dmulloy2
 */

public class SuffixesPlus extends JavaPlugin
{
	public int maxsuffixlength, maxprefixlength;

	public HashMap<Player, List<Player>> listenedToBy = new HashMap<Player, List<Player>>();
	
	public String noperm = ChatColor.RED + "You do not have permission to perform this command";
	
	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();
		
		PluginManager pm = getServer().getPluginManager();
		permsPluginCheck(pm);
		pm.registerEvents(new ChatListener(this), this);
		
		getCommand("prefix").setExecutor(new CmdPrefix (this));
		getCommand("suffix").setExecutor(new CmdSuffix (this));
		getCommand("prefixr").setExecutor(new CmdPrefixR (this));
		getCommand("suffixr").setExecutor(new CmdSuffixR (this));
		getCommand("suffixesplus").setExecutor(new CmdSP (this));
		getCommand("listening").setExecutor(new CmdListening (this));
		getCommand("listen").setExecutor(new CmdListen (this));
		
		getCommand("prefix").setPermissionMessage(noperm);
		getCommand("suffix").setPermissionMessage(noperm);
		getCommand("prefixr").setPermissionMessage(noperm);
		getCommand("suffixr").setPermissionMessage(noperm);
		
		saveDefaultConfig();
		loadConfig();
		
		for (Player player : getServer().getOnlinePlayers())
		{
			createListenedToBy(player);
		}
		
		long finish = System.currentTimeMillis();
		
		outConsole(getDescription().getFullName() + " has been enabled ("+(finish-start)+"ms)");
	}
	
	@Override
	public void onDisable()
	{
		long start = System.currentTimeMillis();
		
		listenedToBy.clear();
		
		long finish = System.currentTimeMillis();
		
		outConsole(getDescription().getFullName() + " has been disabled ("+(finish-start)+"ms)");
	}
	
	public void outConsole(String string)
	{
		getLogger().info(string);
	}
	
	public void outConsole(Level level, String string)
	{
		getLogger().log(level, string);
	}
	
	/**Check for Permissions Plugin**/
	private void permsPluginCheck(PluginManager pm) 
	{
		if (pm.isPluginEnabled("GroupManager")) 
		{
			outConsole("GroupManager found, using it for permissions hooks!");
			return;
		} 
		else if (pm.isPluginEnabled("PermissionsEx"))
		{
			outConsole("PermissionsEx found, using it for permissions hooks!");
			return;
		}
		else 
		{
			outConsole(Level.WARNING, "A permissions plugin is required for SuffixesPlus, please install one");
			outConsole(Level.WARNING, "Disabling " + getDescription().getFullName());
			
			getPluginLoader().disablePlugin(this);
			return;
		}
	}
	
	/**Configuration Load**/
	public void loadConfig()
	{
		maxsuffixlength = getConfig().getInt("maxsuffixlength");
		maxprefixlength = getConfig().getInt("maxprefixlength");
	}

	/**Display Help**/
	public void displayHelp(CommandSender sender) 
	{
		sender.sendMessage(ChatColor.DARK_RED + "====== " + ChatColor.GOLD + getDescription().getFullName() + ChatColor.DARK_RED + " ======");
		sender.sendMessage(ChatColor.RED + "/<command>" + ChatColor.GOLD + " [optional] " + ChatColor.DARK_RED + "<required>");
		if (sender.hasPermission("sp.suffix"))
		{
			sender.sendMessage(ChatColor.RED + "/suffix" + ChatColor.GOLD + " [player]" + ChatColor.DARK_RED + " <suffix> " + ChatColor.YELLOW + "Changes your suffix");
		}
		if (sender.hasPermission("sp.prefix"))
		{
			sender.sendMessage(ChatColor.RED + "/prefix" + ChatColor.GOLD + " [player]" +  ChatColor.DARK_RED + " <suffix> " + ChatColor.YELLOW + "Changes your prefix");
		}
		if (sender.hasPermission("sp.moderator"))
		{
			sender.sendMessage(ChatColor.RED + "/suffixr" + ChatColor.GOLD + " [player] " + ChatColor.YELLOW + "Resets a player's suffix");
			sender.sendMessage(ChatColor.RED + "/prefixr" + ChatColor.GOLD + " [player] " + ChatColor.YELLOW + "Resets a player's prefix");
			sender.sendMessage(ChatColor.RED + "/listen" +  ChatColor.DARK_RED + " <player> " + ChatColor.YELLOW + "Listen to another player");
			sender.sendMessage(ChatColor.RED + "/listening" + ChatColor.YELLOW + " Check who you are listening to");
		}
	}
	
	/**Listening**/
	public boolean isListenedToBy(Player p1, Player p2)
	{
		return listenedToBy.get(p1).contains(p2);
	}

	public void createListenedToBy(Player p1)
	{
		listenedToBy.put(p1, new ArrayList<Player>());
	}

	public void removeListenedToBy(Player p2) 
	{
		for (Player p1 : getServer().getOnlinePlayers())
		{
			if(listenedToBy.get(p1).contains(p2))
				listenedToBy.get(p1).remove(p2);
		}
		listenedToBy.remove(p2);
	}
}