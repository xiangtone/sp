package com.system.util;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.org.apache.regexp.internal.recompile;


public class StringUtil 
{
	Logger logger = Logger.getLogger(StringUtil.class);
	
	public static boolean isNullOrEmpty(String source)
	{
		return source==null || "".equals(source);
	}
	
	public static String getString(String str, String defaultValue)
	{
		return isNullOrEmpty(str) ? defaultValue : str;
	}
	
	public static int getInteger(String str, int defaultValue)
	{
		try
		{
			return Integer.parseInt(str);
		}
		catch (Exception ex)
		{
			
		}
		return defaultValue;
	}
	
	public static Float getFloat(String str, float defaultValue)
	{
		try
		{
			return Float.parseFloat(str);
		}
		catch (Exception ex)
		{
			
		}
		return defaultValue;
	}
	/**
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static Double getDouble(String str, double defaultValue)
	{
		try
		{
			return Double.valueOf(str);
		}
		catch (Exception ex)
		{
			
		}
		return defaultValue;
	}
	
	private final static char[]	hexDigits	= { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static String bytesToHex(byte[] bytes)
	{
		StringBuffer sb = new StringBuffer();
		int t;
		for (int i = 0; i < 16; i++)
		{
			t = bytes[i];
			if (t < 0)
				t += 256;
			sb.append(hexDigits[(t >>> 4)]);
			sb.append(hexDigits[(t % 16)]);
		}
		return sb.toString();
	}
	
	public static String getMd5String(String input, int bit)
	{
		try
		{
			if(StringUtil.isNullOrEmpty(input))
				return "";
			
			MessageDigest md = MessageDigest.getInstance(System.getProperty(
					"MD5.algorithm", "MD5"));
			
			if (bit == 32)
				return bytesToHex(md.digest(input.getBytes("utf-8")));
			
			
			if(bit==24)
				return bytesToHex(md.digest(input.getBytes("utf-8")))
						.substring(0, 24);
			
			if (bit == 16)
				return bytesToHex(md.digest(input.getBytes("utf-8")))
						.substring(8, 24);
			
			if (bit == 8)
			{
				return bytesToHex(md.digest(input.getBytes("utf-8")))
						.substring(8, 16);
			}
			
			return bytesToHex(md.digest(input.getBytes("utf-8")));
		}
		catch (Exception e)
		{
			
		}
		return "";
	}
	
	public static String getDefaultDate()
	{
		return sdf1.format(new Date());
	}
	
	public static String getMonthHeadDate()
	{
		Calendar ca = Calendar.getInstance();
		
		ca.set(Calendar.DAY_OF_MONTH, 1);
		
		return sdf1.format(ca.getTime());
	}
	
	public static String getMonthEndDate()
	{
		Calendar ca = Calendar.getInstance();
		
		ca.set(Calendar.DAY_OF_MONTH, 1);
		
		ca.add(Calendar.MONTH, 1);
		
		ca.add(Calendar.DAY_OF_YEAR, -1);
		
		return sdf1.format(ca.getTime());
	}
	
	public static String getMonthEndDate2()
	{
		Calendar ca = Calendar.getInstance();
		
		ca.set(Calendar.DAY_OF_MONTH, 1);
		
		ca.add(Calendar.MONTH, 1);
		
		ca.add(Calendar.DAY_OF_YEAR, -1);
		
		return sdf3.format(ca.getTime());
	}
	
	public static String getDateFormat(Date date)
	{
		return sdf1.format(date);
	}
	
	public static String getMonthFormat(Date date)
	{
		return sdf2.format(date);
	}
	
	public static String getPreDayOfMonth()
	{
		Calendar ca = Calendar.getInstance();
		
		ca.add(Calendar.DAY_OF_MONTH, -1);
		
		return sdf1.format(ca.getTime());
	}
	
	public static String getPreDayOfMonth2()
	{
		Calendar ca = Calendar.getInstance();
		
		ca.add(Calendar.DAY_OF_MONTH, -1);
		
		return sdf3.format(ca.getTime());
	}
	
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyMMdd");
	
	//传入 yyyy-MM-dd 的格式 传回 yyyyMM 的格式
	public static String getMonthFormat(String date)
	{
		if(isNullOrEmpty(date))
			return getMonthFormat();
		
		try{ return sdf2.format(sdf1.parse(date));}
		catch(Exception ex){}
		
		return sdf2.format(new Date());
	}
	
	public static  String getMonthFormat()
	{
		return sdf2.format(new Date());
	}
	
	private static DecimalFormat df1 = new DecimalFormat("###0.00%");
	private static DecimalFormat df2 = new DecimalFormat("###0.00");
	
	public static String getPercent(int data1,int data2)
	{
		data2 = data2==0 ? 1 : data2;
		return df1.format((float)data1/(float)data2);
	}
	
	public static String getDecimalFormat(float data)
	{
		return df2.format(data);
	}
	
	public static String getDecimalFormat(double data)
	{
		return df2.format(data);
	}
	
	public static String mergerStrings(String[] oris,String splitor)
	{
		if(oris==null)
			return "";
		
		if(oris.length==1)
			return oris[0];
		
		String values = "";
		
		for(String s : oris)
			values += s + splitor;
		
		values = values.substring(0,values.length()-1);
			
		return values;
	}
	
	public static String[] stringToStringArray(String string){
		String[]arr = string.split("},");
        for(int i=0;i<arr.length-1;i++){
        	arr[i]+="}";
        }
        return arr;
	}
	
	public static String stringArrayToString(String [] stringArray){
	    if (stringArray == null || stringArray.length <= 0) {
	      return null;
	    }
	    return Arrays.toString(stringArray).replaceAll("^\\[| |\\]$", "");
	  }
	
	public static String stringListToString(List<String> stringList){
	    if (stringList == null || stringList.size() <= 0) {
	      return null;
	    }
	    return stringList.toString().replaceAll("^\\[| |\\]$", "");
	  }
	
	public static String dateTimeToDateTime(String time){
		if(time.length()<=6){
			return time;
		}
		System.out.println(time.length());
		time = time.substring(2, time.length());
		String[] list = null;
		list = time.split("-");
		time = "";
		for (String string : list) {
			time += string;
		}
//		System.out.println(Arrays.toString(list));
//		time = Arrays.toString(list).replaceAll("^\\[| |\\]$","");
		return time;
	}
	
	public static String parseAscii(String str){
        StringBuilder sb=new StringBuilder();
        byte[] bs=str.getBytes();
        for(int i=0;i<bs.length;i++)
            sb.append(toHex(bs[i]));
        return sb.toString();
    }
	
	public static String toHex(int n){
        StringBuilder sb=new StringBuilder();
        if(n/10==0){
            return toHexUtil(n);
        }else{
            String t=toHex(n/16);
            int nn=n%10;
            sb.append(t).append(toHexUtil(nn));
        }
        return sb.toString();
    }
	
	private static String toHexUtil(int n){
        String rt="";
        switch(n){
        case 10:rt+="A";break;
        case 11:rt+="B";break;
        case 12:rt+="C";break;
        case 13:rt+="D";break;
        case 14:rt+="E";break;
        case 15:rt+="F";break;
        case 16:rt+="G";break;
        case 17:rt+="H";break;
        case 18:rt+="I";break;
        case 19:rt+="J";break;
        case 20:rt+="K";break;
        case 21:rt+="L";break;
        case 22:rt+="M";break;
        case 23:rt+="N";break;
        default:
            rt+=n;
        }
        return rt;
    }
	
    public static String stringToAscii(String value)  
    {  
        StringBuffer sbu = new StringBuffer();  
        char[] chars = value.toCharArray();   
        for (int i = 0; i < chars.length; i++) {  
            if(i != chars.length - 1)  
            {  
                sbu.append((int)chars[i]).append(",");  
            }  
            else {  
                sbu.append((int)chars[i]);  
            }  
        }  
        return sbu.toString();  
    }  
    
	
	public static void main(String[] args)
	{
		///List<String> list = new ArrayList<String>();
		//list.add("1");
		//list.add("4");
		//list.add("5");
		//list.add("2");
		//System.out.println(StringUtil.stringListToString(list));
		//StringUtil.letterToNum("A");
		//System.out.println(getMonthEndDate());
		//System.out.println(StringUtil.dateTimeToDateTime("2015-12-01"));
		
		System.out.println(StringUtil.stringToAscii("G"));
	}
	
}
