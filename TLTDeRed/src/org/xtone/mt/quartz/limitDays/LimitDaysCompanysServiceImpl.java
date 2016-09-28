package org.xtone.mt.quartz.limitDays;
/**
 * Description:定时器job(夜间开关模式业务逻辑类) ---下行 <br/> 
 * Copyright: Copyright (c) 2011<br/>
 * Company:厦门翔通信息科技有限公司<br/>
 * @author 廖鹏杰
 * @version v1.0 2011-10-10 廖鹏杰 </br>
 */
import java.sql.SQLException;
import java.util.List;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.util.DateUtil;

import com.ibatis.sqlmap.client.SqlMapClient;
import common.Logger;


public class LimitDaysCompanysServiceImpl{
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	private static Logger logger=Logger.getLogger(LimitDaysCompanysServiceImpl.class);
	
	/**
	 * 实现业务逻辑线程（针对00点的时候的业务）
	 * @return  
	 */
	public void LimitDaysCompanysByClose(){
		try {
			sqlMap.delete("deleteLogLimitClose");
			List limitList=sqlMap.queryForList("queryNotlimitday");//查询 不执行0点至8点的合作方
			Dto limitDto = new BaseDto();
			Dto companyDto = new BaseDto();
			Dto dto = new BaseDto();
			Dto adto = new BaseDto();
			String a="";
			for(int i=0;i<limitList.size();i++){
				limitDto = (BaseDto) limitList.get(i);
				String company=limitDto.getAsString("company");
				a+=",'"+company+"'";
			}
			a=a.substring(1, a.length());
			dto.put("listcompany", a);
			dto.put("yesterday", DateUtil.yestedayDate("yyyy-MM-dd"));
			
			
			List capsCPList=sqlMap.queryForList("queryLogcloseswitch",dto);
			for(int i=0;i<capsCPList.size();i++){
				adto.clear();
				companyDto=(BaseDto) capsCPList.get(i);
				String company=companyDto.getAsString("company");
				String miscid=companyDto.getAsString("miscid");
				String nowTime=DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss");
				adto.put("company", company);
				adto.put("miscid", miscid);
				adto.put("sendflag", "0");
				adto.put("addflag", nowTime);
				adto.put("flag", "A");
				sqlMap.update("updateCompanyProvSwitch", adto);
				logger.info("0点开启"+adto.toJson());
			}
			
			
			List companyList=sqlMap.queryForList("queryCompanyprovswitch",dto);
			String nowTime=DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss");
			for(int i=0;i<companyList.size();i++){
				adto.clear();
				companyDto=(BaseDto) companyList.get(i);
				String company=companyDto.getAsString("company");
				String miscid=companyDto.getAsString("miscid");
				String addate=companyDto.getAsString("addate");
				adto.put("company", company);
				adto.put("miscid", miscid);
				adto.put("addate", addate);
				adto.put("addflag", nowTime);
				adto.put("sendflag", "1");
				adto.put("flag", "Y");
				sqlMap.insert("addLogLimitClose", adto);
				sqlMap.update("updateCompanyProvSwitch", adto);
			}
		} catch (Exception e) {
			logger.error("0点开启异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 实现业务逻辑线程（针对8点的时候的业务）
	 * @return
	 */
	public void LimitDaysCompanysByState(){
		try {
			List logList = sqlMap.queryForList("queryLogLimitClose");
			Dto logDto = new BaseDto();
			Dto dto = new BaseDto();
			String a="";
			String nowTime=DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss");
				for(int i=0;i<logList.size();i++){
					dto.clear();
					logDto = (BaseDto) logList.get(i);
					String company=logDto.getAsString("company");
					String miscid=logDto.getAsString("miscid");
					dto.put("company", company);
					dto.put("miscid", miscid);
					dto.put("addflag",nowTime);
					dto.put("sendflag", "0");
					dto.put("flag", "Y");
					sqlMap.update("updateCompanyProvSwitch", dto);
				}
		} catch (Exception e) {
			logger.error("8点开启异常", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 特殊合作方处理（针对联肯信息（风谷文化)7点的时候的业务）
	 * @return
	 */
	public void LimitDaysCompanysByStateLkxx(){
		try {
			List logList = sqlMap.queryForList("queryLogLimitCloseLkxx");
			Dto logDto = new BaseDto();
			Dto dto = new BaseDto();
			String a="";
			String nowTime=DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss");
			for(int i=0;i<logList.size();i++){
				logDto = (BaseDto) logList.get(i);
				String company=logDto.getAsString("company");
				String miscid=logDto.getAsString("miscid");
				dto.put("company", company);
				dto.put("miscid", miscid);
				dto.put("addflag",nowTime);
				dto.put("sendflag", "0");
				dto.put("flag", "Y");
				sqlMap.update("updateCompanyProvSwitch", dto);
			}
		} catch (SQLException e) {
			logger.error("7点开启异常", e);
			e.printStackTrace();
		}
	}
}
