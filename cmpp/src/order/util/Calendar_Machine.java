package order.util;
//package TimeTest;
/*
�����ȡ��ǰ��ʱ�䡢�·ݡ��꣬������Ӧ��Ӧ��
*author:tang
*time:2005-11-03
*/
import java.util.*;
import java.text.*;
public class Calendar_Machine{
	int num = 0;
	public int Get_Year(){//ȡ����
		Calendar calendar_ope = Calendar.getInstance();
		num = calendar_ope.get(Calendar.YEAR);
		return num;
		
	}
	public int Get_Month(){//ȡ����
		Calendar calendar_ope = Calendar.getInstance();
		num = calendar_ope.get(Calendar.MONTH);
		return num;
	}
	public int Get_Day(){//ȡ����
		Calendar calendar_ope = Calendar.getInstance();
		num = calendar_ope.get(Calendar.DATE);
		return num;
	}
	public  int Get_Hour(){//ȡ��Сʱ
		Calendar calendar_ope = Calendar.getInstance();
		num = calendar_ope.get(Calendar.HOUR);
		return num;
	}
	public int Get_Dayofweek(){//�õ������ڱ��ܵ������ӣ���0��ʼ��
		Calendar calendar_ope = Calendar.getInstance();
		num = calendar_ope.get(Calendar.DAY_OF_WEEK);
			return num;
	}
	public String Get_sendtime(){//ȡ�ø�ʽ��yyyy-MM-dd HH:mm:ss��ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String send_time = sdf.format(d);
		return send_time;	
	}
	public String Get_time(){//ȡ��ʱ���Сʱ
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


