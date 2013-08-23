/**
 * (c) 2013 dmulloy2
 */
package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author dmulloy2
 */

public class CmdSP implements CommandExecutor
{
	private final SuffixesPlus plugin;

	public CmdSP(final SuffixesPlus plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		plugin.displayHelp(sender);
		return true;
	}
}
