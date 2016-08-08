package org.xtone.util;



import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

public class GetYesterdate
{ 
	public static String getYesterday() 
	{ 
		  Date yesterday = new Date(new Date().getTime()-24*60*60*1000);//获取昨天的日期
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");		
		  String yesterdayStr = sdf.format(yesterday);
		  return yesterdayStr;											//返回昨天日期的字符串
	} 
	
	public static String getYesterday2() 
	{ 
		  Date yesterday = new Date(new Date().getTime()-24*60*60*1000);//获取昨天的日期
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		  String yesterdayStr = sdf.format(yesterday);
		  return yesterdayStr;											//返回昨天日期的字符串
	} 
	
	/**
	 * 获取前一个月
	 * @return
	 */
	public static String getLastMonth() 
	{ 
		  SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		  Calendar cal=Calendar.getInstance();
		  cal.add(Calendar.MONTH, -1);    //得到前一个月 
		  long date = cal.getTimeInMillis();
          String d=format.format(date);
		
		  return d;
	} 
	/**
	 * 获取前一个月
	 * @return
	 */
	public static String getNowMonth() 
	{ 
		  SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		  Calendar cal=Calendar.getInstance();
		 
		  long date = cal.getTimeInMillis();
          String d=format.format(date);
		System.out.println(d);
		  return d;
	} 
	/**
	 * 获取前一个月
	 * @return
	 */
	public static String getLastMonth2() 
	{ 
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		  Calendar cal=Calendar.getInstance();
		  cal.add(Calendar.MONTH, -1);    //得到前一个月 
		  long date = cal.getTimeInMillis();
          String d=format.format(date);
		System.out.println(d);
		  return d;
	} 
	

	public static void main(String args[])
	{ 		
		GetYesterdate.getNowMonth();
//		  System.out.println(getYesterday());	
//		  System.out.println(getLastMonth());
	} 
   } 
