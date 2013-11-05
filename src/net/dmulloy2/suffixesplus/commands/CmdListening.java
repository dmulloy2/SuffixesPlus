package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;

import org.bukkit.entity.Player;

/**
 * @author dmulloy2
 */

public class CmdListening extends SuffixesPlusCommand
{
	public CmdListening(SuffixesPlus plugin)
	{
		super(plugin);
		this.name = "listening";
		this.description = "List players you are listening to";

		this.mustBePlayer = true;
	}

	@Override
	public void perform()
	{
		StringBuilder listening = new StringBuilder();
		listening.append("&6You are listening to:&b ");

		boolean nobody = true;
		for (Player p1 : plugin.getServer().getOnlinePlayers())
		{
			if (plugin.isListenedToBy(p1, player))
			{
				listening.append(p1.getDisplayName() + ", ");
				nobody = false;
			}
		}

		if (!nobody)
		{
			listening.replace(listening.length() - 2, listening.length(), ".");
		}
		else
		{
			listening.append(" nobody.");
		}

		sendMessage(listening.toString());
	}
}