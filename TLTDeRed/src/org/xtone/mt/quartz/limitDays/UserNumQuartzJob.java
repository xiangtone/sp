package org.xtone.mt.quartz.limitDays;
/**
 * Description:定时器job(凌晨一点统计上下行的用户数) --- <br/> 
 * Copyright: Copyright (c) 2011<br/>
 * Company:厦门翔通信息科技有限公司<br/>
 * @author 廖鹏杰
 * @version v1.0 2011-10-10 廖鹏杰 </br>
 */
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.xtone.mt.service.impl.UsersNumServiceImpl;

public class UserNumQuartzJob  implements Job {
	private UsersNumServiceImpl userNumService;
    public UserNumQuartzJob() {
    	userNumService=new UsersNumServiceImpl();
    }
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	try {
			userNumService.saveUsersNumItem();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
 


