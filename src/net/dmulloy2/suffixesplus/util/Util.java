package net.dmulloy2.suffixesplus.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author dmulloy2
 */

public class Util
{
	public static Player matchPlayer(String p)
	{
		List<Player> players = Bukkit.matchPlayer(p);

		if (players.size() >= 1)
			return players.get(0);

		return null;
	}

	private static String[] rainbowCodes = { "&4", "&c", "&6", "&e", "&a", "&2", "&b", "&3", "&9", "&5", "&d" };

	public static String getRainbowizedString(String format, boolean colorize)
	{
		format = format.replaceAll("(&([zZ]))", "&z");
		if (format.contains("&z"))
		{
			String[] ss = format.split("&z");
			if (ss.length == 0)
				return format;

			StringBuilder ret = new StringBuilder();
			ret.append(ss[0]);

			ss[0] = null;

			for (String s : ss)
			{
				if (s != null)
				{
					int index = 0;
					while ((index < s.length()) && (s.charAt(index) != '&'))
					{
						ret.append(rainbowCodes[index % rainbowCodes.length]);
						ret.append(s.charAt(index));
						index++;
					}

					if (index < s.length())
					{
						ret.append(s.substring(index));
					}
				}
			}

			format = ret.toString();

			if (colorize)
			{
				format = format.replaceAll("\'", "\''");

				format = ChatColor.translateAlternateColorCodes('&', format);
			}
		}

		return format;
	}

	public static String getRainbowizedString(String format)
	{
		return getRainbowizedString(format, false);
	}
}