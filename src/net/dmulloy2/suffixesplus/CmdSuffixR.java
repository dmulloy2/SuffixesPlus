package net.dmulloy2.suffixesplus;

import net.dmulloy2.suffixesplus.SuffixesPlus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CmdSuffixR implements CommandExecutor
{
	
	public SuffixesPlus plugin;
	  public CmdSuffixR(SuffixesPlus plugin)  
	  {
	    this.plugin = plugin;
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	  {
//		  Player player = null;
		  if (sender instanceof Player)
		  {
			  sender = (Player) sender;
		  }
		  if (args.length > 0)
		  {
			  ConsoleCommandSender ccs = Bukkit.getServer().getConsoleSender();
			  Player target = Util.matchPlayer(args[0]);
			  Bukkit.getServer().dispatchCommand(ccs, "manudelv " + target.getName() + " suffix");
			  sender.sendMessage(ChatColor.AQUA + "You have reset " + target.getName() + "'s suffix");
			  target.sendMessage(ChatColor.AQUA + "Your suffix has been reset");
		  }
		  else
		  {
			  sender.sendMessage(ChatColor.RED + "Invalid arguments count (/sufr <player>)");
		  }
		  
		  return true;
	  }
}
