package org.xtone.mt.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.util.DateUtil;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 *  @Company:Xtone集团
 *  @Description: 统计日报表	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-10下午04:56:11
 */
public class ReportTableServiceImpl{
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	private final Log log = LogFactory.getLog(getClass());
	/**
	 * 统计日报表第二天早上统计昨天的	
	 * @throws Exception 
	 */
	public void insertDayReport(){
		try{
			Dto dto = new BaseDto();
			String company="";
			String game="";
			String province="";
			String mtnum="";
			String mtusers="";
			String mtsuccnum="";
			String mtsuccusers="";
			int mtsuccmoneynum;
			String mtsuccmoneyusers="";
			int price;
			int income;
			String tableNameMT=DateUtil.yestedayDate("yyyyMMdd")+"company_mt";
			String tableNameMO="smscompanymos."+DateUtil.yestedayDate("yyyyMMdd")+"company_mo";
			dto.put("tableNameMT", tableNameMT);
			dto.put("tableNameMO", tableNameMO);
			dto.put("countdate", DateUtil.yestedayDate("yyyy-MM-dd"));
            dto.put("table", tableNameMO);
			sqlMap.update("updateCpnByPro",dto);
            dto.put("table", "smscompanymts."+tableNameMT);
            sqlMap.update("updateCpnByPro",dto);
			sqlMap.delete("deleteDayReport", dto);
			sqlMap.delete("deleteLostDetail", dto);
			sqlMap.insert("insertDayReportByLostFlag", dto);
//			sqlMap.insert("insertLostDetail", dto);
			sqlMap.delete("deleteLostDetailTemp",dto);
			sqlMap.insert("insertLostDetailTempMT", dto);
			sqlMap.insert("insertLostDetailTempMO", dto);
			sqlMap.insert("insertLostDetailFromTemp", dto);
			sqlMap.delete("deleteLostDetailTemp",dto);
			sqlMap.insert("insertDayReportByMo", dto);
			
			List<BaseDto> list=sqlMap.queryForList("queryMtByAll", dto);
			for(BaseDto baseDto1:list){
				company=baseDto1.getAsString("company");
				game=baseDto1.getAsString("game");
				province=baseDto1.getAsString("province");
				mtnum=baseDto1.getAsString("mtnum");
				mtusers=baseDto1.getAsString("mtusers");
				dto.put("mtnum", mtnum);
				dto.put("mtusers", mtusers);
				dto.put("company", company);
				dto.put("miscid", province);
				dto.put("serviceid", game);
				sqlMap.update("updateDayreport", dto);
			}
			Dto bto = new BaseDto();
			bto.put("tableNameMT", tableNameMT);
			List<BaseDto> list2=sqlMap.queryForList("queryMtBySucc", dto);
			for(BaseDto baseDto2:list2){
				company=baseDto2.getAsString("company");
				game=baseDto2.getAsString("game");
				province=baseDto2.getAsString("province");
				mtsuccnum=baseDto2.getAsString("mtsuccnum");
				mtsuccusers=baseDto2.getAsString("mtsuccusers");
				mtsuccmoneynum=baseDto2.getAsInteger("mtsuccmoneynum");
				mtsuccmoneyusers=baseDto2.getAsString("mtsuccusers");
				price=baseDto2.getAsInteger("price");
				income=mtsuccmoneynum*price;
				bto.put("company", company);
				bto.put("miscid", province);
				bto.put("serviceid", game);
				bto.put("mtsuccnum", mtsuccnum);
				bto.put("mtsuccusers", mtsuccusers);
				bto.put("mtsuccmoneynum", mtsuccmoneynum);
				bto.put("mtsuccmoneyusers", mtsuccmoneyusers);
				bto.put("income", income);
				bto.put("countdate",  DateUtil.yestedayDate("yyyy-MM-dd"));
				sqlMap.update("updateDayreport", bto);
			}
			
			
			
		}catch(Exception e){
			log.error("insertDayReport() error:",e);
		}
			
		}
	/**
	 * 时时统计
	 * @throws Exception 
	 */
	public void insertDayReportForToday(){
		try{
			Dto dto = new BaseDto();
			String company="";
			String game="";
			String province="";
			String mtnum="";
			String mtusers="";
			String mtsuccnum="";
			String mtsuccusers="";
			int mtsuccmoneynum;
			String mtsuccmoneyusers="";
			int price;
			int income;
			String tableNameMT=DateUtil.getCurDate("yyyyMMdd")+"company_mt";
			String tableNameMO="smscompanymos."+DateUtil.getCurDate("yyyyMMdd")+"company_mo";
			
			dto.put("tableNameMO", tableNameMO);
			dto.put("countdate", DateUtil.getCurDate("yyyy-MM-dd"));
			dto.put("tableNameMT", tableNameMT);
			
			sqlMap.delete("deleteDayReport", dto);
			sqlMap.delete("deleteLostDetail", dto);
			sqlMap.insert("insertDayReportByLostFlag", dto);
//			sqlMap.insert("insertLostDetail", dto);
			sqlMap.delete("deleteLostDetailTemp",dto);
			sqlMap.insert("insertLostDetailTempMT", dto);
			sqlMap.insert("insertLostDetailTempMO", dto);
			sqlMap.insert("insertLostDetailFromTemp", dto);
			sqlMap.delete("deleteLostDetailTemp",dto);
			sqlMap.insert("insertDayReportByMo", dto);
			
			List<BaseDto> list=sqlMap.queryForList("queryMtByAll", dto);
			for(BaseDto baseDto1:list){
				company=baseDto1.getAsString("company");
				game=baseDto1.getAsString("game");
				province=baseDto1.getAsString("province");
				mtnum=baseDto1.getAsString("mtnum");
				mtusers=baseDto1.getAsString("mtusers");
				dto.put("mtnum", mtnum);
				dto.put("mtusers", mtusers);
				dto.put("company", company);
				dto.put("miscid", province);
				dto.put("serviceid", game);
				sqlMap.update("updateDayreport", dto);
			}
			
			
			Dto bto = new BaseDto();
			bto.put("tableNameMT", tableNameMT);
			List<BaseDto> list2=sqlMap.queryForList("queryMtBySucc", dto);
			for(BaseDto baseDto2:list2){
				company=baseDto2.getAsString("company");
				game=baseDto2.getAsString("game");
				province=baseDto2.getAsString("province");
				mtsuccnum=baseDto2.getAsString("mtsuccnum");
				mtsuccusers=baseDto2.getAsString("mtsuccusers");
				mtsuccmoneynum=baseDto2.getAsInteger("mtsuccmoneynum");
				mtsuccmoneyusers=baseDto2.getAsString("mtsuccusers");
				price=baseDto2.getAsInteger("price");
				income=mtsuccmoneynum*price;
				bto.put("company", company);
				bto.put("miscid", province);
				bto.put("serviceid", game);
				bto.put("mtsuccnum", mtsuccnum);
				bto.put("mtsuccusers", mtsuccusers);
				bto.put("mtsuccmoneynum", mtsuccmoneynum);
				bto.put("mtsuccmoneyusers", mtsuccmoneyusers);
				bto.put("income", income);
				bto.put("countdate", DateUtil.getCurDate("yyyy-MM-dd"));
				sqlMap.update("updateDayreport", bto);
			}
		}catch(Exception e){
			log.error("insertDayReportForToday() error:",e);
		}
	}
	
}