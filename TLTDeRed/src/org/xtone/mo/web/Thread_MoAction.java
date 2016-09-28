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
 * Description: 利用线程查询再次链接 关闭数据库链接 ---下行 <br/> 
 * Copyright: Copyright (c) 2011<br/>
 * Company:厦门翔通信息科技有限公司<br/>
 * @author 廖鹏杰
 * @version v1.0 2011-09-29 廖鹏杰 创建文件</br>
 */
public class Thread_MoAction extends BaseAction {
	
	
	public void queryThreadMo(){
		System.out.println("上行线程queryPartners.........");
		moReader.queryForObject("queryMoPartners");
		
	}
}
