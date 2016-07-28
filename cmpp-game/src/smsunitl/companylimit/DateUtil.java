package smsunitl.companylimit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
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
//	public static void main(String[] args) throws ParseException 
//	{
//		System.out.println(tomorrowDate("yyyy-MM-dd"));
//	}
	public static void main(String[] args) throws ParseException {
		String addate="2012-05-20 13:12:12";
		String failtime="2012-05-23 12:12:12";
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		Date add = d.parse(addate); 
		Date failt=d.parse(failtime);
		
		System.out.println(failt.getDate()-add.getDate());
	}
	
	
}
