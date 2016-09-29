package org.xtone.mt.service.impl;

import java.util.List;
import java.util.Map;

import org.eredlab.g4.arm.service.UserService;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.arm.util.idgenerator.IDHelper;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.G4Utils;
import org.xtone.mt.service.inface.IMtIncomeService;

/**
 * Description: 每个月1号统计收入detailService类<br/> 
 * Copyright: Copyright (c) 2011<br/>
 * Company:厦门翔通信息科技有限公司<br/>
 * @author 廖鹏杰
 * @version v1.0 2011-12-08 廖鹏杰 创建文件</br>
 */
public class MtIncomeServiceImpl extends BaseServiceImpl implements IMtIncomeService  {
	
	/**
	 * 统计计次收入情况
	 */
	public Dto saveIncomeDetail(Dto pDto) {
		Dto outDto = new BaseDto();
		List mtCountDetail=g4Dao.queryForList("queryMtCountByMonth", pDto);
		for(Object object:mtCountDetail){
			Map map=(Map)object;
			outDto.put("serverid", 	map.get("game"));
			BaseDto baseVO=(BaseDto) g4Dao.queryForObject("querySmsCostByServerid", outDto);
			String  b=(String) map.get("total");//获取条数
			int c=(Integer.valueOf(b))*baseVO.getAsInteger("price");//总收入条数*单价
			outDto.put("cpid", map.get("company"));
			outDto.put("serviceName", baseVO.getAsString("gamename"));
			outDto.put("gameid",map.get("game"));
			outDto.put("feecode_iod", baseVO.getAsString("feecode_iod"));
			outDto.put("price", baseVO.getAsString("price"));
			outDto.put("feenum", map.get("total"));
			outDto.put("ordernum", '0');
			outDto.put("linkid", '0');
			outDto.put("income", c);
			outDto.put("tjYmonth", map.get("month"));
			outDto.put("serviceType", "计次");
			g4Dao.insert("addCpIncomeDetail", outDto);
		}
		return null;

	}
	
	
	/**
	 * 统计包月收入情况
	 */
	@SuppressWarnings("unchecked")
	public Dto saveIncomeDetailByMonth(Dto pDto) {
		Dto outDto = new BaseDto();
		List mtCountDetail=g4Dao.queryForList("queryMonthByMonth", pDto);
		for(Object object:mtCountDetail){
			
			Map map=(Map)object;
			outDto.put("feecode_iod",map.get("serviceid"));
			outDto.put("spid", 	map.get("spid"));
			BaseDto baseVO=(BaseDto) g4Dao.queryForObject("querySmsCostByServeridMonth", outDto);
			
			int price=baseVO.getAsInteger("infofee");//获取单价
			Long b= (Long) map.get("dis");//获取条数
			
			int num = b.intValue();
			int c=num*price;//总收入条数*单价

	         String company=(String) map.get("company");
	         company=company.substring(0, company.length()-5);
			
			outDto.put("cpid", company);
			outDto.put("serviceName", baseVO.getAsString("gamename"));
			outDto.put("gameid",baseVO.getAsString("serverid"));
			outDto.put("feecode_iod", map.get("serviceid"));
			outDto.put("price", baseVO.getAsString("infofee"));
			outDto.put("feenum", map.get("dis"));
			outDto.put("ordernum", '0');
			outDto.put("linkid", '0');
			outDto.put("income", c);
			outDto.put("tjYmonth", map.get("month"));
			outDto.put("serviceType", "包月");
			g4Dao.insert("addCpIncomeDetail", outDto);
		}
		return null;
	}
	/**
	 * 更新gateType 字段
	 */
	public void updateGateType(){
		g4Dao.update("updateGateType1");
		g4Dao.update("updateGateType2");
		g4Dao.update("updateGateType3");
		g4Dao.update("updateGateType4");
	}


	
}
