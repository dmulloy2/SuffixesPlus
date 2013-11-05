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
				String argscheck = args[0].replaceAll("(?i)&([a-f0-9])", "").replaceAll("&", "").replaceAll("\\[", "").replaceAll("\\]", "");
				if (argscheck.length() <= maxLength)
				{
					String newSuffix = args[0];

					PluginManager pm = plugin.getServer().getPluginManager();
					if (!pm.isPluginEnabled("PExChat"))
					{
						newSuffix = Util.getRainbowizedString(args[0]);
					}

					String command = plugin.getConfig().getString("commands.suffix");
					command = command.replaceAll("%p", player.getName());
					command = command.replaceAll("%s", newSuffix);

					ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
					plugin.getServer().dispatchCommand(ccs, command);

					sendpMessage("&bYour suffix is now \"{0}\"", newSuffix);
				}
				else
				{
					err("Your suffix is too long (Max {0} characters)", maxLength);
				}
			}
			else
			{
				err("Console cannot have a suffix!");
			}
		}
		else if (args.length == 2)
		{
			if (plugin.getPermissionHandler().hasPermission(sender, Permission.SUFFIX_OTHERS))
			{
				Player target = Util.matchPlayer(args[0]);
				if (target != null)
				{
					String newSuffix = args[0];

					PluginManager pm = plugin.getServer().getPluginManager();
					if (!pm.isPluginEnabled("PExChat"))
					{
						newSuffix = Util.getRainbowizedString(newSuffix);
					}

					String command = plugin.getConfig().getString("commands.suffix");
					command = command.replaceAll("%p", target.getName());
					command = command.replaceAll("%s", newSuffix);

					ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
					plugin.getServer().dispatchCommand(ccs, command);

					sendpMessage("&b{0}\'s suffix is now \"{1}\"", target.getName(), newSuffix);
					sendMessageTarget("&bYour suffix is now \"{1}\"", target, newSuffix);
				}
				else
				{
					err("Player not found");
				}
			}
			else
			{
				err("You do not have permission to preform this command");
			}
		}
		else
		{
			invalidArgs();
		}
	}
}