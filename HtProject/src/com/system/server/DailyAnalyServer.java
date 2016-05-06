package com.system.server;

import com.system.dao.DailyAnalyDao;
import com.system.util.StringUtil;

public class DailyAnalyServer
{
	public boolean analyDailyMr(String date)
	{
		String dateMonth = StringUtil.getMonthFormat(date);
		return new DailyAnalyDao().analyDailyMr(date, dateMonth);
	}
}
