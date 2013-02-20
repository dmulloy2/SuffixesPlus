package net.dmulloy2.suffixesplus;

import net.dmulloy2.suffixesplus.SuffixesPlus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CmdPrefix implements CommandExecutor
{
	
	public SuffixesPlus plugin;
	  public CmdPrefix(SuffixesPlus plugin)  
	  {
	    this.plugin = plugin;
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	  {
//		  Player player = null;
		  if (sender instanceof Player)
		  {
			  sender = (Player) sender;
			  if (args.length > 0)
			  {
				  ConsoleCommandSender ccs = Bukkit.getServer().getConsoleSender();
				  String newPrefix = args[0].toString();
				  Bukkit.getServer().dispatchCommand(ccs, "manuaddv " + sender.getName() + " prefix " + newPrefix + "&r");
				  sender.sendMessage(ChatColor.AQUA + "Your prefix is now " + newPrefix);
			  }
			  else
			  {
				  sender.sendMessage(ChatColor.RED + "Invalid arguments count (/pre <prefix>)");
			  }
		  }
		  else
		  {
			  sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
		  }
		return true;
	  }
}
