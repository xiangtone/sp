package org.xtone.mt.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.eredlab.g4.arm.service.UserService;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.arm.util.idgenerator.IDHelper;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.G4Utils;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.mt.service.inface.IMtIncomeService;
import org.xtone.util.DateUtil;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 *  @Company:Xtone集团
 *  @Description: 包月统计用户业务类
 *	@author: Dove
 *  @version 
 *  @Date: 2012-2-27下午04:06:34
 */
public class MonthFeeUsersServiceImpl extends BaseServiceImpl{
	
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	
	
	public void monthFeeCountUser() throws SQLException{
		Dto dto = new BaseDto();
		String month=DateUtil.getLastMonth("yyyy-MM");	
		String startDate=DateUtil.getLastMonth2("yyyy-MM")+"-20 00:00:00";
		String endDate=DateUtil.getLastMonth("yyyy-MM")+"-01 00:00:00";
		String table=DateUtil.getLastMonth("yyyyMM")+"monthcountusers";
		dto.put("month", month);
		dto.put("startDate", startDate);
		dto.put("endDate", endDate);
		dto.put("table", table);
		sqlMap.insert("addCompanysFeeUsers", dto);
		
		List limitList=sqlMap.queryForList("queryMonthUsers",dto);
		Dto limitDto = new BaseDto();
		Dto companyDto = new BaseDto();
		Dto numDto = new BaseDto();
		
		
		for(int i=0;i<limitList.size();i++){
			limitDto = (BaseDto) limitList.get(i);
			String company=limitDto.getAsString("company");
			String serviceid=limitDto.getAsString("serviceid");
			String provid=limitDto.getAsString("provid");
			String addate=DateUtil.getLastMonth("yyyy-MM")+"-01 00:00:00";
			companyDto.put("company", company);
			companyDto.put("serviceid", serviceid);
			companyDto.put("provid", provid);
			companyDto.put("table", table);
			companyDto.put("addate", addate);
			List numList=sqlMap.queryForList("countMonthFeeUsers",companyDto);
			for(int j=0;j<numList.size();j++){
				numDto = (BaseDto) numList.get(0);
				int num=numDto.getAsInteger("num");
				numDto.put("monthfeeusers", num);
				numDto.put("company", company);
				numDto.put("serviceid", serviceid);
				numDto.put("provid", provid);
				numDto.put("countmonth", month);
				
				sqlMap.update("updateCompanysFeeUsers",numDto);
			}
			
			
		}
		
	}

	
}
