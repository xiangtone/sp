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
import com.system.model.CommRightModel;
import com.system.model.SpModel;
import com.system.util.SqlUtil;
import com.system.util.StringUtil;

public class CommRightDao {
	public Map<String, Object> loadCommRight(int pageIndex,String keyWord,int type)
	{
		String sql = "select " + Constant.CONSTANT_REPLACE_STRING + " from daily_config.tbl_ds_user_right ur left join daily_config.tbl_user u on ur.user_id=u.id  where 1=1  ";
		
		String limit = " limit "  + Constant.PAGE_SIZE*(pageIndex-1) + "," + Constant.PAGE_SIZE;
		
		if(!StringUtil.isNullOrEmpty(keyWord))
		{
			sql += " AND (u.nick_name LIKE '%" + keyWord + "%')";
		}
		if(type>=0){
			sql +=" AND ur.type="+type;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		sql += " order by ur.id desc ";
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
		
		map.put("list", control.query(sql.replace(Constant.CONSTANT_REPLACE_STRING, " ur.*,u.name,u.nick_name ") + limit, new QueryCallBack()
		{
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException
			{
				List<CommRightModel> list = new ArrayList<CommRightModel>();
				while(rs.next())
				{
					CommRightModel model = new CommRightModel();
					
					model.setId(rs.getInt("id"));
					model.setType(rs.getInt("type"));
					model.setRightList(StringUtil.getString(rs.getString("right_list"), ""));
					model.setRemark(StringUtil.getString(rs.getString("remark"), ""));
					model.setUserName(StringUtil.getString(rs.getString("nick_name"),""));
					
					list.add(model);
				}
				return list;
			}
		}));
		
		return map;
	}
	public boolean addCommRight(CommRightModel model)
	{
		String sql = "insert into daily_config.tbl_ds_user_right(type,user_id,right_list,remark) "
				+ "value(" + model.getType() + "," + model.getUserId()
				+ ",'" + SqlUtil.sqlEncode(model.getRightList()) + "','" + SqlUtil.sqlEncode(model.getRemark())+"' )";
		return new JdbcControl().execute(sql);
	}

	public boolean updateCommRight(CommRightModel model)
	{
		String sql = "update daily_config.tbl_ds_user_right set type = "
				+ model.getType() + ",user_id = "
				+ model.getUserId() + ",right_list='"
				+ SqlUtil.sqlEncode(model.getRightList()) + "',remark='" + SqlUtil.sqlEncode(model.getRemark())
				+ "'" + " where id =" + model.getId();
		return new JdbcControl().execute(sql);
	}
	public CommRightModel loadCommRightById(int id){
		String sql="select ur.*,u.name,u.nick_name from daily_config.tbl_ds_user_right ur left join daily_config.tbl_user u on ur.user_id=u.id  where ur.id="+id;
		JdbcControl control = new JdbcControl();
		return (CommRightModel) control.query(sql, new QueryCallBack() {
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				CommRightModel model =new CommRightModel();
				while(rs.next())
				{					
					model.setId(rs.getInt("id"));
					model.setType(rs.getInt("type"));
					model.setUserId(rs.getInt("user_id"));
					model.setRightList(StringUtil.getString(rs.getString("right_list"), ""));
					model.setRemark(StringUtil.getString(rs.getString("remark"), ""));
					model.setUserName(StringUtil.getString(rs.getString("nick_name"),""));
					
					return model;
				}
				return null;
			}
		});
	}
	
	public boolean deleteCommRight(int id){
		String sql="delete from daily_config.tbl_ds_user_right where id="+id;
		return new JdbcControl().execute(sql);
	}
}
