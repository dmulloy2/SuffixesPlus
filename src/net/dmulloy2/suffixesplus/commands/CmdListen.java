package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.util.FormatUtil;
import net.dmulloy2.suffixesplus.util.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdListen implements CommandExecutor
{
	private final SuffixesPlus plugin;

	public CmdListen(final SuffixesPlus plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		perform(sender, args);
		return true;
	}

	public void perform(CommandSender sender, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage(FormatUtil.format("&cThis command cannot be executed from console."));
			return;
		}

		Player p1 = Util.matchPlayer(args[0]);
		if (p1 == null)
		{
			sender.sendMessage(FormatUtil.format("&cPlayer not found!"));
			return;
		}

		Player p2 = (Player) sender;
		if (p1.getName().equals(p2.getName()))
		{
			sender.sendMessage(FormatUtil.format("&cYou cannot listen to yourself!"));
			return;
		}

		if (!plugin.isListenedToBy(p1, p2))
		{
			plugin.listenedToBy.get(p1).add(p2);
			sender.sendMessage(FormatUtil.format("&2You are now listening to &b{0}&2.", p1.getName()));
		}
		else
		{
			plugin.listenedToBy.get(p1).remove(p2);
			sender.sendMessage(FormatUtil.format("&9You are no longer listening to &b{0}&9.", p1.getName()));
		}
	}
}