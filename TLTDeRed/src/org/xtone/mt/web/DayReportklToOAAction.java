package org.xtone.mt.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.rif.web.BaseAction;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.DayReportklToOAQuartzJob;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description:同步扣量数据到OA服务器
 *	@author: CWQ
 *  @version 
 *  @Date: 2012-5-16
 */
public class DayReportklToOAAction extends BaseAction {
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward dayReportklToOAInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	/**
	 * 点击开始   
	 * @return
	 */
	public ActionForward startDayReportklToOA(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DayReportklToOAQuartzJob job = new DayReportklToOAQuartzJob();
		 String job_name1 ="DayReportklToOA";
        QuartzManager.addJob(job_name1,job,"00 30 02 * * ?");
		return mapping.findForward(null);
	}
}
