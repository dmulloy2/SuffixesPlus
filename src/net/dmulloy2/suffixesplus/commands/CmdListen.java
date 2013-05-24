package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdListen implements CommandExecutor
{
	public SuffixesPlus plugin;
	public CmdListen(SuffixesPlus plugin)  
	{
		this.plugin = plugin;
	}
	 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			if (args.length == 1)
			{
				Player p1 = Util.matchPlayer(args[0]);
				if (p1 == null)
				{
					sender.sendMessage(ChatColor.RED + "Player not found!");
				}
				else
				{
					Player p2 = (Player)sender;
					if (p1.getName() == p2.getName())
					{
						sender.sendMessage(ChatColor.RED +  "You cannot listen to yourself!");
					}
					else
					{
						/**Toggle Off**/
						if (!plugin.isListenedToBy(p1, p2))
						{
							plugin.listenedToBy.get(p1).add(p2);
							sender.sendMessage(ChatColor.DARK_GREEN + "You are now listening to " + p1.getDisplayName() + ".");
						}
						/**Toggle On**/
						else
						{
							plugin.listenedToBy.get(p1).remove(p2);
							sender.sendMessage(ChatColor.BLUE + "You are no longer listening to " + p1.getDisplayName() + ".");
						}
					}
				}
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Invalid arguments (/listen <player>)!");
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "This command cannot be executed from the console.");	
		}
		return true;
	}
}