package org.xtone.mt.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.rif.web.BaseAction;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.AlertPoliceQuartzJob;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description:检查网关报警程序 	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-8下午02:40:15
 */
public class AlertPoliceAction extends BaseAction {
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward alertPoliceInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	/**
	 * 点击开始   
	 * @return
	 */
	public ActionForward startAlertPolice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AlertPoliceQuartzJob job = new AlertPoliceQuartzJob();
		 String job_name1 ="AlertPolice";
        QuartzManager.addJob(job_name1,job,"0 0/10 * * * ?");
		return mapping.findForward(null);
	}
}
