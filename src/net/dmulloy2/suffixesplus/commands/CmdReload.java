package net.dmulloy2.suffixesplus.commands;

import net.dmulloy2.suffixesplus.SuffixesPlus;
import net.dmulloy2.suffixesplus.types.Permission;

/**
 * @author dmulloy2
 */

public class CmdReload extends SuffixesPlusCommand
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
		sendpMessage("&aReloading Configuration...");

		plugin.reloadConfig();

		sendpMessage("&aReload Complete!");
	}
}