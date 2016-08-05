package org.xtone.mt.service.impl;

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
 *  @Date: 2012-8-20 下午02:56:29
 *  @CopyRight: Xtone.com All rights reserved
 */
public class MonthMtServiceImpl{
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	/**
	 * 
	 * @throws Exception 
	 */
	public void addMonthMt() throws Exception{
		Dto dto = new BaseDto();
		dto.put("tableMonth", "detail_month_"+DateUtil.yestedayDate("yyyyMM"));
		dto.put("tableDay", DateUtil.yestedayDate("yyyyMMdd")+"company_mt");
		sqlMap.insert("addMonthMtForLost", dto);
		sqlMap.insert("addMonthMtForSucc",dto);
		
	}
}









