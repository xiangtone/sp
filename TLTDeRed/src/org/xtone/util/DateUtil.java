package org.xtone.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public final static String PATTERN_MONTH = "yyyyMM";
	public final static String PATTERN_DATE = "yyyy-MM-dd";
	/**
	 * 获取当前系统时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String yestedayDate(String pattern) {
			Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
		    calendar.add(Calendar.DATE, -1);    //得到前一天
		    String  yestedayDate = new SimpleDateFormat(pattern).format(calendar.getTime());
	   return yestedayDate;
	}
public static String yesteday2Date(String pattern) {
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
	    calendar.add(Calendar.DATE, -2);    //得到前2天
	    String  yestedayDate = new SimpleDateFormat(pattern).format(calendar.getTime());
   return yestedayDate;
}
	public static String tomorrowDate(String pattern) {
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
	    calendar.add(Calendar.DATE, +1);    //得到明天
	    String  tomorrowDate = new SimpleDateFormat(pattern).format(calendar.getTime());
   return tomorrowDate;
}
	 
	public static String getCurDate(String pattern) {
		Date date = new Date(System.currentTimeMillis());
		DateFormat simpleDate = new SimpleDateFormat(pattern);
		return simpleDate.format(date);
	}
	
	public static String dateToString(Date date){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}
	public static String dateToHh(Date date){
		SimpleDateFormat sf=new SimpleDateFormat("HH");
		return sf.format(date);
	}
	public static String dateToMM(Date date){
		SimpleDateFormat sf=new SimpleDateFormat("MM");
		return sf.format(date);
	}
	public static String dateTodd(Date date){
		SimpleDateFormat sf=new SimpleDateFormat("dd");
		return sf.format(date);
	}
	public static String dateToString2(Date date){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(date);
	}
	public static Date stringToDate(String source) throws ParseException {
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		return sf.parse(source);
	}
	
	public static String autoDay(Date date) throws ParseException 
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		return sf.format(date);
	}
	public static String nowMonth(Date date) throws ParseException 
	{
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMM");
		return sf.format(date);
	}
	public static String dateToTime(Date date)
	{
		SimpleDateFormat sf=new SimpleDateFormat("HH:mm:ss");
		return sf.format(date);
	}
	public static Date timeToDate(String source) throws ParseException 
	{
		SimpleDateFormat sf=new SimpleDateFormat("HH:mm:ss");
		return sf.parse(source);
	}
	public boolean IsTimeIn(Date time,Date begin,Date end) throws ParseException
	{
        return time.getTime()>=begin.getTime() && time.getTime()<=end.getTime();
    }

	
	/**
	 * 获取2分钟之前时间
	 * @param args
	 * @throws ParseException
	 */
	public static String getLastTime2(String pattern){
		 Calendar can = Calendar.getInstance();
		  can.add(Calendar.MINUTE, -2);
		  SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String preMonth = dateFormat.format(can.getTime());
		return preMonth;
	}
	
	
	/**
	 * 获取当前月的月份
	 * @param args
	 * @throws ParseException
	 */
	public static String getCurMonth(String pattern){
		return calcMonth(pattern,0);
	}
	/**
	 * 获取上个月的月份
	 * @param args
	 * @throws ParseException
	 */
	public static String getLastMonth(String pattern){
		return calcMonth(pattern,-1);
	}
	/**
	 * 获取上上个月的月份
	 * @param args
	 * @throws ParseException
	 */
	public static String getLastMonth2(String pattern){
		return calcMonth(pattern,-2);
	}
	/**
	 * 获取下个月的月份
	 * @param args
	 * @throws ParseException
	 */
	public static String getNextMonth(String pattern){
		return calcMonth(pattern,1);
	}
	public static String calcMonth(String pattern,int num){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, num);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String preMonth = dateFormat.format(c.getTime());
		return preMonth;
	}
	
	public static String calcDate(String pattern,int num) {
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
	    calendar.add(Calendar.DATE, num);    
	    String  tomorrowDate = new SimpleDateFormat(pattern).format(calendar.getTime());
	    return tomorrowDate;
	}
	
	public static String calcMonth(String pattern,int num,Date dt){
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MONTH, num);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String preMonth = dateFormat.format(c.getTime());
		return preMonth;
	}
	
	public static void main(String[] args) throws ParseException 
	{
		System.out.println(calcDate("yyyy-MM-dd",-91));
	}
	
	
	
	
}
