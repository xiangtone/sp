package org.xtone.mt.quartz.limitDays;
import java.util.List;

import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xtone.mt.service.impl.Company_UserByMonthServiceImpl;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description:每个月1号统计包月数据用户	 	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-17下午02:26:22
 */
public class Company_UserByMonthQuartzJob implements Job {
	
	private Company_UserByMonthServiceImpl s;

    public Company_UserByMonthQuartzJob() {
    	s=new Company_UserByMonthServiceImpl();
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	try {
			s.queryCompany_UserByMonth1();
			List<BaseDto> c=s.queryCompany_UserByMonth2();
			s.insertMonthCountUsers(c);
			s.leaveUser();
			s.createDetailMonth();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}