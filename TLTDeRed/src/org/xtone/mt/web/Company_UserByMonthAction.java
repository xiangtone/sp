package org.xtone.mt.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.rif.web.BaseAction;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.Company_UserByMonthQuartzJob;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description:每个月1号统计包月 数据到YYYYMMmonthcountusers	 	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-17下午05:51:53
 */
public class Company_UserByMonthAction extends BaseAction {
	String jobTime="0 01 05 01 * ?";//每个月1号凌晨05点1分 
	/**
	 * 跳转页面1306
	 * @return
	 */
	public ActionForward companyUserByMonthInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	/**
	 * 点击开始   
	 * @return
	 */
	public ActionForward startCompanyUserByMonth(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Company_UserByMonthQuartzJob job = new Company_UserByMonthQuartzJob();
		 String job_name1 ="Company_UserByMonth";
        QuartzManager.addJob(job_name1,job,jobTime);
		return mapping.findForward(null);
	}
}
