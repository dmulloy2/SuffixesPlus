package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.types.Permission;
import net.dmulloy2.suffixesplus.types.Reloadable;

/**
 * @author dmulloy2
 */

public class CmdReload extends SuffixesPlusCommand implements Reloadable
{
	public CmdReload(SuffixesPlus plugin)
	{
		super(plugin);
		this.name = "reload";
		this.aliases.add("rl");
		this.description = "Reload SuffixesPlus";
		this.permission = Permission.RELOAD;

		this.usesPrefix = true;
	}

	@Override
	public void perform()
	{
		reload();
	}

	@Override
	public void reload()
	{
		sendpMessage("&aReloading Configuration...");

		plugin.reload();

		sendpMessage("&aReload Complete!");
	}
}