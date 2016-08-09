package org.xtone.mt.quartz.limitDays;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xtone.mt.service.impl.MonthSuccCpnNumServiceImpl;

public class MonthSuccCpnNumQuartzJob implements Job {
	
	private MonthSuccCpnNumServiceImpl s;

    public MonthSuccCpnNumQuartzJob() {
    	s=new MonthSuccCpnNumServiceImpl();
    }
    

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	try {
			s.queryMonthSuccCpnNum();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
 


