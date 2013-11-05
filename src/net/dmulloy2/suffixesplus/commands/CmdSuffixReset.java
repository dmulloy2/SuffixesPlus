package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.types.Permission;
import net.dmulloy2.suffixesplus.util.Util;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author dmulloy2
 */

public class CmdSuffixReset extends SuffixesPlusCommand
{
	public CmdSuffixReset(SuffixesPlus plugin)
	{
		super(plugin);
		this.name = "suffixreset";
		this.aliases.add("suffixr");
		this.optionalArgs.add("player");
		this.description = "Reset your suffix";
		this.permission = Permission.SUFFIX_RESET;
	}

	@Override
	public void perform()
	{
		if (args.length == 0)
		{
			if (sender instanceof Player)
			{
				String command = plugin.getConfig().getString("commands.suffixReset");
				command = command.replaceAll("%p", player.getName());

				ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
				if (plugin.getServer().dispatchCommand(ccs, command))
				{
					sendpMessage("&eYour suffix has been reset");
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
		else if (args.length == 1)
		{
			Player target = Util.matchPlayer(args[0]);
			if (target != null)
			{
				String command = plugin.getConfig().getString("commands.suffixReset");
				command = command.replaceAll("%p", target.getName());

				ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
				if (plugin.getServer().dispatchCommand(ccs, command))
				{
					sendpMessage("&eYou have reset {0}''s suffix", target.getName());
					sendMessageTarget("&eYour suffix has been reset", target);
				}
				else
				{
					err("Could not execute command! Consult an Administrator.");
				}
			}
			else
			{
				err("Player \"{0}\" not found!", args[0]);
			}
		}
		else
		{
			invalidArgs();
		}
	}
}