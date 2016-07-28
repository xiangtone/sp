/**
 *Copyright 2003 Xiamen Xiangtone Co. Ltd.
 *All right reserved.
 */


package com.xiangtone.util;
import java.security.MessageDigest;

/**
 *MD5º”√‹
 *
 */

public class MD5
{
	
	public static String getMD5str(String srcStr)
	{
		String rtnStr=new String();
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] dat = srcStr.getBytes();
			int len = dat.length;
			
			md.update(dat, 0, len);
			byte[] ddata = md.digest();
			
			for(int j=0; j<ddata.length; j++) 
			{
		   		int r = (ddata[j] >= 0) ? ddata[j]:(256+ddata[j]);
		    	if(r < 0x10) 
		    	{
						rtnStr+="0";
		   		}
		    	rtnStr+=Integer.toString(r, 16);
	    	}
		}
		catch (Exception e) 
		{
	    	e.printStackTrace();
		}
		return rtnStr;
	}
	
	
	public static void main(String[] args)
	{
		System.out.println(MD5.getMD5str(args[0]));
	}
}

