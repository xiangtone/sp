/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package com.xiangtone.util;

import java.util.*;
import java.text.SimpleDateFormat;

/**
* A System Time  format
*
*/
public class FormatSysTime
{
	/**
	*method one
	*
	*/
	
	public static String getCurrentTime(String formatStr)
	{
		long ll= System.currentTimeMillis();
	    String sbf = new String("");
	    String sss = new String(formatStr);    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);  
	    sbf = sdf.format(new java.util.Date(ll));  
	    String curTime=sbf.toString();
	    return curTime;
    }
    
	public static String getCurrentTimeA()
	{
		long ll= System.currentTimeMillis();
	    String sbf = new String("");
	    String sss = new String("yyyy-MM-dd HH:mm:ss");    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);  
	    sbf = sdf.format(new java.util.Date(ll));  
	    String curTime=sbf.toString();
	    return curTime;
    }
}