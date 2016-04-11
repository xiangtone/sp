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
import com.system.model.SpModel;
import com.system.util.StringUtil;

public class SpDao
{
	@SuppressWarnings("unchecked")
	public List<SpModel> loadSp()
	{
		String sql = "select * from daily_config.tbl_sp order by convert(short_name using gbk) asc";
		return (List<SpModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SpModel> list = new ArrayList<SpModel>();
				
				while(rs.next())
				{
					SpModel model = new SpModel();
					
					model.setId(rs.getInt("id"));
					model.setUserId(rs.getInt("user_id"));
					model.setFullName(StringUtil.getString(rs.getString("full_name"), ""));
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setContactPerson(StringUtil.getString(rs.getString("contract_person"), ""));
					model.setCommerceUserId(rs.getInt("commerce_user_id"));
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
	public List<SpModel> loadSpQiBa()
	{
		String sql = "select * from daily_config.tbl_sp order by id asc";
		return (List<SpModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<SpModel> list = new ArrayList<SpModel>();
				
				while(rs.next())
				{
					SpModel model = new SpModel();
					
					model.setId(rs.getInt("id"));
					model.setUserId(rs.getInt("user_id"));
					model.setFullName(StringUtil.getString(rs.getString("full_name"), ""));
					model.setShortName(StringUtil.getString(rs.getString("short_name"), ""));
					model.setContactPerson(StringUtil.getString(rs.getString("contract_person"), ""));
					model.setCommerceUserId(rs.getInt("commerce_user_id"));
					model.setAddress(StringUtil.getString(rs.getString("address"), ""));
					model.setContractStartDate(StringUtil.getString(rs.getString("contract_start_date"), ""));
					model.setContractEndDate(StringUtil.getString(rs.getString("contract_end_date"), ""));
					
					list.add(model);
				}
				
				return list;
			}
		});
	}
	
	public Map<String, Object> loadSp(int pageIndex)
	{
		String sql = "select " + Constant.CONSTANT_REPLACE_STRING + " from daily_config.tbl_sp order by convert(short_name using gbk) asc";
		
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
				List<SpModel> list = new ArrayList<SpModel>();
				while(rs.next())
				{
					SpModel model = new SpModel();
					
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
	
	public Map<String, Object> loadSp(int pageIndex,String fullName,String shortName,int commerceUserId)
	{
		String sql = "select " + Constant.CONSTANT_REPLACE_STRING + " from daily_config.tbl_sp a left join daily_config.tbl_user b on a.commerce_user_id = b.id where 1=1 ";
		
		String limit = " limit "  + Constant.PAGE_SIZE*(pageIndex-1) + "," + Constant.PAGE_SIZE;
		
		if(!StringUtil.isNullOrEmpty(fullName))
		{
			sql += " AND full_name LIKE '%"+fullName+"%' ";
		}
		
		if(!StringUtil.isNullOrEmpty(shortName))
		{
			sql += " AND short_name LIKE '%"+shortName+"%' ";
		}
		
		if(commerceUserId>0)
		{
			sql += " AND a.commerce_user_id = " + commerceUserId;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		sql += " order by convert(short_name using gbk) asc ";
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
				List<SpModel> list = new ArrayList<SpModel>();
				while(rs.next())
				{
					SpModel model = new SpModel();
					
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
					model.setCommerceUserId(rs.getInt("commerce_user_id"));
					model.setCommerceUserName(StringUtil.getString(rs.getString("nick_name"), ""));
					
					list.add(model);
				}
				return list;
			}
		}));
		
		return map;
	}
	
	public SpModel loadSpById(int id)
	{
		String sql = "select * from daily_config.tbl_sp where id = " + id;
		return (SpModel)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					SpModel model = new SpModel();
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
					model.setCommerceUserId(rs.getInt("commerce_user_id"));
					return model;
				}
				
				return null;
			}
		});
	}
	
	public boolean addSp(SpModel model)
	{
		String sql = "insert into daily_config.tbl_sp(full_name,short_name,contract_person,qq,mail,phone,address,contract_start_date,contract_end_date,commerce_user_id) "
				+ "value('" + model.getFullName() + "','" + model.getShortName()
				+ "','" + model.getContactPerson() + "','" + model.getQq()
				+ "','" + model.getMail() + "','" + model.getPhone() + "','"
				+ model.getAddress() + "','" + model.getContractStartDate()
				+ "','" + model.getContractEndDate() + "',"+ model.getCommerceUserId() +")";
		return new JdbcControl().execute(sql);
	}

	public boolean updateSp(SpModel model)
	{
		String sql = "update daily_config.tbl_sp set full_name = '"
				+ model.getFullName() + "',short_name = '"
				+ model.getShortName() + "',contract_person='"
				+ model.getContactPerson() + "',qq='" + model.getQq()
				+ "',mail='" + model.getMail() + "',phone='" + model.getPhone()
				+ "',address='" + model.getAddress() + "',contract_start_date='"
				+ model.getContractStartDate() + "',contract_end_date='"
				+ model.getContractEndDate() + "',commerce_user_id=" + model.getCommerceUserId() + " where id =" + model.getId();
		return new JdbcControl().execute(sql);
	}
	
}
