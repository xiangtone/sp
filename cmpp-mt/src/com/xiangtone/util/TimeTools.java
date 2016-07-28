/*
 * Created on 2006-11-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiangtone.util;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.util.*;
public class TimeTools {
	public static String get_month(){
		long ll= System.currentTimeMillis();
		String sbf = new String("");
	    String sss = new String("yyyy_MM");    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);  
	    sbf = sdf.format(new java.util.Date(ll));  
	    String cur_time=sbf.toString();
	    return cur_time;
	    //System.out.println(cur_time);
	}
}
