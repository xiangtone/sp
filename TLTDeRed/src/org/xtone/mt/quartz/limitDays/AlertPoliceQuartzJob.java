package org.xtone.mt.quartz.limitDays;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xtone.mt.service.impl.AlertPoliceServiceImpl;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description: 网关报警程序 	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-8下午02:37:44
 */
public class AlertPoliceQuartzJob implements Job {
	
	private AlertPoliceServiceImpl s;

    public AlertPoliceQuartzJob() {
    	s=new AlertPoliceServiceImpl();
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	try {
			s.queryMoCountNum();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
 


