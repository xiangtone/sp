package com.system.sdk.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.system.database.JdbcGameControl;
import com.system.database.QueryCallBack;
import com.system.sdk.model.SdkSpModel;
import com.system.util.StringUtil;

public class SdkSpDao {
	@SuppressWarnings("unchecked")
	public List<SdkSpModel>loadSdkSp(){
		String sql="SELECT * FROM daily_config.tbl_sdk_sp sp ORDER BY sp.int DESC";
		return (List<SdkSpModel>)new JdbcGameControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SdkSpModel> list = new ArrayList<SdkSpModel>();
				while(rs.next())
				{
					SdkSpModel model = new SdkSpModel();
					
					model.setId(rs.getInt("int"));
					model.setFullName(StringUtil.getString(rs.getString("full_name"), ""));
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setRemark(StringUtil.getString(rs.getString("remark"), ""));
					model.setCreateDate(StringUtil.getString(rs.getString("create_date"), ""));
					list.add(model);
				}
				return list;
			}
		});

	
		
	}

}
