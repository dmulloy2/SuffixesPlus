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

public class CmdPrefix extends SuffixesPlusCommand
{
	public CmdPrefix(SuffixesPlus plugin)
	{
		super(plugin);
		this.name = "prefix";
		this.aliases.add("pre");
		this.requiredArgs.add("prefix");
		this.optionalArgs.add("player");
		this.description = "Change your prefix";
		this.permission = Permission.PREFIX;
	}

	@Override
	public void perform()
	{
		if (args.length == 1)
		{
			if (sender instanceof Player)
			{
				int maxlength = plugin.getConfig().getInt("maxLengths.prefix");
				String argscheck = args[0].replaceAll("(?i)&([a-f0-9])", "").replaceAll("&", "").replaceAll("\\[", "").replaceAll("\\]", "");
				if (argscheck.length() <= maxlength)
				{
					String newPrefix = args[0];

					PluginManager pm = plugin.getServer().getPluginManager();
					if (!pm.isPluginEnabled("PExChat"))
					{
						newPrefix = Util.getRainbowizedString(newPrefix);
					}

					String command = plugin.getConfig().getString("commands.prefix");
					command = command.replaceAll("%p", player.getName());
					command = command.replaceAll("%s", newPrefix);

					ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
					plugin.getServer().dispatchCommand(ccs, command);

					sendpMessage("&bYour prefix is now \"{0}\"", newPrefix);
				}
				else
				{
					err("Error, Your prefix is too long (Max {0} Characters)", maxlength);
				}
			}
			else
			{
				err("Console cannot have a prefix!");
			}
		}
		else if (args.length == 2)
		{
			if (plugin.getPermissionHandler().hasPermission(sender, Permission.PREFIX_OTHERS))
			{
				Player target = Util.matchPlayer(args[0]);
				if (target != null)
				{
					String newPrefix = args[0];

					PluginManager pm = plugin.getServer().getPluginManager();
					if (!pm.isPluginEnabled("PExChat"))
					{
						newPrefix = Util.getRainbowizedString(newPrefix);
					}

					String command = plugin.getConfig().getString("commands.prefix");
					command = command.replaceAll("%p", target.getName());
					command = command.replaceAll("%s", newPrefix);

					ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
					plugin.getServer().dispatchCommand(ccs, command);

					sendpMessage("&b{0}\'s prefix is now \"{1}\"", target.getName(), newPrefix);
					sendMessageTarget("&bYour prefix is now \"{1}\"", target, newPrefix);
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