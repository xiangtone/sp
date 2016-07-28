/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package mtlogbackup;

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
	    String cur_time=sbf.toString();
	    return cur_time;
    }
    
	public static String getCurrentTimeA()
	{
		long ll= System.currentTimeMillis();
	    String sbf = new String("");
	    String sss = new String("yyyy-MM-dd HH:mm:ss");    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);  
	    sbf = sdf.format(new java.util.Date(ll));  
	    String cur_time=sbf.toString();
	    return cur_time;
    }
	public static String getBeforeDay(int beforeDay){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month  = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		int befDay = day - beforeDay;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		c.set(year, month, befDay);
		String strBeforeDay = sdf.format(c.getTime());
		return strBeforeDay.toString();
		
	}
}