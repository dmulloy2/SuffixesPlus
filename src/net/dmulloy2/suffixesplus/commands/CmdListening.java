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
		listening.append("&eYou are listening to: ");

		for (Player p1 : plugin.getServer().getOnlinePlayers())
		{
			if (plugin.isListenedToBy(p1, player))
			{
				listening.append("&b" + p1.getName() + "&e, ");
			}
		}

		if (listening.lastIndexOf("&e, ") >= 0)
		{
			listening.delete(listening.lastIndexOf("&e"), listening.lastIndexOf(" "));
			listening.append("&e.");
		}
		else
		{
			listening.append("&bnobody&e.");
		}

		sendpMessage(listening.toString());
	}
}