package org.xtone.mt.service.impl;

import static org.xtone.util.ProbabilityUtil.detain;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 *  @Company:Xtone集团
 *  @Description: 更新状态报告，扣量模式
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-10下午04:56:11
 */

public class UpdateReprotByMtServiceImpl{
	
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	Random random=new Random();
	
	public void UpdateReprotByMt() throws Exception{
			Dto dto = new BaseDto();
			List<BaseDto> list=sqlMap.queryForList("queryMtReport", dto);
			String msgid="";
			String linkid="";
			String company="";
			String province="";
			String lostNum="";
			int lostflag=0;
			for(BaseDto baseDto1:list){
				msgid=baseDto1.getAsString("submitmsgid");
				linkid=baseDto1.getAsString("linkid");
				company=baseDto1.getAsString("company");
				province=baseDto1.getAsString("province");
				dto.put("submitmsgid", msgid);
				dto.put("linkid", linkid);
				dto.put("company", company);
				dto.put("province", province);
				List<BaseDto> list2=sqlMap.queryForList("loadMtReport",dto);
				if(list2.size()>0){
					for(BaseDto baseDto2:list2){
						String stat_msg=baseDto2.getAsString("stat_msg");
						if(stat_msg=="DELIVRD"||stat_msg.equals("DELIVRD")){
							//成功的产生扣量
							List<BaseDto> list3=sqlMap.queryForList("loadLostMr",dto);
							if(list3.size()>0){
								for(BaseDto baseDto3:list3){
									
									lostNum=baseDto3.getAsString("lostnum");
									if(StringUtils.isNotBlank(lostNum)){
										 int pBytP = Integer.parseInt(lostNum);
										 if(pBytP!=0  && detain(pBytP)){ //如果是扣量的数据
											 int r=random.nextInt(6);
											 String[] strArray={"DB:0102","CA:0051","DB:0140","DB:0103","CB:0007","MK:0021"};
											 stat_msg=strArray[r];
											 lostflag=1;
											 dto.put("sendstate", stat_msg);
											 dto.put("lostflag", lostflag);
											 sqlMap.update("updateMr", dto);
											 sqlMap.delete("deleteReportLog",dto);
								    	}else{
								    		 lostflag=0; 
								    		 dto.put("lostflag", lostflag);
								    		 dto.put("sendstate", stat_msg);
								    		 sqlMap.update("updateMr", dto);
								    		 sqlMap.delete("deleteReportLog",dto);
								    	}
									}else{
										 lostflag=0; 
							    		 dto.put("lostflag", lostflag);
										 dto.put("sendstate", stat_msg);
										 sqlMap.update("updateMr", dto);
										 sqlMap.delete("deleteReportLog",dto);
									}
								}
							}else{
								 lostflag=0; 
					    		 dto.put("lostflag", lostflag);
								 dto.put("sendstate", stat_msg);
								 sqlMap.update("updateMr", dto);
								 sqlMap.delete("deleteReportLog",dto);
							}
							
						}else{
							  //失败的状态报告，直接更新
							 lostflag=0; 
				    		 dto.put("lostflag", lostflag);
							 dto.put("sendstate", stat_msg);
							 sqlMap.update("updateMr", dto);
							 sqlMap.delete("deleteReportLog",dto);
					}
					}
				
				}
			}
	}
//	public static Random random=new Random();
//	public static boolean detain(int p){
//		int r=random.nextInt(100);
//		return r<p;
//	}
//	public static void main(String[] args) {
//		Random random=new Random();
//		String s=""; 
//		int r=random.nextInt(5);
//		 String[] strArray={"DB:0108","CA:0051","DB:0140","DB:0103","CB:0007"};
//		 s=strArray[r];
//		 System.out.println(s);
//	}
	
	
}