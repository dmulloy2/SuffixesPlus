/**
 * (c) 2013 dmulloy2
 */
package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author dmulloy2
 */

public class CmdPrefixR implements CommandExecutor
{
	
	public SuffixesPlus plugin;
	  public CmdPrefixR(SuffixesPlus plugin)  
	  {
	    this.plugin = plugin;
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	  {
		  if (sender instanceof Player)
		  {
			  sender = (Player) sender;
		  }
		  if (args.length == 1)
		  {
			  ConsoleCommandSender ccs = Bukkit.getServer().getConsoleSender();
			  Player target = Util.matchPlayer(args[0]);
			  if (target != null)
			  {
				  Bukkit.getServer().dispatchCommand(ccs, "manudelv " + target.getName() + " prefix");
				  sender.sendMessage(ChatColor.AQUA + "You have reset " + target.getName() + "'s prefix");
				  target.sendMessage(ChatColor.AQUA + "Your prefix has been reset");
			  }
			  else
			  {
				  sender.sendMessage(ChatColor.RED + "Player not found");
			  }
		  }
		  else if (args.length == 0)
		  {
			  ConsoleCommandSender ccs = Bukkit.getServer().getConsoleSender();
			  Bukkit.getServer().dispatchCommand(ccs, "manudelv " + sender.getName() + " prefix");
			  sender.sendMessage(ChatColor.AQUA + "Your prefix has been reset");
		  }
		  else
		  {
			  sender.sendMessage(ChatColor.RED + "Invalid arguments count (/prer [player])");
		  }

		return true;
	  }
}
