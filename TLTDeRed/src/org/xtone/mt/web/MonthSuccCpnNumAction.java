package org.xtone.mt.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.rif.web.BaseAction;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.MonthSuccCpnNumQuartzJob;

public class MonthSuccCpnNumAction extends BaseAction {
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward monthSuccCpnNumInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	/**
	 * 点击开始   
	 * @return
	 */
	public ActionForward startMonthSuccCpnNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthSuccCpnNumQuartzJob job = new MonthSuccCpnNumQuartzJob();
		 String job_name1 ="MonthSuccCpnNum";
        QuartzManager.addJob(job_name1,job,"0 0/2 * * * ?");
		return mapping.findForward(null);
	}
}
