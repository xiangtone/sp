package org.xtone.mt.service.impl;

import java.util.List;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.DBHandle.IbatisServiceProviderForDay;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.util.DateUtil;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 *  
 *  @Company:Xtone集团
 *  @Description: 同步扣量数据包OA	
 *	@author: CWQ
 *  @version 
 *  @Date: 2013-5-16
 */
public class DayReportklToOAServiceImpl{
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	SqlMapClient sqlMap2 = IbatisServiceProviderForDay.getSqlMapper();
	/**
	 * 
	 * @throws Exception
	 */
	public void DayReportklToOA() throws Exception{
//		for(int i=0;i<=0;i++){
		
		Dto dto = new BaseDto();
		String miscid="0000";
		dto.put("countdate", DateUtil.yestedayDate("yyyy-MM-dd"));
		List list=sqlMap.queryForList("queryDayReportklToOA",dto);
		for(int j=0;j<list.size();j++){
			dto = (BaseDto) list.get(j);
			if(dto.getAsString("miscid")==null||dto.getAsString("miscid")==""||dto.getAsString("miscid").equals("")){
				miscid="0000";
			}else{
				miscid=dto.getAsString("miscid");
			}
			dto.put("miscid", miscid);
			dto.put("cpid", dto.getAsString("cpid"));
			dto.put("service_id", dto.getAsString("servicecode"));
			dto.put("calledno", "");
			dto.put("spname", "通联移动");
			dto.put("service_type", "SMS");
			dto.put("order_type", "1");
			dto.put("cpname", dto.getAsString("partnername"));
			dto.put("mo_num", dto.getAsString("monum"));
			dto.put("mo_income", dto.getAsString("moincome"));
			dto.put("mt_num", dto.getAsString("mtnum"));
			dto.put("mt_income", dto.getAsString("income"));
			dto.put("date_time", dto.getAsString("countdate"));                                            
			dto.put("tableName", "dayreport_kl_"+DateUtil.yestedayDate("yyyyMM"));
			sqlMap2.insert("insertDayReportklToOA", dto);   
		}
	}
		
//	}
}


