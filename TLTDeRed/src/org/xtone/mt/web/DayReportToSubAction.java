package org.xtone.mt.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.rif.web.BaseAction;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.DayReportToSubQuartzJob;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description:移动日报到服务器
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-8下午02:40:15
 */
public class DayReportToSubAction extends BaseAction {
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward dayReportToSubInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	/**
	 * 点击开始   
	 * @return
	 */
	public ActionForward startDayReportToSub(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DayReportToSubQuartzJob job = new DayReportToSubQuartzJob();
		 String job_name1 ="DayReportToSub";
        QuartzManager.addJob(job_name1,job,"00 30 03 * * ?");
		return mapping.findForward(null);
	}
}
