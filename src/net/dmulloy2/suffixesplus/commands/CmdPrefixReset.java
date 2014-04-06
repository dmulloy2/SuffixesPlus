package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.types.Permission;
import net.dmulloy2.suffixesplus.util.Util;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author dmulloy2
 */

public class CmdPrefixReset extends SuffixesPlusCommand
{
	public CmdPrefixReset(SuffixesPlus plugin)
	{
		super(plugin);
		this.name = "prefixreset";
		this.aliases.add("prer");
		this.optionalArgs.add("player");
		this.description = "Reset your prefix";
		this.permission = Permission.PREFIX_RESET;
	}

	@Override
	public void perform()
	{
		if (args.length == 0)
		{
			if (sender instanceof Player)
			{
				String command = plugin.getConfig().getString("commands.prefixReset");
				command = command.replaceAll("%p", player.getName());

				ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
				if (plugin.getServer().dispatchCommand(ccs, command))
				{
					sendpMessage("&eYour prefix has been reset!");
				}
				else
				{
					err("Could not execute command! Consult an Administrator.");
				}
			}
			else
			{
				err("Console cannot have a prefix!");
			}
		}
		else if (args.length == 1)
		{
			// Check for the player first
			Player target = Util.matchPlayer(args[0]);
			if (target == null)
			{
				err("Player \"&c{0}&4\" not found!", args[0]);
				return;
			}

			// Permission check
			if (! sender.getName().equals(target.getName()) && ! hasPermission(Permission.PREFIX_RESET_OTHERS))
			{
				err("You do not have permission to perform this command!");
				return;
			}

			// Attempt to execute the command
			String command = plugin.getConfig().getString("commands.prefixReset");
			command = command.replaceAll("%p", target.getName());

			ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
			if (plugin.getServer().dispatchCommand(ccs, command))
			{
				sendpMessage("&eYou have reset {0}''s prefix!", target.getName());
				sendMessageTarget("&eYour prefix has been reset!", target);
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