package org.xtone.mt.service.impl;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.DBHandle.IbatisServiceProviderForQuartz;
import org.xtone.util.DateUtil;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * @Description:
 * @Notice:
 * @Author: Dove
 * @Version 1.0
 * @Date: 2012-8-14 下午01:45:37
 * @CopyRight: Xtone.com All rights reserved
 */
public class AlterTableServiceImpl {

	SqlMapClient sqlMap = IbatisServiceProviderForQuartz.getSqlMapper();
	String tablename = "";
	String addate = "";
	String newTable = "";
	String yesterday = "";

	/**
	 * 
	 * @throws Exception
	 */
	public void alterTable() throws Exception {
		Dto dto = new BaseDto();
		tablename = DateUtil.yestedayDate("yyyyMMdd");
		addate = DateUtil.getCurDate("yyyy-MM-dd");
		yesterday = DateUtil.yestedayDate("yyyy-MM-dd");
		tablename = tablename + "company_mt";
		newTable = "tempcompany_mt" + DateUtil.yestedayDate("yyyyMMdd");
		dto.put("yesterday", yesterday);
		dto.put("tablename", tablename);
		dto.put("addate", addate);
		dto.put("newTable", newTable);
		sqlMap.insert("insertYestedayMt", dto);// 先把临时表数据为昨天的移动到昨天的日表中
		sqlMap.insert("updateTable", dto);// 更改表名称为当天日期
		sqlMap.update("createTable", dto);// 根据更改完后的表名称创建结构一样并把当天数据移动到表中
		sqlMap.insert("insertTempcompany_mt", dto);// 根据更改完后的表名称创建结构一样并把当天数据移动到表中
	}

	public static void main(String[] args) {
		AlterTableServiceImpl alterTableServiceImpl = new AlterTableServiceImpl();
		try {
			alterTableServiceImpl.alterTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
