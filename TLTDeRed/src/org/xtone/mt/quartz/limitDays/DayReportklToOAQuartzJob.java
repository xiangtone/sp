package org.xtone.mt.quartz.limitDays;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xtone.mt.service.impl.DayReportklToOAServiceImpl;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description: 移动日报到服务器
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-8下午02:37:44
 */
public class DayReportklToOAQuartzJob implements Job {
	
	private DayReportklToOAServiceImpl s;

    public DayReportklToOAQuartzJob() {
    	s=new DayReportklToOAServiceImpl();
    }
    

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	try {
			s.DayReportklToOA();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
 


