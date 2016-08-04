package com.system.sdk.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.system.database.JdbcGameControl;
import com.system.database.QueryCallBack;
import com.system.sdk.model.SdkSpTroneModel;
import com.system.util.StringUtil;

public class SdkSpTroneDao {
	@SuppressWarnings("unchecked")
	public List<SdkSpTroneModel>loadSdkSpTrone(){
		String sql="SELECT * FROM daily_config.tbl_sdk_sp_trone ORDER BY id DESC";
		return (List<SdkSpTroneModel>)new JdbcGameControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SdkSpTroneModel> list = new ArrayList<SdkSpTroneModel>();
				while(rs.next())
				{
					SdkSpTroneModel model = new SdkSpTroneModel();
					
					model.setId(rs.getInt("id"));
					model.setSpId(rs.getInt("sp_id"));
					model.setSpTroneId(rs.getInt("sp_trone_id"));
					model.setName(StringUtil.getString(rs.getString("name"), ""));
					model.setOperatorId(rs.getInt("operator_id"));
					model.setCteateDate(StringUtil.getString(rs.getString("create_date"), ""));
					list.add(model);
				}
				return list;
			}
		});
		
		}

}
