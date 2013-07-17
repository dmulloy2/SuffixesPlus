/**
 * (c) 2013 dmulloy2
 */
package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.util.FormatUtil;
import net.dmulloy2.suffixesplus.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.shadowvolt.shadowvolt.ColorManager;

/**
 * @author dmulloy2
 */

public class CmdPrefix implements CommandExecutor
{
	private final SuffixesPlus plugin;
	public CmdPrefix(final SuffixesPlus plugin)  
	{
		this.plugin = plugin;
	}
	 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		PluginManager pm = plugin.getServer().getPluginManager();
		int maxlength = plugin.maxprefixlength;
		if (args.length == 1)
		{
			if (sender instanceof Player)
			{
				String argscheck = args[0].replaceAll("(?i)&([a-f0-9])", "").replaceAll("&", "").replaceAll("\\[", "").replaceAll("\\]", "");
				if (argscheck.length() <= maxlength)
				{
					String newPrefix = args[0];
					if (pm.isPluginEnabled("Shadowvolt"))
					{
						newPrefix = ColorManager.getRainbowizedString(newPrefix);
					}
					
					ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
					if (pm.isPluginEnabled("GroupManager"))
					{
						plugin.getServer().dispatchCommand(ccs, "manuaddv " + sender.getName() + " prefix " + newPrefix + "&r");
						sender.sendMessage(FormatUtil.format("&bYour prefix is now ''{0}&b''", newPrefix));
					}
					else if (pm.isPluginEnabled("PermissionsEx"))
					{
						plugin.getServer().dispatchCommand(ccs, "pex user " + sender.getName() + " prefix \"" + newPrefix + "\"&r");
						sender.sendMessage(FormatUtil.format("&bYour prefix is now ''{0}&b''", newPrefix));
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Neither GroupManager nor PEX was found.");
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "Error, Your prefix is too long (Max 10 Characters)");
				}
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Console cannot have a prefix!");
			}
		}
		else if (args.length == 2)
		{
			if (sender.hasPermission("sp.others"))
			{
				Player target = Util.matchPlayer(args[0]);
				if (target != null)
				{
					String newPrefix = args[1];
					if (pm.isPluginEnabled("Shadowvolt"))
					{
						newPrefix = ColorManager.getRainbowizedString(newPrefix);
					}
					
					ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
					if (pm.isPluginEnabled("GroupManager"))
					{
						plugin.getServer().dispatchCommand(ccs, "manuaddv " + target.getName() + " prefix " + newPrefix + "&r");
						sender.sendMessage(FormatUtil.format("&b{0}''s prefix is now ''{1}&b''", target.getName(), newPrefix));
						target.sendMessage(FormatUtil.format("&bYour prefix is now ''{0}&b''", newPrefix));
					}
					else if (pm.isPluginEnabled("PermissionsEx"))
					{
						plugin.getServer().dispatchCommand(ccs, "pex user " + target.getName() + " prefix \"" + newPrefix + "\"&r");
						sender.sendMessage(FormatUtil.format("&b{0}''s prefix is now ''{1}&b''", target.getName(), newPrefix));
						target.sendMessage(FormatUtil.format("&bYour prefix is now ''{0}&b''", newPrefix));
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Neither PEX nor GroupManager was found");
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "Player not found");
				}
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "You do not have permission to preform this command");
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "Invalid arguments count (/pre [player] <prefix>)");
		}
		
		return true;
	}
}