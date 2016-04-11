package com.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.constant.Constant;
import com.system.database.JdbcControl;
import com.system.database.QueryCallBack;
import com.system.model.CpModel;
import com.system.util.StringUtil;

public class CpDao
{
	@SuppressWarnings("unchecked")
	public List<CpModel> loadCp()
	{
		String sql = "select * from daily_config.tbl_cp order by convert(short_name using gbk) asc";
		
		return (List<CpModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<CpModel> list = new ArrayList<CpModel>();
				
				while(rs.next())
				{
					CpModel model = new CpModel();
					
					model.setId(rs.getInt("id"));
					model.setUserId(rs.getInt("user_id"));
					model.setFullName(StringUtil.getString(rs.getString("full_name"), ""));
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setContactPerson(StringUtil.getString(rs.getString("contract_person"), ""));
					model.setCommerceUserId(rs.getInt("commerce_user_id"));
					model.setSynFlag(rs.getInt("syn_flag"));
					model.setDefaultHoldPercent(rs.getInt("default_hold_percent"));
					model.setAddress(StringUtil.getString(rs.getString("address"), ""));
					model.setContractStartDate(StringUtil.getString(rs.getString("contract_start_date"), ""));
					model.setContractEndDate(StringUtil.getString(rs.getString("contract_end_date"), ""));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<CpModel> loadCpQiBa()
	{
		String sql = "select * from daily_config.tbl_cp order by id asc";
		
		return (List<CpModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<CpModel> list = new ArrayList<CpModel>();
				
				while(rs.next())
				{
					CpModel model = new CpModel();
					
					model.setId(rs.getInt("id"));
					model.setUserId(rs.getInt("user_id"));
					model.setFullName(StringUtil.getString(rs.getString("full_name"), ""));
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setContactPerson(StringUtil.getString(rs.getString("contract_person"), ""));
					model.setCommerceUserId(rs.getInt("commerce_user_id"));
					model.setSynFlag(rs.getInt("syn_flag"));
					model.setDefaultHoldPercent(rs.getInt("default_hold_percent"));
					model.setAddress(StringUtil.getString(rs.getString("address"), ""));
					model.setContractStartDate(StringUtil.getString(rs.getString("contract_start_date"), ""));
					model.setContractEndDate(StringUtil.getString(rs.getString("contract_end_date"), ""));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	public Map<String, Object> loadCp(int pageIndex)
	{
		String sql = "select " + Constant.CONSTANT_REPLACE_STRING + " from daily_config.tbl_cp order by convert(short_name using gbk) asc";
		
		String limit = " limit "  + Constant.PAGE_SIZE*(pageIndex-1) + "," + Constant.PAGE_SIZE;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		JdbcControl control = new JdbcControl();
		map.put("rows",control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, "count(*)"), new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
					return rs.getInt(1);
				
				return 0;
			}
		}));
		
		map.put("list", control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, " * ") + limit, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<CpModel> list = new ArrayList<CpModel>();
				while(rs.next())
				{
					CpModel model = new CpModel();
					
					model.setId(rs.getInt("id"));
					
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setFullName(StringUtil.getString(rs.getString("full_name"), ""));
					model.setContactPerson(StringUtil.getString(rs.getString("contract_person"), ""));
					model.setQq(StringUtil.getString(rs.getString("qq"), ""));
					model.setPhone(StringUtil.getString(rs.getString("phone"), ""));
					model.setMail(StringUtil.getString(rs.getString("mail"), ""));
					model.setAddress(StringUtil.getString(rs.getString("address"), ""));
					model.setContractStartDate(StringUtil.getString(rs.getString("contract_start_date"), ""));
					model.setContractEndDate(StringUtil.getString(rs.getString("contract_end_date"), ""));
					
					list.add(model);
				}
				return list;
			}
		}));
		
		return map;
	}
	
	public Map<String, Object> loadCp(int pageIndex,String fullName,String shortName)
	{
		String sql = "select " + Constant.CONSTANT_REPLACE_STRING + " from daily_config.tbl_cp a left join daily_config.tbl_user b on a.user_id = b.id where 1=1";
		
		String limit = " limit "  + Constant.PAGE_SIZE*(pageIndex-1) + "," + Constant.PAGE_SIZE;
		
		if(!StringUtil.isNullOrEmpty(fullName))
		{
			sql += " AND a.full_name LIKE '%"+fullName+"%' ";
		}
		
		if(!StringUtil.isNullOrEmpty(shortName))
		{
			sql += " AND a.short_name LIKE '%"+shortName+"%' ";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		sql += " order by convert(a.short_name using gbk) asc ";
		
		JdbcControl control = new JdbcControl();
		map.put("rows",control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, "count(*)"), new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
					return rs.getInt(1);
				
				return 0;
			}
		}));
		
		map.put("list", control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, " a.*,b.name,b.nick_name ") + limit, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<CpModel> list = new ArrayList<CpModel>();
				while(rs.next())
				{
					CpModel model = new CpModel();
					
					model.setId(rs.getInt("id"));
					
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setFullName(StringUtil.getString(rs.getString("full_name"), ""));
					model.setContactPerson(StringUtil.getString(rs.getString("contract_person"), ""));
					model.setQq(StringUtil.getString(rs.getString("qq"), ""));
					model.setPhone(StringUtil.getString(rs.getString("phone"), ""));
					model.setMail(StringUtil.getString(rs.getString("mail"), ""));
					model.setAddress(StringUtil.getString(rs.getString("address"), ""));
					model.setContractStartDate(StringUtil.getString(rs.getString("contract_start_date"), ""));
					model.setContractEndDate(StringUtil.getString(rs.getString("contract_end_date"), ""));
					model.setUserName(StringUtil.getString(rs.getString("nick_name"),""));
					
					list.add(model);
				}
				return list;
			}
		}));
		
		return map;
	}
	
	public CpModel loadCpById(int id)
	{
		String sql = "select * from daily_config.tbl_cp where id = " + id;
		return (CpModel)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					CpModel model = new CpModel();
					model.setId(rs.getInt("id"));					
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setFullName(StringUtil.getString(rs.getString("full_name"), ""));
					model.setContactPerson(StringUtil.getString(rs.getString("contract_person"), ""));
					model.setQq(StringUtil.getString(rs.getString("qq"), ""));
					model.setPhone(StringUtil.getString(rs.getString("phone"), ""));
					model.setMail(StringUtil.getString(rs.getString("mail"), ""));
					model.setAddress(StringUtil.getString(rs.getString("address"), ""));
					model.setUserId(rs.getInt("user_id"));
					model.setContractStartDate(StringUtil.getString(rs.getString("contract_start_date"), ""));
					model.setContractEndDate(StringUtil.getString(rs.getString("contract_end_date"), ""));
					return model;
				}
				
				return null;
			}
		});
	}
	
	public boolean addCp(CpModel model)
	{
		String sql = "insert into daily_config.tbl_cp(full_name,short_name,contract_person,qq,mail,phone,address,contract_start_date,contract_end_date) "
				+ "value('" + model.getFullName() + "','" + model.getShortName()
				+ "','" + model.getContactPerson() + "','" + model.getQq()
				+ "','" + model.getMail() + "','" + model.getPhone() + "','"
				+ model.getAddress() + "','" + model.getContractStartDate()
				+ "','" + model.getContractEndDate() + "')";
		return new JdbcControl().execute(sql);
	}

	public boolean updateCp(CpModel model)
	{
		String sql = "update daily_config.tbl_cp set full_name = '"
				+ model.getFullName() + "',short_name = '"
				+ model.getShortName() + "',contract_person='"
				+ model.getContactPerson() + "',qq='" + model.getQq()
				+ "',mail='" + model.getMail() + "',phone='" + model.getPhone()
				+ "',address='" + model.getAddress() + "',contract_start_date='"
				+ model.getContractStartDate() + "',contract_end_date='"
				+ model.getContractEndDate() + "' where id =" + model.getId();
		return new JdbcControl().execute(sql);
	}
	
	public boolean updateCpAccount(int cpId,int userId)
	{
		String sql = "update daily_config.tbl_cp set user_id = " + userId + " where id = " + cpId;
		return new JdbcControl().execute(sql);
	}
	
}
