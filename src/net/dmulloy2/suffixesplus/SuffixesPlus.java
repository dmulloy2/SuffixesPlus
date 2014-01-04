/**
 * SuffixesPlus - a bukkit plugin 
 * Copyright (C) 2013 - 2014 dmulloy2
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.dmulloy2.suffixesplus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.dmulloy2.suffixesplus.commands.CmdHelp;
import net.dmulloy2.suffixesplus.commands.CmdListen;
import net.dmulloy2.suffixesplus.commands.CmdListening;
import net.dmulloy2.suffixesplus.commands.CmdPrefix;
import net.dmulloy2.suffixesplus.commands.CmdPrefixReset;
import net.dmulloy2.suffixesplus.commands.CmdReload;
import net.dmulloy2.suffixesplus.commands.CmdSuffix;
import net.dmulloy2.suffixesplus.commands.CmdSuffixReset;
import net.dmulloy2.suffixesplus.handlers.CommandHandler;
import net.dmulloy2.suffixesplus.handlers.LogHandler;
import net.dmulloy2.suffixesplus.handlers.PermissionHandler;
import net.dmulloy2.suffixesplus.listeners.ChatListener;
import net.dmulloy2.suffixesplus.types.Reloadable;
import net.dmulloy2.suffixesplus.util.FormatUtil;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author dmulloy2
 */

public class SuffixesPlus extends JavaPlugin implements Reloadable
{
	private @Getter PermissionHandler permissionHandler;
	private @Getter CommandHandler commandHandler;
	private @Getter LogHandler logHandler;

	private @Getter HashMap<String, List<String>> listenedToBy;

	private @Getter String prefix = FormatUtil.format("&6[&4&lSP&6] ");

	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();

		// Register handlers
		permissionHandler = new PermissionHandler();
		commandHandler = new CommandHandler(this);
		logHandler = new LogHandler(this);

		// Configuration stuff
        File conf = new File(getDataFolder(), "config.yml");
        if (! conf.exists())
        {
        	outConsole("Config not found. Generating a new one.");
        	saveDefaultConfig();
        }
        else
        {
        	if (! getConfig().isSet("maxLengths.prefix"))
        	{
        		conf.renameTo(new File(getDataFolder(), "oldConfig.yml"));
        		
        		outConsole("Your config is out of date. Generating a new one.");
        		
        		saveDefaultConfig();
        	}
        }

        reloadConfig();

		// Prefixed Commands
		commandHandler.setCommandPrefix("suffixesplus");
		commandHandler.registerPrefixedCommand(new CmdHelp(this));
		commandHandler.registerPrefixedCommand(new CmdReload(this));

		// Non Prefixed Commands
		commandHandler.registerCommand(new CmdListen(this));
		commandHandler.registerCommand(new CmdListening(this));
		commandHandler.registerCommand(new CmdPrefix(this));
		commandHandler.registerCommand(new CmdPrefixReset(this));
		commandHandler.registerCommand(new CmdSuffix(this));
		commandHandler.registerCommand(new CmdSuffixReset(this));

		// Register Events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ChatListener(this), this);

		// Listening stuff
		listenedToBy = new HashMap<String, List<String>>();

		for (Player player : getServer().getOnlinePlayers())
		{
			createListenedToBy(player);
		}

		outConsole("{0} has been enabled ({1} ms)", getDescription().getFullName(), System.currentTimeMillis() - start);
	}

	@Override
	public void onDisable()
	{
		long start = System.currentTimeMillis();

		listenedToBy.clear();

		outConsole("{0} has been disabled ({1} ms)", getDescription().getFullName(), System.currentTimeMillis() - start);
	}

	// Console logging
	public void outConsole(String string, Object... objects)
	{
		logHandler.log(string, objects);
	}

	public void outConsole(Level level, String string, Object... objects)
	{
		logHandler.log(level, string, objects);
	}

	// Listening
	public boolean isListenedToBy(Player p1, Player p2)
	{
		return listenedToBy.get(p1.getName()).contains(p2.getName());
	}

	public void createListenedToBy(Player p1)
	{
		listenedToBy.put(p1.getName(), new ArrayList<String>());
	}

	public void removeListenedToBy(Player p2)
	{
		for (Player p1 : getServer().getOnlinePlayers())
		{
			if (listenedToBy.containsKey(p1.getName()))
				listenedToBy.get(p1.getName()).remove(p2.getName());
		}

		listenedToBy.remove(p2.getName());
	}

	@Override
	public void reload()
	{
		reloadConfig();
	}
}