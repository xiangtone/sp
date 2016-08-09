package org.xtone.mt.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.rif.web.BaseAction;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.MonthFeeUsersQuartzJob;
/**
 *  @Company:Xtone集团
 *  @Description:每个月统计一次包月数据 	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-2-27下午05:48:13
 */
public class MonthFeeUsersAction extends BaseAction {
	
	//String jobTime="30 02 05 02 * ?";//每个月的2号凌晨5点02分30秒
	String jobTime="0 52 10 02 * ?";//每个月的2号凌晨5点02分30秒
	
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward monthFeeUsersInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	
	/**
	 * 点击开始   
	 * @return
	 * 
	 */
	public ActionForward statrMonthFeeUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthFeeUsersQuartzJob job = new MonthFeeUsersQuartzJob();
		 String job_name2 ="100";
         QuartzManager.addJob(job_name2,job,jobTime);
		return mapping.findForward(null);
	}
	
}
