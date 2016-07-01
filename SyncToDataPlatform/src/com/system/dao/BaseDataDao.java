package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.database.HtJdbcControl;
import com.system.database.IJdbcControl;
import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;
import com.system.database.TlJdbcControl;
import com.system.database.YdJdbcControl;
import com.system.model.SpModel;
import com.system.util.StringUtil;

public class BaseDataDao
{
	@SuppressWarnings("unchecked")
	public Map<Integer, SpModel> loadOriSpData(int coId)
	{
		IJdbcControl control = getCoControl(coId);
		
		String sql = "SELECT id,short_name,full_name,STATUS FROM daily_config.tbl_sp";
		
		if(control!=null)
		{
			return (Map<Integer, SpModel>)control.query(sql, new QueryCallBack()
			{
				@Override
				public Object onCallBack(ResultSet rs) throws SQLException
				{
					Map<Integer, SpModel> map = new HashMap<Integer, SpModel>();
					
					while(rs.next())
					{
						SpModel model = new SpModel();
						model.setSpId(rs.getInt("id"));
						model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
						model.setFullName(StringUtil.getString(rs.getString("full_name"), ""));
						model.setStatus(rs.getInt("status"));
						map.put(model.getSpId(), model);
					}
					
					return map;
				}
			});
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, SpModel> loadDescSpData(int coId)
	{
		String sql = "SELECT id,short_name,full_name,STATUS FROM comsum_config.tbl_sp where co_id = " + coId;
		
		return (Map<Integer, SpModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				Map<Integer, SpModel> map = new HashMap<Integer, SpModel>();
				
				while(rs.next())
				{
					SpModel model = new SpModel();
					model.setSpId(rs.getInt("sp_id"));
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setFullName(StringUtil.getString(rs.getString("full_name"), ""));
					model.setStatus(rs.getInt("status"));
					map.put(model.getSpId(), model);
				}
				
				return map;
			}
		});
	}
	
	public void addSpData(int coId,List<SpModel> list)
	{
		if(list==null || list.isEmpty())
			return;
			
		StringBuffer sb = new StringBuffer(256);
		
		sb.append("INSERT INTO comsum_config.tbl_sp(co_id,sp_id,short_name,full_name,STATUS) VALUES ");
		
		for(SpModel model : list)
		{
			sb.append("(" + coId + "," + model.getSpId() + ",'" + model.getShortName() + "','" + model.getFullName() + "'," + model.getStatus() + "),");
		}
		
		new JdbcControl().execute(sb.toString().substring(0,sb.length()-1));
	}
	
	public void updateSpData(int coId,List<SpModel> list)
	{
		if(list==null || list.isEmpty())
			return;
		
		JdbcControl control = new JdbcControl();
		
		for(SpModel model : list)
		{
			
		}
	}
	
	
	private IJdbcControl getCoControl(int coId)
	{
		IJdbcControl control = null;
		
		switch(coId)
		{
			case 1:
				control = new HtJdbcControl();
				break;
				
			case 2:
				control = new TlJdbcControl();
				break;
				
			case 3:
				control = new YdJdbcControl();
				break;			
				
			default:
				break;
		}
		
		return control;
	}
}
