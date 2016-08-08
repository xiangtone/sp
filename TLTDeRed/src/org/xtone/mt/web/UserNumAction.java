package org.xtone.mt.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.UserNumQuartzJob;
import org.xtone.mt.service.inface.IQuartz;
/**
 * Description: <br/> 
 * Copyright: Copyright (c) 2011<br/>
 * Company:厦门翔通信息科技有限公司<br/>
 * @author 廖鹏杰
 * @version v1.0 2011-11-28 廖鹏杰 创建文件</br>
 */
public class UserNumAction extends BaseAction {
	private IQuartz quartzService = (IQuartz) super.getService("quartzService");
	/**
	 * 点击开始   夜间开关模式
	 * @return
	 */
	public ActionForward statrThead(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
        
         
//        CommonActionForm aForm = (CommonActionForm) form;
// 		Dto inDto = aForm.getParamAsDto(request);
// 		inDto.put("state", 1);
// 		inDto.put("threadNum", 1000);
// 		quartzService.updateDeptItem(inDto);
 		
         
		return mapping.findForward(null);
	}
	/**
	 * 点击关闭   夜间开关模式
	 * @return
	 */
	public ActionForward stopThead(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 String job_name ="11";
         QuartzManager.removeJob(job_name);
        
		 String job_name2 ="12";
         QuartzManager.removeJob(job_name2);
         
         CommonActionForm aForm = (CommonActionForm) form;
  		Dto inDto = aForm.getParamAsDto(request);
  		inDto.put("state", 2);
  		inDto.put("threadNum", 1000);
  		quartzService.updateDeptItem(inDto);
		return mapping.findForward(null);
	}
	
	
	
	
	
	
}
