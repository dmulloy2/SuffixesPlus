package net.dmulloy2.suffixesplus.types;

public enum Permission
{
	PREFIX, 
	PREFIX_OTHERS, 
	PREFIX_RESET,

	SUFFIX, 
	SUFFIX_OTHERS,
	SUFFIX_RESET,

	RELOAD,
	;

	public String getNode()
	{
		return toString().toLowerCase().replaceAll("_", ".");
	}
}