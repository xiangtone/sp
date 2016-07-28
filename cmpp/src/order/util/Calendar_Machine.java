package order.util;
//package TimeTest;
/*
该类获取当前的时间、月份、年，用于相应的应用
*author:tang
*time:2005-11-03
*/
import java.util.*;
import java.text.*;
public class Calendar_Machine{
	int num = 0;
	public int Get_Year(){//取得年
		Calendar calendar_ope = Calendar.getInstance();
		num = calendar_ope.get(Calendar.YEAR);
		return num;
		
	}
	public int Get_Month(){//取得月
		Calendar calendar_ope = Calendar.getInstance();
		num = calendar_ope.get(Calendar.MONTH);
		return num;
	}
	public int Get_Day(){//取得日
		Calendar calendar_ope = Calendar.getInstance();
		num = calendar_ope.get(Calendar.DATE);
		return num;
	}
	public  int Get_Hour(){//取得小时
		Calendar calendar_ope = Calendar.getInstance();
		num = calendar_ope.get(Calendar.HOUR);
		return num;
	}
	public int Get_Dayofweek(){//得到该天在本周的周日子（从0开始）
		Calendar calendar_ope = Calendar.getInstance();
		num = calendar_ope.get(Calendar.DAY_OF_WEEK);
			return num;
	}
	public String Get_sendtime(){//取得格式：yyyy-MM-dd HH:mm:ss的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String send_time = sdf.format(d);
		return send_time;	
	}
	public String Get_time(){//取得时间的小时
		Date d = new Date();
		String s = DateFormat.getDateTimeInstance().format(d);
		int time_position = s.indexOf(" ");
		int time_position1 = s.indexOf(":");
		return s.substring(time_position + 1,time_position1);
	}
	public static void main(String[] args){
		
		Calendar_Machine cm = new Calendar_Machine();
		int week_day = cm.Get_Dayofweek();
		int time = cm.Get_Hour();
		System.out.println("the week day is:" + week_day);
		System.out.println("the time is:" + time);
		Calendar calendar_ope = Calendar.getInstance();
		System.out.println("am and pm test:" + calendar_ope.get(Calendar.AM));
	}
}


