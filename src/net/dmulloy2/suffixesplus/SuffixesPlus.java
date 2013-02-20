package net.dmulloy2.suffixesplus;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SuffixesPlus extends JavaPlugin
{
	private static Logger log;
	
	public void onEnable()
	{
		log = Logger.getLogger("Minecraft");
		outConsole(getDescription().getFullName() + " has been enabled");
		
		getCommand("prefix").setExecutor(new CmdPrefix (this));
		getCommand("suffix").setExecutor(new CmdSuffix (this));
		getCommand("prefixr").setExecutor(new CmdPrefixR (this));
		getCommand("suffixr").setExecutor(new CmdSuffixR (this));
		getCommand("suffixesplus").setExecutor(new CmdSP (this));
		
		getCommand("prefix").setPermissionMessage(ChatColor.RED + "You do not have permission to preform this command");
		getCommand("suffix").setPermissionMessage(ChatColor.RED + "You do not have permission to preform this command");
		getCommand("prefixr").setPermissionMessage(ChatColor.RED + "You do not have permission to preform this command");
		getCommand("suffixr").setPermissionMessage(ChatColor.RED + "You do not have permission to preform this command");
		
		PluginManager pm = getServer().getPluginManager();
		checkGroupManager(pm);
	}
	
	public void onDisable()
	{
		outConsole(getDescription().getFullName() + " has been disabled");
	}
	
	public static void outConsole(String s)
	{
		log.log(Level.INFO, "[SuffixesPlus] " + s);
	}
	
	private void checkGroupManager(PluginManager pm) 
	{
		Plugin p = pm.getPlugin("GroupManager");
		if (p != null) 
		{
			setEnabled(true);
			outConsole("GroupManager found, enabling " + getDescription().getFullName());
		} 
		else 
		{
			log.info("[SuffixesPlus] GroupManager is required for SuffixesPlus, please install it.");
			log.info("[SuffixesPlus] Disabling " + getDescription().getFullName());
			setEnabled(false);
		}
	}

	public void displayHelp(CommandSender sender) 
	{
		sender.sendMessage(ChatColor.DARK_RED + "====== " + ChatColor.GOLD + getDescription().getFullName() + ChatColor.DARK_RED + " ======");
		if (sender.hasPermission("sp.suffix"))
		{
			sender.sendMessage(ChatColor.RED + "/suffix" + ChatColor.DARK_RED + " <suffix> " + ChatColor.YELLOW + "Changes your suffix");
		}
		if (sender.hasPermission("sp.prefix"))
		{
			sender.sendMessage(ChatColor.RED + "/prefix" + ChatColor.DARK_RED + " <suffix> " + ChatColor.YELLOW + "Changes your prefix");
		}
		if (sender.hasPermission("sp.moderator"))
		{
			sender.sendMessage(ChatColor.RED + "/suffixr" + ChatColor.DARK_RED + " <player> " + ChatColor.YELLOW + "Resets a player's suffix");
			sender.sendMessage(ChatColor.RED + "/prefixr" + ChatColor.DARK_RED + " <player> " + ChatColor.YELLOW + "Resets a player's prefix");
		}
	}
}
