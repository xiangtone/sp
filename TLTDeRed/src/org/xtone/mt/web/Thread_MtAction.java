package org.xtone.mt.web;
import org.eredlab.g4.rif.web.BaseAction;

/**
 * Description:利用线程查询再次链接 关闭数据库链接 ---下行 <br/> 
 * Copyright: Copyright (c) 2011<br/>
 * Company:厦门翔通信息科技有限公司<br/>
 * @author 廖鹏杰
 * @version v1.0 2011-09-29 廖鹏杰 创建文件</br>
 */
public class Thread_MtAction extends BaseAction {
	
	
	public void queryThreadMt(){
		System.out.println("下行线程queryPartners.........");
		g4Reader.queryForObject("queryPartners");
	}
}
