package com.razorthink.rzt.internal.management.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.razorthink.rzt.internal.management.exception.DataException;

public class MD5Utils {

	public static String md5String( String content )
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(content.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuilder sb = new StringBuilder();
			for( int i = 0; i < byteData.length; i++ )
			{
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();
		}
		catch( NoSuchAlgorithmException e )
		{
			e.printStackTrace();
			throw new DataException("im.error", e.getLocalizedMessage());
		}

	}
}
