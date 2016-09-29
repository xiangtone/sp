package org.xtone.mt.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.rif.web.BaseAction;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.AlterTableQuartzJob;
/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description:检查网关报警程序 	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-8下午02:40:15
 */
public class AlterTableAction extends BaseAction {
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward alterTableInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	/**
	 * 点击开始   
	 * @return
	 */
	public ActionForward startAlterTable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 AlterTableQuartzJob job = new AlterTableQuartzJob();
		 String job_name1 ="alterTable";
	     QuartzManager.addJob(job_name1,job,"00 01 00 * * ?");
	return mapping.findForward(null);
	}
}
