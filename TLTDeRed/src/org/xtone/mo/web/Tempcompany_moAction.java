package org.xtone.mo.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * Description: 短信sms上行临时表action类<br/> 
 * Copyright: Copyright (c) 2011<br/>
 * Company:厦门翔通信息科技有限公司<br/>
 * @author 廖鹏杰
 * @version v1.0 2011-09-28 廖鹏杰 创建文件</br>
 */
public class Tempcompany_moAction extends BaseAction {
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward temtMoInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}

	/**
	 * 根据页面输入的信息查询
	 */
	public ActionForward queryTemtMo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		List eventList = moReader.queryForPage("queryTempMo", inDto);
		Integer totalCount = (Integer)moReader.queryForObject("queryTempMoForCount", inDto);
		String jsonString = encodeList2PageJson(eventList, totalCount, GlobalConstants.FORMAT_DateTime);
		write(jsonString, response);
		return mapping.findForward(null);	
	}
}
