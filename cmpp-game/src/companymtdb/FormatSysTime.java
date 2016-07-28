/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
package companymtdb;

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
	public static String getCurrentTimeB()
	{
		long ll= System.currentTimeMillis();
	    String sbf = new String("");
	    String sss = new String("yyyy-MM-dd");
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);
	    sbf = sdf.format(new java.util.Date(ll));
	    String cur_time=sbf.toString();
	    return cur_time;
    }

    	public static String getvalidtime(int c_year,int c_month,int c_day,int c_hour,int c_minutes,int c_second){
    		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
    		Date d1 = new GregorianCalendar(c_year,c_month,c_day,c_hour,c_minutes,c_second).getTime();
    		return formatter.format(d1).toString();
    	}
}