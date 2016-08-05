package org.xtone.mt.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.LimitDaysQuartzJob1;
import org.xtone.mt.quartz.limitDays.LimitDaysQuartzJob2;
import org.xtone.mt.quartz.limitDays.UserNumQuartzJob;
import org.xtone.mt.service.inface.IQuartz;
/**
 * Description: 线程程序管理action类<br/> 
 * Copyright: Copyright (c) 2011<br/>
 * Company:厦门翔通信息科技有限公司<br/>
 * @author 廖鹏杰
 * @version v1.0 2011-10-10 廖鹏杰 创建文件</br>
 */
public class LimitDaysCompanysAction extends BaseAction {
	
	private IQuartz quartzService = (IQuartz) super.getService("quartzService");
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward threadInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	/**
	 * 加载开关情况
	 * @return
	 */
	public void loadQuartzs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		List eventList = g4Reader.queryForList("queryQuartzs", inDto);
		String jsonString ="{success:true,";
		for(Object object:eventList){
			BaseDto dto=(BaseDto)object; 
			jsonString+="status_"+dto.get("threadnum")+":"+dto.get("state")+",";
		}
		jsonString=jsonString.substring(0, jsonString.length()-1);
		jsonString+="}";
		write(jsonString, response);
	}
	/**
	 * 点击开始   夜间开关模式
	 * @return
	 */
	public ActionForward statrThead(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 LimitDaysQuartzJob1 job = new LimitDaysQuartzJob1();
		 String job_name1 ="11";
		 
         QuartzManager.addJob(job_name1,job,"0 01 00 * * ? *");
         LimitDaysQuartzJob2 job2 = new LimitDaysQuartzJob2();
		 String job_name2 ="12";
         QuartzManager.addJob(job_name2,job2,"0 01 08 * * ? *");
         
        CommonActionForm aForm = (CommonActionForm) form;
 		Dto inDto = aForm.getParamAsDto(request);
 		inDto.put("state", 1);
 		inDto.put("threadNum", 1000);
 		quartzService.updateDeptItem(inDto);
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
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 点击开始   统计日表用户数
	 * @return
	 */
	public ActionForward starUserNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserNumQuartzJob job = new UserNumQuartzJob();
		 String job_name1 ="13";
        QuartzManager.addJob(job_name1,job,"0 41 16 * * ? *");
		return mapping.findForward(null);
	}
	
	
	
	
}
