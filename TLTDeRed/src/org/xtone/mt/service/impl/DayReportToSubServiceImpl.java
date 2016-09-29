package org.xtone.mt.service.impl;

import java.util.List;


import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.DBHandle.IbatisServiceProviderForDay;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.util.DateUtil;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
public class DayReportToSubServiceImpl{
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	SqlMapClient sqlMap2 = IbatisServiceProviderForDay.getSqlMapper();
	/**
	 * 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void DayReportToSub() throws Exception{
	
		//包月
		Dto pto = new BaseDto();
		String miscid2="0000";
		String cpid="";
		pto.put("countdate", DateUtil.yestedayDate("yyyy-MM-dd"));
		List listMonth=sqlMap.queryForList("queryMonthToSub",pto);
		for(int j=0;j<listMonth.size();j++){
			pto = (BaseDto) listMonth.get(j);
			if(pto.getAsString("miscid")==null||pto.getAsString("miscid")==""||pto.getAsString("miscid").equals("")){
				miscid2="0000";
			}else{
				miscid2=pto.getAsString("miscid");
			}
			cpid=pto.getAsString("cpid");
			cpid=Pattern.compile("month", Pattern.DOTALL).matcher(cpid).replaceAll("");
			pto.put("miscid", miscid2);
			pto.put("cpid", cpid);
			pto.put("servicecode", pto.getAsString("servicecode"));
			pto.put("serviceid", pto.getAsString("serviceid"));
			pto.put("countdate", pto.getAsString("countdate"));
			pto.put("order_type", "9");
			pto.put("income", pto.getAsString("income"));
			pto.put("monum", 0);
			pto.put("mousers", 0);
			pto.put("mtnum", 0);
			pto.put("mtusers", 0);
			pto.put("mtsuccnum", 0);
			pto.put("mtsuccusers", 0);
			pto.put("mtsuccmoneynum", pto.getAsString("mtsuccmoneynum"));
			pto.put("mtsuccmoneyusers", pto.getAsString("mtsuccmoneynum"));
			sqlMap.insert("insertDayreportByMonth", pto);
		}
//		Dto dto = new BaseDto();
//		String miscid="0000";
//		dto.put("countdate", DateUtil.yestedayDate("yyyy-MM-dd"));
//		List list=sqlMap.queryForList("queryDayReportToSub",dto);
//		for(int j=0;j<list.size();j++){
//			dto = (BaseDto) list.get(j);
//			if(dto.getAsString("miscid")==null||dto.getAsString("miscid")==""||dto.getAsString("miscid").equals("")){
//				miscid="0000";
//			}else{ 
//				miscid=dto.getAsString("miscid");
//			}
//			dto.put("miscid", miscid);
//			dto.put("cpid", dto.getAsString("cpid"));
//			dto.put("service_id", dto.getAsString("servicecode"));
//			dto.put("calledno", dto.getAsString("serviceid"));
//			dto.put("date_time", dto.getAsString("countdate"));
//			dto.put("order_type", dto.getAsString("order_type"));
//			dto.put("spname", "通联移动");
//			dto.put("income", dto.getAsString("income"));
//			dto.put("mo_all_num", dto.getAsString("monum"));
//			dto.put("mo_all_user_num", dto.getAsString("mousers"));
//			dto.put("mt_all_num", dto.getAsString("mtsuccnum"));
//			dto.put("mt_all_user_num", dto.getAsString("mtsuccusers"));
//			dto.put("mt_succ_num", dto.getAsString("mtsuccmoneynum"));
//			dto.put("mt_succ_user_num", dto.getAsString("mtsuccmoneyusers"));
//			dto.put("tableName", "datacenter_"+DateUtil.yestedayDate("yyyyMM"));
//			sqlMap2.insert("insertDataCenter", dto);
//		}
		
	}
}


