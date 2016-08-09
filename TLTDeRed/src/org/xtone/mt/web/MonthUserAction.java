package org.xtone.mt.web;
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
 *  @Company:Xtone集团
 *  @Description:包月收入统计 	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-4-24下午01:15:49
 */
public class MonthUserAction extends BaseAction {
	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward monthUserInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	
	/**
	 * 根据页面输入的信息查询
	 */
	public ActionForward queryMonthUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		List eventList = g4Reader.queryForPage("queryUserNumByPro", inDto);
		Integer totalCount=31;
		//Integer totalCount = (Integer)g4Reader.queryForObject("queryUserNumByProForCount", inDto);
		String jsonString = encodeList2PageJson(eventList, totalCount, GlobalConstants.FORMAT_DateTime);
		write(jsonString, response);
		return mapping.findForward(null);	
	}
}
