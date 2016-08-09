package org.xtone.mt.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.LimitDaysQuartzJob1;
import org.xtone.mt.quartz.limitDays.LimitDaysQuartzJob2;
import org.xtone.mt.quartz.limitDays.UserNumQuartzJob;
import org.xtone.mt.service.inface.IMtIncomeService;
import org.xtone.mt.service.inface.IQuartz;
/**
 * Description: 每个月1号统计收入action类<br/> 
 * Copyright: Copyright (c) 2011<br/>
 * Company:厦门翔通信息科技有限公司<br/>
 * @author 廖鹏杰
 * @version v1.0 2011-12-08 廖鹏杰 创建文件</br>
 */
public class MtIncomeAction extends BaseAction {
	
	private IMtIncomeService mtIncomeService = (IMtIncomeService) super.getService("mtIncomeService");
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward countMtIncomeInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	/**
	 * @return
	 */
	public ActionForward countMtIncome(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request); 
		String time=inDto.getAsString("data");
		String table=time.substring(0, 4)+time.substring(5,7)+"monthcountusers";
		try{
			inDto.put("countdate", time);
			mtIncomeService.saveIncomeDetail(inDto);//统计计次
			inDto.put("table", table);
			inDto.put("addate", time);
			mtIncomeService.saveIncomeDetailByMonth(inDto);//统计包月
			mtIncomeService.updateGateType();//更新gatetype值
			write("{success:true}", response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			write("{success:false}", response);
		}
		return mapping.findForward(null);
	}
}
