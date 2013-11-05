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
		this.aliases.add("prefixr");
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
				plugin.getServer().dispatchCommand(ccs, command);

				sendpMessage("&bYour prefix has been reset");
			}
			else
			{
				err("Console cannot have a prefix!");
			}
		}
		else if (args.length == 1)
		{
			Player target = Util.matchPlayer(args[0]);
			if (target != null)
			{
				String command = plugin.getConfig().getString("commands.prefixReset");
				command = command.replaceAll("%p", target.getName());

				ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
				plugin.getServer().dispatchCommand(ccs, command);

				sendpMessage("&bYou have reset {0}\'s prefix", target.getName());
				sendMessageTarget("&bYour prefix has been reset", target);
			}
			else
			{
				err("Player not found");
			}
		}
		else
		{
			invalidArgs();
		}
	}
}