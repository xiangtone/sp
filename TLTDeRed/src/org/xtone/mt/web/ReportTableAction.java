package org.xtone.mt.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.rif.web.BaseAction;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.ReportTableQuartzJob;
import org.xtone.mt.quartz.limitDays.ReportTableQuartzJob2;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description:
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-8下午02:40:15
 */
public class ReportTableAction extends BaseAction {
	String job_name1 ="reportTable";
	String jobTime="0 06 02 * * ? *";
	
	String job_name2 ="reportTable2";
	String jobTime2="0 0/10 * * * ?";
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward reportTableInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	/**
	 * 点击开始   
	 * @return
	 */
	public ActionForward startReportTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReportTableQuartzJob job = new ReportTableQuartzJob();
        QuartzManager.addJob(job_name1,job,jobTime);
        ReportTableQuartzJob2 job2 = new ReportTableQuartzJob2();
        QuartzManager.addJob(job_name2,job2,jobTime2);
		return mapping.findForward(null);
	}
}
