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
import com.system.model.GroupModel;
import com.system.model.UserModel;
import com.system.util.StringUtil;

public class GroupDao
{
	@SuppressWarnings("unchecked")
	public List<GroupModel> loadAllGroup()
	{
		String sql = "select * from daily_config.tbl_group"+ " order by convert(name using gbk) asc ";
		
		return (List<GroupModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<GroupModel> list = new ArrayList<GroupModel>();
				 
				while(rs.next())
				{
					GroupModel model = new GroupModel();
					model.setId(rs.getInt("id"));
					model.setName(StringUtil.getString(rs.getString("name"),""));
					model.setRemark(StringUtil.getString(rs.getString("remark"),""));
					list.add(model);
				}
				
				return list;
			}
		});
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<GroupModel> loadRightGroupByUserId(int userId)
	{
		
		String sql = " SELECT b.`group_list` FROM daily_config.`tbl_group_user` a";
		sql += " LEFT JOIN daily_config.`tbl_group_group` b ON a.`group_id` = b.`group_id`";
		sql += " WHERE user_id = " + userId;
		
		JdbcControl control = new JdbcControl();
		
		final List<Integer> list = new ArrayList<Integer>();
		
		control.query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				String groupList = "";
				while(rs.next())
				{
					groupList = rs.getString("group_list");
					if(!StringUtil.isNullOrEmpty(groupList))
					for(String str : groupList.split(","))
					{
						int groupId = StringUtil.getInteger(str, 0);
						if(groupId>0)
						{
							if(!list.contains(groupId))
							{
								list.add(groupId);
							}
						}
					}
				}
				return null;
			}
		});
		
		if(list.isEmpty())
			return new ArrayList<GroupModel>();
		
		String groups = "";
		
		for(Integer groupId : list)
		{
			groups +=  groupId + ",";
		}
		
		groups = groups.substring(0,groups.length()-1);
		
		String sql2 = "select * from daily_config.tbl_group where id in ("+ groups +") order by convert(name using gbk) asc ";
		
		return (List<GroupModel>)new JdbcControl().query(sql2, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<GroupModel> list = new ArrayList<GroupModel>();
				 
				while(rs.next())
				{
					GroupModel model = new GroupModel();
					model.setId(rs.getInt("id"));
					model.setName(StringUtil.getString(rs.getString("name"),""));
					model.setRemark(StringUtil.getString(rs.getString("remark"),""));
					list.add(model);
				}
				
				return list;
			}
		});
		
	}
	
	public Map<String, Object> loadGroup(int pageIndex)
	{
		String sql = "select " + Constant.CONSTANT_REPLACE_STRING + " from daily_config.tbl_group";
		Map<String, Object> map = new  HashMap<String, Object>();
		map.put("rows", new JdbcControl().query(sql.replace(Constant.CONSTANT_REPLACE_STRING, " count(*) "), new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
					return rs.getInt(1);
				return 0;
			}
		}));
		
		String limit = " limit "  + Constant.PAGE_SIZE*(pageIndex-1) + "," + Constant.PAGE_SIZE;
		
		map.put("list", new JdbcControl().query(sql.replace(Constant.CONSTANT_REPLACE_STRING, " * ") + limit, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<GroupModel> list = new ArrayList<GroupModel>();
				 
				while(rs.next())
				{
					GroupModel model = new GroupModel();
					model.setId(rs.getInt("id"));
					model.setName(StringUtil.getString(rs.getString("name"),""));
					model.setRemark(StringUtil.getString(rs.getString("remark"),""));
					list.add(model);
				}
				
				return list;
			}
		}));
		
		return map;
	}
	
	public Map<String, Object> loadGroup(int pageIndex,String name)
	{
		String sql = "select " + Constant.CONSTANT_REPLACE_STRING + " from daily_config.tbl_group WHERE 1=1 ";
		
		if (!StringUtil.isNullOrEmpty(name)) {
			sql += " AND name LIKE '%"+name+"%' ";
		}
		
		Map<String, Object> map = new  HashMap<String, Object>();
		map.put("rows", new JdbcControl().query(sql.replace(Constant.CONSTANT_REPLACE_STRING, " count(*) "), new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
					return rs.getInt(1);
				return 0;
			}
		}));
		
		String limit = " limit "  + Constant.PAGE_SIZE*(pageIndex-1) + "," + Constant.PAGE_SIZE;
		
		map.put("list", new JdbcControl().query(sql.replace(Constant.CONSTANT_REPLACE_STRING, " * ") + limit, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<GroupModel> list = new ArrayList<GroupModel>();
				 
				while(rs.next())
				{
					GroupModel model = new GroupModel();
					model.setId(rs.getInt("id"));
					model.setName(StringUtil.getString(rs.getString("name"),""));
					model.setRemark(StringUtil.getString(rs.getString("remark"),""));
					list.add(model);
				}
				
				return list;
			}
		}));
		
		return map;
	}
	
	public GroupModel loadGroupById(int id)
	{
		String sql = "select * from daily_config.tbl_group where id =" + id;
		return (GroupModel)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				if(rs.next())
				{
					GroupModel model = new GroupModel();
					model.setId(rs.getInt("id"));
					model.setName(StringUtil.getString(rs.getString("name"),""));
					model.setRemark(StringUtil.getString(rs.getString("remark"),""));
					return model;
				}
				return null;
			}
		});
	}
	
	public boolean updateGroup(GroupModel model)
	{
		String sql = "update daily_config.tbl_group set name = '" + model.getName() + "',remark = '" + model.getRemark() + "' where id =" + model.getId();
		
		return new JdbcControl().execute(sql);
	}
	
	public boolean addGroup(GroupModel model)
	{
		String sql = "insert into daily_config.tbl_group (name,remark) value('" + model.getName() + "','" + model.getRemark() + "')";
		return new JdbcControl().execute(sql);
	}
	
	//暂时还没使用，等以后再来看看这个系统
	@SuppressWarnings("unchecked")
	public Map<Integer,Integer> loadUserByGroupId(int id)
	{
		String sql = "select user_id,group_id from daily_config.tbl_group_user where group_id =" + id;
		return (Map<Integer,Integer>)new JdbcControl().query(sql, new QueryCallBack()
		{
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				Map<Integer,Integer> map = new HashMap<Integer, Integer>();
				
				while(rs.next())
				{
					map.put(rs.getInt("user_id"), rs.getInt("group_id"));
				}
				
				
				return map;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> loadRightByGroupId(int id)
	{
		String sql = "SELECT menu_2_id FROM daily_config.`tbl_group_right` WHERE group_id = " + id;
		return (List<Integer>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<Integer> list = new ArrayList<Integer>();
				while(rs.next())
				{
					list.add(rs.getInt(1));
				}
				return list;
			}
		});
	}
	
	public void delGroupRightById(int id)
	{
		String sql = "delete from daily_config.tbl_group_right where group_id = " + id;
		new JdbcControl().execute(sql);
	}
	
	public void addGroupRight(int groupId,List<Integer> list)
	{
		String sql = "insert into daily_config.tbl_group_right(group_id,menu_2_id) values ";
		
		String values = "";
		
		for(int i=0; i <list.size(); i++)
		{
			values += "("+ groupId +","+ list.get(i) +"),";
		}
		
		if(!StringUtil.isNullOrEmpty(values))
		{
			values = values.substring(0,values.length()-1);
			values += ";";
			new JdbcControl().execute(sql + values);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UserModel> loadGroupUsersById(int id)
	{
		String sql = "SELECT a.id,a.group_id,a.user_id,b.name,b.remark,c.nick_name "
				+ "	FROM daily_config.tbl_group_user a "
				+ "	LEFT JOIN daily_config.tbl_group b ON a.group_id=b.id "
				+ " LEFT JOIN daily_config.tbl_user c ON a.user_id=c.id where b.id="+id 
				+ " order by convert(c.nick_name using gbk) asc ";
		
		return (List<UserModel>)new JdbcControl().query(sql, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<UserModel> list = new ArrayList<UserModel>();
				 
				while(rs.next())
				{
					UserModel model = new UserModel();
					model.setId(rs.getInt("user_id"));
					model.setNickName(StringUtil.getString(rs.getString("nick_name"),""));
					list.add(model);
				}
				
				return list;
			}
		});
		
	}
	public void delGroupUserById(int id)
	{
		String sql = "delete from daily_config.tbl_group_user where group_id = " + id;
		new JdbcControl().execute(sql);
	}
	
	public void addGroupUser(int groupId,List<Integer> list)
	{
		String sql = "insert into daily_config.tbl_group_user(group_id,user_id) values ";
		
		String values = "";
		
		for(int i=0; i <list.size(); i++)
		{
			values += "("+ groupId +","+ list.get(i) +"),";
		}
		
		if(!StringUtil.isNullOrEmpty(values))
		{
			values = values.substring(0,values.length()-1);
			values += ";";
			new JdbcControl().execute(sql + values);
		}
	}
	
}
