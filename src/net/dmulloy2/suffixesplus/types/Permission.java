package net.dmulloy2.suffixesplus.types;

/**
 * @author dmulloy2
 */

public enum Permission
{
	// Prefixes
	PREFIX,
	PREFIX_OTHERS,
	PREFIX_RESET,
	PREFIX_RESET_OTHERS,

	// Suffixes
	SUFFIX,
	SUFFIX_OTHERS,
	SUFFIX_RESET,
	SUFFIX_RESET_OTHERS,

	// Other
	RELOAD,
	;

	public String getNode()
	{
		return toString().toLowerCase().replaceAll("_", ".");
	}
}