package org.xtone.mt.service.impl;

import java.util.List;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.util.SendMsg;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description: 创世SMS监测上行是否发送条数	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-10下午04:56:11
 */
public class AlertPoliceServiceImpl{
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	/**
	 * 10分钟查询一次，是否上行有数据上来
	 * @throws Exception 
	 */
	public void queryMoCountNum() throws Exception{
		//报警url
		String url="http://wxoa.nomo.cn/platform/interface/warn_interface.php";
		Dto dto = new BaseDto();
		String company="";
		String companyName="";
		int numCount=0;
		String content="";
		List<BaseDto> list=sqlMap.queryForList("queryMoCountNum", dto);
			for(BaseDto baseDto:list){
				company=baseDto.getAsString("company");
				companyName=baseDto.getAsString("companyname");
				numCount=baseDto.getAsInteger("num");
				if(numCount>500){
					//发送彩信报警
					content="通联天地SMS合作方"+companyName+company+"MO未发送条"+numCount+"请检查相对应程序";
					url=url+"?cpn=15259295973;13859905479&content="+java.net.URLEncoder.encode(content)+"&level=2";
					SendMsg.send(url);
					System.out.println("==============="+url);
				}
			}
	}
}
//参数cpn 手机号码  多个手机号码用  ; 隔开
//参数 content  下发内容 需经过encode编码  GBK格式
//参数 level  等级设置
//Level=1 任何时间接收到均马上下发
//Level=2 在8-23点 直接收到马上下发，其他时间收到回执 早上8点之后下发
//Level=9 任何时间接收到均不马上下发 ，每20分钟统一下发一次








