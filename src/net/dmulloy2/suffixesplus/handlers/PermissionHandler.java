package net.dmulloy2.suffixesplus.handlers;

import net.dmulloy2.suffixesplus.types.Permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author dmulloy2
 */

public class PermissionHandler
{
	public boolean hasPermission(CommandSender sender, Permission permission)
	{
		return permission == null || hasPermission(sender, getPermissionString(permission));
	}

	public boolean hasPermission(CommandSender sender, String permission)
	{
		if (sender instanceof Player)
		{
			Player p = (Player) sender;
			return p.hasPermission(permission) || p.isOp();
		}

		return true;
	}

	private String getPermissionString(Permission permission)
	{
		return "sp." + permission.getNode().toLowerCase();
	}
}