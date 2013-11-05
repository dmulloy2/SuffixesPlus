package net.dmulloy2.suffixesplus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.types.Permission;
import net.dmulloy2.suffixesplus.util.FormatUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author dmulloy2
 */

public abstract class SuffixesPlusCommand implements CommandExecutor
{
	protected final SuffixesPlus plugin;

	protected CommandSender sender;
	protected Player player;
	protected String args[];

	protected String name;
	protected String description;

	protected Permission permission;

	protected boolean mustBePlayer;
	protected List<String> requiredArgs;
	protected List<String> optionalArgs;
	protected List<String> aliases;

	protected boolean usesPrefix;

	public SuffixesPlusCommand(SuffixesPlus plugin)
	{
		this.plugin = plugin;
		requiredArgs = new ArrayList<String>(2);
		optionalArgs = new ArrayList<String>(2);
		aliases = new ArrayList<String>(2);
	}

	public abstract void perform();

	@Override
	public final boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		execute(sender, args);
		return true;
	}

	public final void execute(CommandSender sender, String[] args)
	{
		this.sender = sender;
		this.args = args;

		if (sender instanceof Player)
			player = (Player) sender;

		if (mustBePlayer && !isPlayer())
		{
			err("You must be a player to execute this command!");
			return;
		}

		if (requiredArgs.size() > args.length)
		{
			invalidArgs();
			return;
		}

		if (!hasPermission())
		{
			err("You do not have permission to perform this command!");
			plugin.outConsole(Level.WARNING, sender.getName() + " was denied access to a command!");
			return;
		}

		perform();
	}

	protected final boolean isPlayer()
	{
		return (player != null);
	}

	private final boolean hasPermission()
	{
		return (plugin.getPermissionHandler().hasPermission(sender, permission));
	}

	protected final boolean argMatchesAlias(String arg, String... aliases)
	{
		for (String s : aliases)
			if (arg.equalsIgnoreCase(s))
				return true;
		return false;
	}

	protected final void err(String string, Object... objects)
	{
		sendpMessage("&c" + string, objects);
	}

	// Send non prefixed message
	protected final void sendMessage(String msg, Object... args)
	{
		sender.sendMessage(FormatUtil.format(msg, args));
	}

	// Send prefixed message
	protected final void sendpMessage(String msg, Object... args)
	{
		sender.sendMessage(plugin.getPrefix() + FormatUtil.format(msg, args));
	}

	// Send message to the whole server
	protected final void sendMessageAll(String msg, Object... args)
	{
		plugin.getServer().broadcastMessage(plugin.getPrefix() + FormatUtil.format(msg, args));
	}

	// Send message to another player
	protected final void sendMessageTarget(String msg, Player target, Object... args)
	{
		sendpMessage(plugin.getPrefix() + FormatUtil.format(msg, args));
	}

	public final String getName()
	{
		return name;
	}

	public final List<String> getAliases()
	{
		return aliases;
	}

	public final String getUsageTemplate(final boolean displayHelp)
	{
		StringBuilder ret = new StringBuilder();
		ret.append("&b/");

		if (plugin.getCommandHandler().usesCommandPrefix() && usesPrefix)
			ret.append(plugin.getCommandHandler().getCommandPrefix() + " ");

		ret.append(name);

		for (String s : optionalArgs)
			ret.append(String.format(" &3[" + s + "]"));

		for (String s : requiredArgs)
			ret.append(String.format(" &3<" + s + ">"));

		if (displayHelp)
			ret.append(" &e" + description);

		return FormatUtil.format(ret.toString());
	}

	protected int argAsInt(int arg, boolean msg)
	{
		try
		{
			return Integer.valueOf(args[arg]);
		}
		catch (NumberFormatException ex)
		{
			if (msg)
				invalidArgs();
			return -1;
		}
	}

	protected double argAsDouble(int arg, boolean msg)
	{
		try
		{
			return Double.valueOf(args[arg]);
		}
		catch (NumberFormatException ex)
		{
			if (msg)
				invalidArgs();
			return -1;
		}
	}

	protected final void invalidArgs()
	{
		err("Invalid arguments! Try: " + getUsageTemplate(false));
	}

	protected final boolean hasPermission(Permission permission)
	{
		return plugin.getPermissionHandler().hasPermission(sender, permission);
	}
}