package org.xtone.mt.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.rif.web.BaseAction;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.MonthMtQuartzJob;
/**
 * 
 *  @Description: 定时移动日表到月表中 
 *  @Notice:	
 *	@Author: Dove
 *  @Version 1.0
 *  @Date: 2012-8-20 下午03:03:54
 *  @CopyRight: Xtone.com All rights reserved
 */
public class MonthMtAction extends BaseAction {
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward monthMtInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	/**
	 * 点击开始   
	 * @return
	 */
	public ActionForward startmonthMT(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthMtQuartzJob job = new MonthMtQuartzJob();
		 String job_name1 ="monthMT";
	        QuartzManager.addJob(job_name1,job,"00 30 01 * * ?");
			return mapping.findForward(null);
	}
}
