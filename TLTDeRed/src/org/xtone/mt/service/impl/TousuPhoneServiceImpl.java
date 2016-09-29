package org.xtone.mt.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.util.ConfigTool;
import org.xtone.util.DateUtil;
import org.xtone.util.FileTool;
import org.xtone.util.URLConnectionUtil;

import com.ibatis.sqlmap.client.SqlMapClient;

public class TousuPhoneServiceImpl extends BaseServiceImpl {
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	static Logger log = Logger.getLogger(TousuPhoneServiceImpl.class); 
	/**
	 * 投诉号码入库 
	 * @param dto 参数
	 * @param list 要处理的手机号数据
	 */
	public void insertPhonesBatch( Dto para , List<String>list){
		try {
			String idoa = para.getAsString("idoa");
			String pid = para.getAsString("pid");
			
			sqlMap.startTransaction();
			sqlMap.startBatch();
			int errorNum=0;
			Set<String> set = new HashSet<String>();
			set.addAll(list);//去重
			Iterator<String> it = set.iterator();
			while(it.hasNext()){
				String phone = it.next();
				if(isPhone(phone)){
					Dto dto = new BaseDto();
					dto.put("idoa",idoa);
					dto.put("phone", phone);
					sqlMap.insert("insertTousuPhone", dto);
					//插入黑名单
					if("1".equals(para.getAsString("black"))){
						insertBlackPhone(dto);
					}
				}else{
					errorNum++;
				}
			}
			sqlMap.executeBatch();
			sqlMap.commitTransaction();
			sqlMap.endTransaction();
			
			
			//统计数据信息
			int countNum = list.size();
			int repeatNum = countNum - set.size();
			para.put("countnum", countNum);
			para.put("repeatnum", repeatNum);
			para.put("errornum", errorNum);
			sqlMap.insert("insertTousuInfo", para);
			//保存处理结果,1为全部 2为计次 3为包月
			String type=para.getAsString("type");
			String datestart = para.getAsString("datestart");// 开始时间，包含该天
			String dateend = para.getAsString("dateend");// 结束时间，包含该天
			if("1".equals(type)){
				insertTousuResult(idoa,datestart,dateend);
				insertTousuResultMonth(idoa);
			}else if("2".equals(type)){
				insertTousuResult(idoa,datestart,dateend);
			}else if("3".equals(type)){
				insertTousuResultMonth(idoa);
			}

			//生成txt文件
			queryToTxt(idoa,para.getAsString("path"));
			
			//通知OA
			sendOAInfo(countNum,repeatNum,errorNum,idoa,pid);
			String file = para.getAsString("contextpath");
			sendOAFile(file, idoa, pid);
			//最后清除该批次手机号
			sqlMap.delete("deleteTousuPhone",para);
			
		} catch (Exception e) {
			log.error("",e);
			e.printStackTrace();
		}
	
	}
	/**
	 * 插入计次结果
	 * @param idoa
	 * @throws SQLException 
	 * @throws Exception 
	 */
	private void insertTousuResult(String idoa,String start,String end) throws Exception{
		String table="detail_month_";
		String tableMt1="";
		String tableMt2="";
		String tableMt3="";
		String tableMt4="";
		String tableMt5="";
		String tableMt6="";
		
		Dto dto = new BaseDto();
		dto.put("idoa", idoa);
		dto.put("companyname",ConfigTool.getValue("company_name") );
		dto.put("service", "SMS");
		dto.put("pricetype", "计次");
		dto.put("startDate", start);
		dto.put("endDate", end);
		
		Date startDate=DateUtil.stringToDate(start);
		Date endDate=DateUtil.stringToDate(end);
		//相差月份数
		int monthNum = (endDate.getYear() - startDate.getYear())*12 +endDate.getMonth() - startDate.getMonth();
		switch (monthNum) {
		case 0:
			tableMt1=table+DateUtil.nowMonth(startDate);
			dto.put("tableMtOne", tableMt1);
			sqlMap.insert("insertTousuResultA", dto);
			break;
		case 1:
			tableMt1=table+DateUtil.nowMonth(startDate);
			tableMt2=table+DateUtil.nowMonth(endDate);
			dto.put("tableMtOne", tableMt1);
			dto.put("tableMtTwo", tableMt2);
			sqlMap.insert("insertTousuResultB", dto);
			break;
		case 2:
			tableMt1=table+DateUtil.nowMonth(startDate);
			tableMt2=table+DateUtil.calcMonth("yyyyMM",1,DateUtil.stringToDate(start));
			tableMt3=table+DateUtil.nowMonth(endDate);
			dto.put("tableMtOne", tableMt1);
			dto.put("tableMtTwo", tableMt2);
			dto.put("tableMtThree", tableMt3);
			sqlMap.insert("insertTousuResultC", dto);
			break;
		case 3:
			tableMt1=table+DateUtil.nowMonth(startDate);
			tableMt2=table+DateUtil.calcMonth("yyyyMM",1,DateUtil.stringToDate(start));
			tableMt3=table+DateUtil.calcMonth("yyyyMM",2,DateUtil.stringToDate(start));
			tableMt4=table+DateUtil.nowMonth(endDate);
			dto.put("tableMtOne", tableMt1);
			dto.put("tableMtTwo", tableMt2);
			dto.put("tableMtThree", tableMt3);
			dto.put("tableMtFour", tableMt4);
			sqlMap.insert("insertTousuResultD", dto);
			break;
		case 4:
			tableMt1=table+DateUtil.nowMonth(startDate);
			tableMt2=table+DateUtil.calcMonth("yyyyMM",1,DateUtil.stringToDate(start));
			tableMt3=table+DateUtil.calcMonth("yyyyMM",2,DateUtil.stringToDate(start));
			tableMt4=table+DateUtil.calcMonth("yyyyMM",3,DateUtil.stringToDate(start));
			tableMt5=table+DateUtil.nowMonth(endDate);
			dto.put("tableMtOne", tableMt1);
			dto.put("tableMtTwo", tableMt2);
			dto.put("tableMtThree", tableMt3);
			dto.put("tableMtFour", tableMt4);
			dto.put("tableMtFive", tableMt5);
			sqlMap.insert("insertTousuResultE", dto);
			break;
		case 5:
			tableMt1=table+DateUtil.nowMonth(startDate);
			tableMt2=table+DateUtil.calcMonth("yyyyMM",1,DateUtil.stringToDate(start));
			tableMt3=table+DateUtil.calcMonth("yyyyMM",2,DateUtil.stringToDate(start));
			tableMt4=table+DateUtil.calcMonth("yyyyMM",3,DateUtil.stringToDate(start));
			tableMt5=table+DateUtil.calcMonth("yyyyMM",4,DateUtil.stringToDate(start));
			tableMt6=table+DateUtil.nowMonth(endDate);
			dto.put("tableMtOne", tableMt1);
			dto.put("tableMtTwo", tableMt2);
			dto.put("tableMtThree", tableMt3);
			dto.put("tableMtFour", tableMt4);
			dto.put("tableMtFive", tableMt5);
			dto.put("tableMtSix", tableMt6);
			sqlMap.insert("insertTousuResultF", dto);
			break;
		default:
			tableMt1=table+DateUtil.getCurMonth(DateUtil.PATTERN_MONTH);
			tableMt2=table+DateUtil.getLastMonth(DateUtil.PATTERN_MONTH);
			tableMt3=table+DateUtil.getLastMonth2(DateUtil.PATTERN_MONTH);
			dto.put("tableMtOne", tableMt1);
			dto.put("tableMtTwo", tableMt2);
			dto.put("tableMtThree", tableMt3);
			sqlMap.insert("insertTousuResultC", dto);
			break;
		}
	}
	/**
	 * 插入包月结果
	 * @param idoa
	 * @throws SQLException 
	 */
	private void insertTousuResultMonth(String idoa) throws SQLException{
		String countdate=DateUtil.calcDate(DateUtil.PATTERN_DATE,-365);
		Dto dto = new BaseDto();
		dto.put("countdate", countdate);
		dto.put("idoa", idoa);
		dto.put("orderlabel", "订购");
		dto.put("cancellabel", "取消");
		dto.put("companyname",ConfigTool.getValue("company_name") );
		dto.put("companynametwo",ConfigTool.getValue("company_name_two") );
		dto.put("pricetype", "包月");
		sqlMap.insert("insertTousuResultMonth", dto);
	}
	/**
	 * 组成结果集生成Txt
	 * @param idoa
	 * @param path
	 * @throws Exception 
	 */
	private void queryToTxt(String idoa,String path) throws Exception{
			List<BaseDto> allList = new ArrayList<BaseDto>();
			List<BaseDto> list=queryResultPerTime(idoa);
			List<BaseDto> list2=queryResultMonth(idoa);
			allList.addAll(list);
			allList.addAll(list2);
			
			StringBuilder sb = new StringBuilder();
			String parten="###";
			for(BaseDto dt:allList){
				sb.append(dt.getAsString("companyname")).append(parten);
				sb.append(dt.getAsString("service")).append(parten);
				sb.append(dt.getAsString("pricetype")).append(parten);
				sb.append(dt.getAsString("phone")).append(parten);
				sb.append(dt.getAsString("moneytime")).append(parten);
				sb.append(dt.getAsString("servicename")).append(parten);
				sb.append(dt.getAsString("cpname")).append(parten);
				sb.append(dt.getAsString("money")).append(parten);
//				sb.append(dt.getAsString("province")).append(parten);
//				sb.append(dt.getAsString("city"));
				sb.append("").append(parten);
				sb.append("");
				sb.append("\r\n");
			}
			FileTool.writeTxt(sb.toString(), path);
	}
	/**
	 * 计次记录查询
	 * @param idoa
	 * @return
	 * @throws SQLException 
	 */
	private List<BaseDto> queryResultPerTime(String idoa) throws SQLException{
		Dto dto = new BaseDto();
		dto.put("idoa", idoa);
		dto.put("pricetype", "计次");

		List<BaseDto> list=new ArrayList<BaseDto>();
		List<BaseDto> ls = sqlMap.queryForList("queryTousuResult",dto);
		for(BaseDto baseDto:ls){
			String phone=baseDto.getAsString("phone");
			String cpname=baseDto.getAsString("cpname");
			dto.put("phone", phone);
			dto.put("cpname", cpname);
			//按号码，合作方分组
			List<BaseDto> lsMore = sqlMap.queryForList("queryTousuResultMore",dto);
			String serviceName="";
			int money=0;
			Date minDate=null;
			Date maxDate=null;
			
			for(BaseDto dtoMore:lsMore){
				//拼接业务名称
				serviceName+=dtoMore.getAsString("servicename")+"、";
				//累加金额
				money+=dtoMore.getAsInteger("money");
				//日期比较，找出最大，最小
				Date minDateTemp=dtoMore.getAsDate("mintime");
				Date maxDateTemp=dtoMore.getAsDate("maxtime");
				if(minDate==null){
					minDate=minDateTemp;
				}else if(minDate.after(minDateTemp)){
					minDate=minDateTemp;
				}
				if(maxDate==null){
					maxDate=maxDateTemp;
				}else if(maxDateTemp.after(maxDate)){
					maxDate=maxDateTemp;
				}
			}
			String moneyTime="";
			if(null!=minDate&&null!=maxDate){
				if(minDate.equals(maxDate)){
					moneyTime=DateUtil.dateToString2(minDate);
				}else{
					moneyTime=DateUtil.dateToString2(minDate)+"至"+DateUtil.dateToString2(maxDate);
				}
			}
			if(lsMore.size()>0){
			BaseDto newDto =lsMore.get(0);
				if(serviceName.indexOf("、")>0){
					serviceName=serviceName.substring(0,serviceName.lastIndexOf("、"));
				}
				newDto.put("servicename", serviceName);
				newDto.put("money", money);
				newDto.put("moneytime", moneyTime);
				list.add(newDto);
			}
			
		}
		return list;
	}
	/**
	 * 包月记录查询
	 * @param idoa
	 * @return
	 * @throws SQLException 
	 */
	private List<BaseDto> queryResultMonth(String idoa) throws SQLException{
		Dto dto = new BaseDto();
		dto.put("idoa", idoa);
		dto.put("pricetype", "包月");

		List<BaseDto> list=new ArrayList<BaseDto>();
		List<BaseDto> ls = sqlMap.queryForList("queryResultMonth",dto);
		for(BaseDto baseDto:ls){
			//分组多条数据拼接
			if(baseDto.getAsInteger("num")>1){
				String phone = baseDto.getAsString("phone");
				String serviceName = baseDto.getAsString("servicename");
				String cpname = baseDto.getAsString("cpname");
				BaseDto queryMore = new BaseDto();
				queryMore.put("idoa", idoa);
				queryMore.put("pricetype", "包月");
				queryMore.put("phone", phone);
				queryMore.put("servicename", serviceName);
				queryMore.put("cpname", cpname);
				List<BaseDto> lsMore = sqlMap.queryForList("queryResultMonthMore",queryMore);
				String momeyTimeMore="";
				for(BaseDto dtoMore:lsMore){
					//拼接订购，取消时间
					momeyTimeMore+=dtoMore.getAsString("moneytime")+" ";
				}
				if(lsMore.size()>0){
					BaseDto newDto =lsMore.get(0);
					newDto.put("moneytime", momeyTimeMore);
					list.add(newDto);
				}
			}else{
				list.add(baseDto);
			}
		}
		return list;
	}
	/**
	 * 手机号校验
	 * @param phone
	 * @return
	 */
	private boolean isPhone(String phone){
		//纯数字，11位
		String regEx = "[0-9]{11}+$";   
		Pattern pat = Pattern.compile(regEx);  
		Matcher mat = pat.matcher(phone);  
		return mat.matches();  
	}
	/**
	 * 插入黑名单
	 * @param idoa 批次号
	 * @return
	 * @throws SQLException 
	 */
	private void insertBlackPhone(Dto para) throws SQLException{
		List<BaseDto> ls1 =sqlMap.queryForList("checkWhitePhone",para);
		if(ls1.size()<=0){
			List<BaseDto> ls2 =sqlMap.queryForList("checkBlackPhone",para);
			if(ls2.size()<=0){
				sqlMap.insert("insertBlackPhone", para);
			}
		}
	}
	/**
	 * 提交处理结果
	 * @param count 纪录总数
	 * @param repeat 重复总数
	 * @param error 错误总数
	 * @param id  唯一批次号
	 * @param pid 业务线ID号
	 * @return	1为成功 -1为失败
	 * @throws Exception 
	 */
	private String sendOAInfo(int count,int  repeat,int  error,String id,String pid) throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("count", count);
		paramMap.put("repeat", repeat);
		paramMap.put("error", error);
		paramMap.put("id", id);
		paramMap.put("pid", pid);
		String urlStr = ConfigTool.getValue("tousu_info");
		String result="-1";
		int i=0;

		//请求失败，再次请求，最多3次
		while(result.contains("-1")){
			result= URLConnectionUtil.doPost(urlStr, paramMap);
			i++;
			if(i==3){
				log.error(urlStr + "请求失败:" + result);
				break;
			}
		}
	
		return result;
	}
	/**
	 * 提交文件
	 * @param file 文件地址
	 * @param id 唯一批次号
	 * @param pid 业务线ID号
	 * @return 1为成功 -1为失败
	 * @throws Exception 
	 */
	private String sendOAFile(String file,String id,String pid) throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("file", file);
		paramMap.put("id", id);
		paramMap.put("pid", pid);
		String urlStr = ConfigTool.getValue("tousu_file");
		String result="-1";
		int i=0;
		
		while(result.contains("-1")){
				result= URLConnectionUtil.doPost(urlStr, paramMap);
				log.error("test file:"+file);
				log.info("test id:"+id);
				log.info("test pid:" +pid);
				log.info("test result:"+result);
				i++;
				if(i==3){
					log.error(urlStr + "请求失败:" + result);
					break;
				}
			}
	
		return result;
	}

}
