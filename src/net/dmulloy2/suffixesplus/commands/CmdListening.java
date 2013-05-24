package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdListening implements CommandExecutor
{
	public SuffixesPlus plugin;
	public CmdListening(SuffixesPlus plugin)  
	{
		this.plugin = plugin;
	}
	 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			Player p2 = (Player) sender;
			StringBuilder listening = new StringBuilder();
			listening.append(ChatColor.GOLD + "You are listening to: " + ChatColor.AQUA);
			int listenamt = 0;
			for (Player p1 : plugin.getServer().getOnlinePlayers()) 
			{
				if (plugin.isListenedToBy(p1, p2))
				{
					listening.append(" " + p1.getDisplayName() + ",");
					listenamt++;
				}
			}
			if (listenamt == 0)
			{
				listening.append("nobody.");
			}
			else
			{
				listening.replace(listening.length()-1, listening.length(), ".");
			}
			sender.sendMessage(listening.toString());
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "This command cannot be executed from the console.");	
		}
		return true;
	}
}