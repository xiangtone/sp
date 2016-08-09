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
import org.xtone.mt.service.inface.IMtIncomeService;
import org.xtone.mt.service.inface.IQuiteCountService;
/**
 *  @Company:Xtone集团
 *  @Description:统计合作方每个月收入情况 并比较 	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-3-2下午04:16:35
 */
public class QuiteCountAction extends BaseAction {
	
	private IQuiteCountService quiteCountService = (IQuiteCountService) super.getService("quiteCountService");

	/**
	 * 跳转页面
	 * @return
	 */
	public ActionForward quiteCountInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("load");
	}
	
	public ActionForward quiteCount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		quiteCountService.quiteCount(inDto);
		return mapping.findForward(null);	
	}
}
