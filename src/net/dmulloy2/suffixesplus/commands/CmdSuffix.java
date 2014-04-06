package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.types.Permission;
import net.dmulloy2.suffixesplus.util.Util;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

/**
 * @author dmulloy2
 */

public class CmdSuffix extends SuffixesPlusCommand
{
	public CmdSuffix(SuffixesPlus plugin)
	{
		super(plugin);
		this.name = "suffix";
		this.aliases.add("suf");
		this.requiredArgs.add("suffix");
		this.optionalArgs.add("player");
		this.description = "Set your prefix";
		this.permission = Permission.SUFFIX;
	}

	@Override
	public void perform()
	{
		if (args.length == 1)
		{
			if (sender instanceof Player)
			{
				int maxLength = plugin.getConfig().getInt("maxLengths.suffix");

				String newSuffix = args[0];
				String argscheck = newSuffix.replaceAll("(?i)&([a-f0-9])", "").replaceAll("&", "").replaceAll("\\[", "")
						.replaceAll("\\]", "");

				// Perform and enforce args check
				if (argscheck.length() > maxLength)
				{
					err("Your suffix is too long! (Max &c{0} &4characters)", maxLength);
					return;
				}

				PluginManager pm = plugin.getServer().getPluginManager();
				if (! pm.isPluginEnabled("PExChat"))
				{
					newSuffix = Util.getRainbowizedString(args[0]);
				}

				String command = plugin.getConfig().getString("commands.suffix");
				command = command.replaceAll("%p", player.getName());
				command = command.replaceAll("%s", newSuffix);

				ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
				if (plugin.getServer().dispatchCommand(ccs, command))
				{
					sendpMessage("&eYour suffix is now \"{0}&e\"", newSuffix);
				}
				else
				{
					err("Could not execute command! Consult an Administrator.");
				}
			}
			else
			{
				err("Console cannot have a suffix!");
			}
		}
		else if (args.length == 2)
		{
			Player target = Util.matchPlayer(args[0]);
			if (target == null)
			{
				err("Player \"&c{0}&4\" not found!", args[0]);
				return;
			}

			// Players can set their own suffix if they specify their name,
			// If they specify someone else's name, they must have SUFFIX_OTHERS
			if (! sender.getName().equals(target.getName()) && ! hasPermission(Permission.SUFFIX_OTHERS))
			{
				err("You do not have permission to perform this command!");
				return;
			}

			int maxLength = plugin.getConfig().getInt("maxLengths.suffix");

			String newSuffix = args[1];
			String argsCheck = newSuffix.replaceAll("(?i)&([a-f0-9])", "").replaceAll("&", "").replaceAll("\\[", "").replaceAll("\\]", "");

			// Perform and enforce args check
			if (argsCheck.length() > maxLength)
			{
				err("Your suffix is too long! (Max &c{0} &4characters)", maxLength);
				return;
			}

			PluginManager pm = plugin.getServer().getPluginManager();
			if (! pm.isPluginEnabled("PExChat"))
			{
				newSuffix = Util.getRainbowizedString(newSuffix);
			}

			String command = plugin.getConfig().getString("commands.suffix");
			command = command.replaceAll("%p", target.getName());
			command = command.replaceAll("%s", newSuffix);

			ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
			if (plugin.getServer().dispatchCommand(ccs, command))
			{
				sendpMessage("&e{0}''s suffix is now \"{1}&e\"", target.getName(), newSuffix);
				sendMessageTarget("&eYour suffix is now \"{0}&e\"", target, newSuffix);
			}
			else
			{
				err("Could not execute command! Consult an Administrator.");
			}
		}
		else
		{
			invalidArgs();
		}
	}
}