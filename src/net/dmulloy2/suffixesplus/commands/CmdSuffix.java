package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CmdSuffix implements CommandExecutor
{
	
	public SuffixesPlus plugin;
	  public CmdSuffix(SuffixesPlus plugin)  
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
				  String argscheck = args[0].toString().replaceAll("(?i)&([a-f0-9])", "").replaceAll("&", "");
				  if (argscheck.length() < 11)
				  {
					  ConsoleCommandSender ccs = Bukkit.getServer().getConsoleSender();
					  String newSuffix = args[0].toString();
					  Bukkit.getServer().dispatchCommand(ccs, "manuaddv " + sender.getName() + " suffix " + newSuffix + "&r");
					  sender.sendMessage(ChatColor.AQUA + "Your suffix is now " + newSuffix);
				  }
				  else
				  {
					  sender.sendMessage(ChatColor.RED + "Error, your suffix is too long (Max 10 characters)");
				  }
			  }
			  else
			  {
				  sender.sendMessage(ChatColor.RED + "Invaild arguments count (/suf <suffix>)");
			  }
		  }
		  else
		  {
			  sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
		  }
		return true;
	  }
}
