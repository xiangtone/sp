package org.xtone.mt.quartz.limitDays;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xtone.mt.service.impl.UpdateReprotByMtServiceImpl;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description:更新状态报告，扣量模式	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-2-27下午05:45:30
 */
public class UpdateReprotByMtQuartzJob  implements Job {
	
	private UpdateReprotByMtServiceImpl updateReprotByMtService;
	
    public UpdateReprotByMtQuartzJob() {
    	updateReprotByMtService=new UpdateReprotByMtServiceImpl();
    }
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
    		try {
				updateReprotByMtService.UpdateReprotByMt();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    	
    }
}
 


