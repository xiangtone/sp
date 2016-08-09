package org.xtone.mt.service.impl;

import java.util.List;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.util.DateUtil;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 *  @Description: 
 *  @Notice:	
 *	@Author: Dove
 *  @Version 1.0
 *  @Date: 2012-9-24 下午04:08:55
 *  @CopyRight: Xtone.com All rights reserved
 */
public class MonthSuccCpnNumServiceImpl{
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	/**
	 * @throws Exception
	 */
	public void queryMonthSuccCpnNum() throws Exception{
		 Dto dto = new BaseDto();
		 dto.put("tableName", DateUtil.getCurDate("yyyyMMdd")+"company_mt");
		 dto.put("startTime", DateUtil.getLastTime2("yyyy-MM-dd HH:mm:ss"));
		 dto.put("endTime", DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss"));
		 
		 List <BaseDto> list1=sqlMap.queryForList("queryDayByCpn",dto);
		 
		for(BaseDto baseDto:list1){
				dto.put("cpn", baseDto.getAsString("cpn"));
				dto.put("succNum", baseDto.getAsInteger("num"));
				List <BaseDto> list2=sqlMap.queryForList("queryIfCpn",dto);
				if(list2.size()>0){
					//存在  执行update
					sqlMap.update("updateMonthSuccCpnNum",dto);
				}else{
					//不存在 执行insert
					sqlMap.insert("addMonthSuccCpnNum",dto);
				}	
		}
	}
}