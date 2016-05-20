
package com.system.server;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.system.util.ConfigManager;
import com.system.util.StringUtil;

public class TimerServer
{
	Logger log = Logger.getLogger(TimerServer.class);

	public void startTimer()
	{
		startAnalyDataFromHtSummer();
	}

	//每天早上三点分析浩天的数据到大数据平台
	private void startAnalyDataFromHtSummer()
	{
		Calendar ca = Calendar.getInstance();

		int curHour = ca.get(Calendar.HOUR_OF_DAY);

		if (curHour > 3)
		{
			ca.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		ca.set(Calendar.HOUR_OF_DAY, 3);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);

		//ca.set(Calendar.HOUR_OF_DAY, 21);
		//ca.set(Calendar.MINUTE, 15);
		//ca.set(Calendar.SECOND, 0);

		long firstTime = ca.getTimeInMillis();

		//24小时
		long periodTime = 3600000*24;

		// long periodTime = 120000;

		long delayMils = firstTime - System.currentTimeMillis();

		log.info("大数据分析准备开启:" + delayMils);
		log.info("周期时间是:" + periodTime);
		Timer timer = new Timer();
		timer.schedule(new AnalyDataTimerTask1(), delayMils, periodTime);
		log.info("已经启动了大数据分析定时任务...");
	}
	
	public static void main(String[] args)
	{
		ConfigManager.setConfigFilePath("");
		
		new TimerServer().startTimer();
	}

	//每隔一小时扫描daily_log.tbl_mr_xxxx，把更新的数据传到大数据中心
	private class AnalyDataTimerTask1 extends TimerTask
	{
		@Override
		public void run()
		{
			Calendar ca = Calendar.getInstance();
			
			ca.add(Calendar.DAY_OF_MONTH, -1);
			
			String feeDate = StringUtil.getDateFormat(ca.getTime());
			
			ca.add(Calendar.DAY_OF_MONTH, -3);
			
			String monthFeeDate = StringUtil.getDateFormat(ca.getTime());
			
			new DailyDataServer().analyDataToSummer(feeDate,monthFeeDate);
		}
	}
	
}
