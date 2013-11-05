package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.util.Util;

import org.bukkit.entity.Player;

/**
 * @author dmulloy2
 */

public class CmdListen extends SuffixesPlusCommand
{
	public CmdListen(SuffixesPlus plugin)
	{
		super(plugin);
		this.name = "listen";
		this.requiredArgs.add("player");
		this.description = "Listen to another player";

		this.mustBePlayer = true;
	}

	@Override
	public void perform()
	{
		Player p1 = Util.matchPlayer(args[0]);
		if (p1 == null)
		{
			err("&cPlayer not found!");
			return;
		}

		Player p2 = (Player) sender;
		if (p1.getName().equals(p2.getName()))
		{
			err("You cannot listen to yourself!");
			return;
		}

		if (!plugin.isListenedToBy(p1, p2))
		{
			plugin.getListenedToBy().get(p1.getName()).add(p2.getName());
			sendpMessage("&2You are now listening to &b{0}&2.", p1.getName());
		}
		else
		{
			plugin.getListenedToBy().get(p1.getName()).remove(p2.getName());
			sendpMessage("&9You are no longer listening to &b{0}&9.", p1.getName());
		}
	}
}