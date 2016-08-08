package org.xtone.mt.quartz.limitDays;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xtone.mt.service.impl.ReportTableServiceImpl;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description:  	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-6-8下午02:37:44
 */
public class ReportTableQuartzJob2 implements Job {
	private ReportTableServiceImpl s;
    public ReportTableQuartzJob2() {
    	s=new ReportTableServiceImpl();
    }
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	try {
			s.insertDayReportForToday();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}
 


