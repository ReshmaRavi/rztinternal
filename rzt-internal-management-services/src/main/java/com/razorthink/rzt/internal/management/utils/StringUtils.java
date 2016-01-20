package com.razorthink.rzt.internal.management.utils;

public class StringUtils {

	public static boolean isNullOrEmpty( String content )
	{
		if( null == content || content.isEmpty() || content.equalsIgnoreCase("null") )
		{
			return true;
		}
		return false;
	}

	public static boolean isNumberNull( Integer integer )
	{
		if( null == integer || integer.intValue() == 0 )
		{
			return true;
		}
		return false;
	}

	public static String toCapitalizeFirstLetter( String source )
	{
		String res = "";
		String[] strArr = source.split(" ");
		for( String str : strArr )
		{
			if( !((str == null) || (str.isEmpty())) )
			{
				if( !Character.isDigit(str.charAt(0)) )
				{
					if( str.length() > 1 )
					{
						res += Character.toUpperCase(str.charAt(0)) + str.substring(1);
					}
					else
					{
						res += Character.toUpperCase(str.charAt(0));
					}
				}
				else
				{
					res += str;
				}
			}
			res += " ";
		}
		return res;
	}
}
