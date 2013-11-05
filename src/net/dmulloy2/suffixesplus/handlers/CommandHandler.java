package net.dmulloy2.suffixesplus.handlers;

import java.util.ArrayList;
import java.util.List;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.commands.CmdHelp;
import net.dmulloy2.suffixesplus.commands.SuffixesPlusCommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

/**
 * @author dmulloy2
 */

public class CommandHandler implements CommandExecutor
{
	private final SuffixesPlus plugin;
	// Only need the name of command prefix - all other aliases listed in
	// plugin.yml will be usable
	private String commandPrefix;
	private List<SuffixesPlusCommand> registeredPrefixedCommands;
	private List<SuffixesPlusCommand> registeredCommands;

	public CommandHandler(SuffixesPlus plugin)
	{
		this.plugin = plugin;
		registeredCommands = new ArrayList<SuffixesPlusCommand>();
	}

	public void registerCommand(SuffixesPlusCommand command)
	{
		PluginCommand pluginCommand = plugin.getCommand(command.getName());
		if (pluginCommand != null)
		{
			pluginCommand.setExecutor(command);
			registeredCommands.add(command);
		}
		else
		{
			plugin.outConsole("Entry for command {0} is missing in plugin.yml", command.getName());
		}
	}

	public void registerPrefixedCommand(SuffixesPlusCommand command)
	{
		if (commandPrefix != null)
			registeredPrefixedCommands.add(command);
	}

	public List<SuffixesPlusCommand> getRegisteredCommands()
	{
		return registeredCommands;
	}

	public List<SuffixesPlusCommand> getRegisteredPrefixedCommands()
	{
		return registeredPrefixedCommands;
	}

	public String getCommandPrefix()
	{
		return commandPrefix;
	}

	public void setCommandPrefix(String commandPrefix)
	{
		this.commandPrefix = commandPrefix;
		registeredPrefixedCommands = new ArrayList<SuffixesPlusCommand>();
		plugin.getCommand(commandPrefix).setExecutor(this);
	}

	public boolean usesCommandPrefix()
	{
		return commandPrefix != null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		List<String> argsList = new ArrayList<String>();

		if (args.length > 0)
		{
			String commandName = args[0];
			for (int i = 1; i < args.length; i++)
				argsList.add(args[i]);

			for (SuffixesPlusCommand command : registeredPrefixedCommands)
			{
				if (commandName.equalsIgnoreCase(command.getName()) || command.getAliases().contains(commandName.toLowerCase()))
				{
					command.execute(sender, argsList.toArray(new String[0]));
					return true;
				}
			}

			sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "Unknown SuffixesPlus command \"" + args[0]
					+ "\". Try /suffixesplus help!");
		}
		else
		{
			new CmdHelp(plugin).execute(sender, args);
		}

		return true;
	}
}