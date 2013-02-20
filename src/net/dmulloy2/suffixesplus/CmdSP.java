package net.dmulloy2.suffixesplus;

import net.dmulloy2.suffixesplus.SuffixesPlus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSP implements CommandExecutor
{
	
	public SuffixesPlus plugin;
	  public CmdSP(SuffixesPlus plugin)  
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
		  plugin.displayHelp(sender);
		  
		return true;
	  }
}
