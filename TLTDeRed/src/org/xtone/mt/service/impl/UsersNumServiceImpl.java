package org.xtone.mt.service.impl;

import java.sql.SQLException;

import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.rif.web.BaseAction;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.mt.service.inface.IUsersNumService;
import org.xtone.util.DateUtil;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 统计上行 下行用户数
 * 
 */
public class UsersNumServiceImpl extends BaseServiceImpl  implements IUsersNumService{
	
	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	
	/**
	 * 保存用户
	 * @param pDto
	 * @return
	 * @throws SQLException 
	 */
	public void saveUsersNumItem() throws SQLException {
		Dto inDto = new BaseDto();
		String day=DateUtil.yestedayDate("yyyy-MM-dd");
		String moTab=DateUtil.yestedayDate("yyyyMMdd")+"company_mo";
		String mtTab=DateUtil.yestedayDate("yyyyMMdd")+"company_mt";
		inDto.put("tableNameMo", moTab);
		inDto.put("tableNameMt", mtTab);
		Integer monum = (Integer)sqlMap.queryForObject("queryMoUsers", inDto);
		Integer mtnum = (Integer)sqlMap.queryForObject("queryMtUsers", inDto);
		Dto pDto=new BaseDto();
		pDto.put("usermo", monum);
		pDto.put("usermt", mtnum);
		pDto.put("day", day);
		sqlMap.insert("addUsersNum", pDto);
	}
	
	
}
