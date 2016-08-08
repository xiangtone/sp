package org.xtone.mt.quartz.limitDays;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class LimitDaysQuartzJob3  implements Job {
	private LimitDaysCompanysServiceImpl s;

    public LimitDaysQuartzJob3() {
    	s=new LimitDaysCompanysServiceImpl();
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	s.LimitDaysCompanysByStateLkxx();
    }
}
 


