package net.dmulloy2.suffixesplus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import net.dmulloy2.suffixesplus.commands.CmdListen;
import net.dmulloy2.suffixesplus.commands.CmdListening;
import net.dmulloy2.suffixesplus.commands.CmdPrefix;
import net.dmulloy2.suffixesplus.commands.CmdPrefixR;
import net.dmulloy2.suffixesplus.commands.CmdSP;
import net.dmulloy2.suffixesplus.commands.CmdSuffix;
import net.dmulloy2.suffixesplus.commands.CmdSuffixR;
import net.dmulloy2.suffixesplus.util.FormatUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author dmulloy2
 */

public class SuffixesPlus extends JavaPlugin
{
	public int maxsuffixlength, maxprefixlength;

	public HashMap<Player, List<Player>> listenedToBy = new HashMap<Player, List<Player>>();

	public String noperm = ChatColor.RED + "You do not have permission to perform this command";

	@Override
	public void onEnable()
	{
		long start = System.currentTimeMillis();

		PluginManager pm = getServer().getPluginManager();
		if (!permsPluginCheck(pm))
			return;

		pm.registerEvents(new ChatListener(this), this);

		getCommand("prefix").setExecutor(new CmdPrefix(this));
		getCommand("suffix").setExecutor(new CmdSuffix(this));

		getCommand("prefixr").setExecutor(new CmdPrefixR(this));
		getCommand("suffixr").setExecutor(new CmdSuffixR(this));

		getCommand("suffixesplus").setExecutor(new CmdSP(this));

		getCommand("listening").setExecutor(new CmdListening(this));
		getCommand("listen").setExecutor(new CmdListen(this));

		getCommand("prefix").setPermissionMessage(noperm);
		getCommand("suffix").setPermissionMessage(noperm);
		getCommand("prefixr").setPermissionMessage(noperm);
		getCommand("suffixr").setPermissionMessage(noperm);

		saveDefaultConfig();
		loadConfig();

		for (Player player : getServer().getOnlinePlayers())
		{
			createListenedToBy(player);
		}

		long finish = System.currentTimeMillis();

		outConsole(getDescription().getFullName() + " has been enabled (" + (finish - start) + "ms)");
	}

	@Override
	public void onDisable()
	{
		long start = System.currentTimeMillis();

		listenedToBy.clear();

		long finish = System.currentTimeMillis();

		outConsole(getDescription().getFullName() + " has been disabled (" + (finish - start) + "ms)");
	}

	public void outConsole(String string, Object... objects)
	{
		getLogger().info(FormatUtil.format(string, objects));
	}

	public void outConsole(Level level, String string, Object... objects)
	{
		getLogger().log(level, FormatUtil.format(string, objects));
	}

	/** Check for Permissions Plugin **/
	private boolean permsPluginCheck(PluginManager pm)
	{
		if (pm.isPluginEnabled("GroupManager"))
		{
			outConsole("GroupManager found, using it for permissions hooks!");
			return true;
		}
		else if (pm.isPluginEnabled("PermissionsEx"))
		{
			outConsole("PermissionsEx found, using it for permissions hooks!");
			return true;
		}
		else
		{
			outConsole(Level.WARNING, "A permissions plugin is required for SuffixesPlus, please install one");
			outConsole(Level.WARNING, "Disabling " + getDescription().getFullName());

			getPluginLoader().disablePlugin(this);
			return false;
		}
	}

	/** Configuration Load **/
	public void loadConfig()
	{
		maxsuffixlength = getConfig().getInt("maxsuffixlength");
		maxprefixlength = getConfig().getInt("maxprefixlength");
	}

	/** Display Help **/
	public void displayHelp(CommandSender sender)
	{
		List<String> lines = new ArrayList<String>();
		lines.add(FormatUtil.format("&4=====[ &6{0} &4]=====", getDescription().getFullName()));
		lines.add(FormatUtil.format("&c/<command> &6[optional] &4<required>"));
		if (sender.hasPermission("sp.suffix"))
		{
			lines.add(FormatUtil.format("&c/suffix &6[player] &4<suffix> &eChanges your suffix"));
		}

		if (sender.hasPermission("sp.prefix"))
		{
			lines.add(FormatUtil.format("&c/prefix &6[player] &4<suffix> &eChanges your prefix"));
		}

		if (sender.hasPermission("sp.moderator"))
		{
			lines.add(FormatUtil.format("&c/suffixr &6[player] &eResets a player's suffix"));
			lines.add(FormatUtil.format("&c/prefixr &6[player] &eResets a player's prefix"));
		}

		lines.add(FormatUtil.format("&c/listen &4<player> &eListen to another player"));
		lines.add(FormatUtil.format("&c/listening &eCheck who you are listening to"));
	}

	/** Listening **/
	public boolean isListenedToBy(Player p1, Player p2)
	{
		return listenedToBy.get(p1).contains(p2);
	}

	public void createListenedToBy(Player p1)
	{
		listenedToBy.put(p1, new ArrayList<Player>());
	}

	public void removeListenedToBy(Player p2)
	{
		for (Player p1 : getServer().getOnlinePlayers())
		{
			if (listenedToBy.get(p1).contains(p2))
				listenedToBy.get(p1).remove(p2);
		}

		listenedToBy.remove(p2);
	}
}