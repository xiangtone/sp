package org.xtone.mt.quartz.limitDays;

import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xtone.mt.service.impl.MonthFeeUsersServiceImpl;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description:每个月统计一次包月数据 	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-2-27下午05:45:30
 */
public class MonthFeeUsersQuartzJob  implements Job {
	private MonthFeeUsersServiceImpl monthFeeService;
    public MonthFeeUsersQuartzJob() {
    	monthFeeService=new MonthFeeUsersServiceImpl();
    }
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	try {
    		monthFeeService.monthFeeCountUser();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
 


