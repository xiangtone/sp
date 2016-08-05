package org.xtone.mt.quartz.limitDays;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xtone.mt.service.impl.MonthMtServiceImpl;
/**
 * 
 *  @Description:定时移动日表到月表中 
 *  @Notice:	
 *	@Author: Dove
 *  @Version 1.0
 *  @Date: 2012-8-20 下午03:02:41
 *  @CopyRight: Xtone.com All rights reserved
 */
public class MonthMtQuartzJob implements Job {
	
	private MonthMtServiceImpl s;

    public MonthMtQuartzJob() {
    	s=new MonthMtServiceImpl();
    }
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	try {
			s.addMonthMt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
 


