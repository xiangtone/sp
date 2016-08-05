package org.xtone.mt.service.impl;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.util.DateUtil;
import org.xtone.util.GetYesterdate;

import com.ibatis.sqlmap.client.SqlMapClient;
/**
 *  @Company:Xtone集团
 *  @Description: 每个月1号统计包月数据用户	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-5-15下午04:41:39
 */
public class Company_UserByMonthServiceImpl{

	 SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	 String tablename1="sms_platform.sms_mtlog"+GetYesterdate.getLastMonth();
	 String tablename2 = "smscompanymonthorders."+GetYesterdate.getLastMonth()+"monthcountusers";//表的名称
	
	 /**
	  * 处理上月遗留的用户信息
	  * @throws SQLException 
	  */
	 public void leaveUser() throws SQLException{
		 Dto dto = new BaseDto();
		 dto.put("table", "leave_user_"+DateUtil.getCurDate("yyyyMM"));
		 dto.put("addate", DateUtil.getCurDate("yyyy-MM-dd")+" 00:00:00");
		 sqlMap.update("createLeaveUser", dto);//创建新表
		 sqlMap.insert("insertLeaveUser", dto);//加入新表数据
	 }
	 /**
	  * 创建表detail_month_yyyyMM
	  */
	 public void createDetailMonth() throws SQLException{
		 Dto dto = new BaseDto();
		 dto.put("table", "detail_month_"+DateUtil.getCurDate("yyyyMM"));
		 sqlMap.update("createDetailMonth", dto);//创建新表
		
	 }
	/**
	 * 筛选出state 为1且addate 在上月20号之前的数据
	 * @throws SQLException
	 */
	public void queryCompany_UserByMonth1() throws SQLException{
		String cpn="";
		String serviceid="";
	    int num;
	    int price;
		Dto dto = new BaseDto();
		String querydate=GetYesterdate.getLastMonth()+"20";
		dto.put("querydate", querydate);
		List<BaseDto> list=sqlMap.queryForList("queryMonthUserBy",dto);
			for(BaseDto baseDto:list){
				cpn=baseDto.getAsString("cpn");
				serviceid=baseDto.getAsString("serviceid");
				if(baseDto.getAsInteger("price")==null||"".equals(baseDto.getAsInteger("price"))){
					price=0;
				}else{
					price=baseDto.getAsInteger("price");
				}
				dto.put("cpn", cpn);
				dto.put("serviceid", serviceid);
				dto.put("tablename1", tablename1);
				num = ((BaseDto)sqlMap.queryForObject("queryUserNum", dto)).getAsInteger("num");
				    if(num<price/100){
				    	System.out.println("不存入数据库.....");
				    }else{
				    	dto.put("tablename2", tablename2);
				    	dto.put("company", baseDto.getAsString("company"));
				    	dto.put("spid", baseDto.getAsString("spid"));
				    	dto.put("infofee", price);
				    	dto.put("state", baseDto.getAsString("state"));
				    	dto.put("addate", baseDto.getAsString("addate"));
				    	dto.put("changedate", baseDto.getAsString("pausedate"));
				    	dto.put("provid", baseDto.getAsString("provid"));
				    	sqlMap.insert("insertMonthCountUsers", dto);
				    }
			}
	}
	
	/**
	 * 筛选出state为非1且addate 在上个月1号之后且在上月21号之前的数据
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public List<BaseDto> queryCompany_UserByMonth2() throws SQLException, ParseException{
	    List<BaseDto> c =new ArrayList<BaseDto>();
	    String lastMonthStart=GetYesterdate.getLastMonth()+"01";
	    String lastMonthEnd=GetYesterdate.getLastMonth()+"19";
		Dto dto = new BaseDto();
		dto.put("lastMonthStart", lastMonthStart);
		dto.put("lastMonthEnd", lastMonthEnd);
		String addate="";
		String pausedate="";
		String stopdate="";
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		List <BaseDto> list=sqlMap.queryForList("queryMonthUser2",dto);
		for(BaseDto baseDto:list){
			addate=baseDto.getAsString("addate");
			pausedate=baseDto.getAsString("pausedate");
			stopdate=baseDto.getAsString("stopdate");
			//把3个string类型的转换成date类型
			Date add = d.parse(addate); 
			Date pause=d.parse(pausedate);
			Date stop=d.parse(stopdate);
			//当2个同时有值
			if(pause.getTime()-add.getTime()>0&&stop.getTime()-add.getTime()>0){
				long a =pause.getTime()-add.getTime(); 			
			    long a1 = a / (1000 * 60 * 60 * 24);
			    long b=stop.getTime()-add.getTime();
			    long b1 = b / (1000 * 60 * 60 * 24);
			    if(a1>b1){
			    	if(b1<3){
			    		continue;
			    	}
			    	else{
			    		c.add(baseDto);
			    	}
			    }
			    if(a1<b1){
			    	if(a1<3){
			    		continue;
			    	}
			    	else{
			    		c.add(baseDto);
			    	}
			    }
			}
			//当pausdate有值，stop没值的
			if(pause.getTime()-add.getTime()>0&&stop.getTime()-add.getTime()<0){
				long l =pause.getTime()-add.getTime(); 			
			    long i = l / (1000 * 60 * 60 * 24);
				if(i<3){
					continue;
				}else{
					c.add(baseDto);
				}
			}
			//当stop有值，pausdate没值
			if(pause.getTime()-add.getTime()<0&&stop.getTime()-add.getTime()>0){
				long l =stop.getTime()-add.getTime(); 			
			    long i = l / (1000 * 60 * 60 * 24);
				if(i<3){
					continue;
				}else{
					c.add(baseDto);
				}
			}
		}
			return c;
	}
	
	/**
	 * 在筛选后 存入数据库
	 * @param c
	 * @throws SQLException 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void insertMonthCountUsers(List<BaseDto> c) throws SQLException{
		int num;
		int price;
		Dto dto = new BaseDto();
		for(BaseDto v:c){
			dto.put("cpn", v.getAsString("cpn"));
			dto.put("serviceid", v.getAsString("serviceid"));
			dto.put("tablename1", tablename1);
			dto.put("tablename2", tablename2);
			price=v.getAsInteger("price");
			num = ((BaseDto)sqlMap.queryForObject("queryUserNum", dto)).getAsInteger("num");
			 if(num<price/100){
			    	System.out.println("不做任何操作");
			    }else{
			    	if(((BaseDto)sqlMap.queryForObject("queryMonthByCpn", dto)).getAsString("cpn")==null||((BaseDto)sqlMap.queryForObject("queryMonthByCpn", dto)).getAsString("cpn")==""){
			    		dto.put("company", v.getAsString("company"));
				    	dto.put("spid", v.getAsString("spid"));
				    	dto.put("infofee", price);
				    	dto.put("state", v.getAsString("state"));
				    	dto.put("addate", v.getAsString("addate"));
				    	dto.put("changedate", v.getAsString("pausedate"));
				    	dto.put("provid", v.getAsString("provid"));
				    	sqlMap.insert("insertMonthCountUsers", dto);
			    	}
			    }
		}
		
	}
}


