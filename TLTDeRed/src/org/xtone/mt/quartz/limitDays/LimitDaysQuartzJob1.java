package org.xtone.mt.quartz.limitDays;
/**
 * Description:定时器job(夜间开关模式晚上0点关闭必须关闭的合作方) ---下行 <br/> 
 * Copyright: Copyright (c) 2011<br/>
 * Company:厦门翔通信息科技有限公司<br/>
 * @author 廖鹏杰
 * @version v1.0 2011-10-10 廖鹏杰 </br>
 */
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class LimitDaysQuartzJob1  implements Job {
	private LimitDaysCompanysServiceImpl s;

    public LimitDaysQuartzJob1() {
    	s=new LimitDaysCompanysServiceImpl();
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	s.LimitDaysCompanysByClose();
    }
}
 


