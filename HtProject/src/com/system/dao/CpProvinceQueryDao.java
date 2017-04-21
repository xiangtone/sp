package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;
import com.system.model.CpProvinceModel;
import com.system.util.StringUtil;

public class CpProvinceQueryDao
{
	public void loadCpProvince(final int cpId)
	{
		String sql = "SELECT f.id operator_id,f.name_cn,b.price,c.provinces";
		sql += " FROM " + com.system.constant.Constant.DB_DAILY_CONFIG + ".tbl_trone_order a";
		sql += " LEFT JOIN " + com.system.constant.Constant.DB_DAILY_CONFIG + ".tbl_trone b ON a.trone_id = b.id";
		sql += " LEFT JOIN " + com.system.constant.Constant.DB_DAILY_CONFIG + ".tbl_sp_trone c ON b.sp_trone_id = c.id";
		sql += " LEFT JOIN " + com.system.constant.Constant.DB_DAILY_CONFIG + ".tbl_product_2 d ON c.product_id = d.id";
		sql += " LEFT JOIN " + com.system.constant.Constant.DB_DAILY_CONFIG + ".tbl_product_1 e ON d.product_1_id = e.id";
		sql += " LEFT JOIN " + com.system.constant.Constant.DB_DAILY_CONFIG + ".tbl_operator f ON e.operator_id = f.id";
		sql += " WHERE c.status = 1 AND b.status = 1 AND a.disable = 0 AND a.cp_id = " + cpId;
		sql += " GROUP BY f.id,b.price,c.id";
		
		new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<CpProvinceModel> list = new ArrayList<CpProvinceModel>();
				
				while(rs.next())
				{
					CpProvinceModel model = new CpProvinceModel();
					
					model.setCpId(cpId);
					model.setOperatorId(rs.getInt("operator_id"));
					model.setOperatorName(StringUtil.getString(rs.getString("name_cn"), ""));
					model.setPrice(rs.getFloat("price"));
					String province = rs.getString("provinces");
					if(!StringUtil.isNullOrEmpty(province))
					{
						List<Integer> pros = new ArrayList<Integer>();
						for(String pro : province.split(","))
						{
							pros.add(StringUtil.getInteger(pro, 0));
						}
						model.setProvinces(pros);
					}
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	private void loadCpDFSFDS(int cp)
	{
		
	}
	
}
