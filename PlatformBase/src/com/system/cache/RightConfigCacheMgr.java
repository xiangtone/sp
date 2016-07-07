package com.system.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.system.dao.Menu1Dao;
import com.system.dao.Menu2Dao;
import com.system.dao.MenuHeadDao;
import com.system.dao.PublicUrlDao;
import com.system.dao.RightAccessDao;
import com.system.dao.UserDao;
import com.system.model.Menu1Model;
import com.system.model.Menu2Model;
import com.system.model.MenuHeadModel;
import com.system.model.PublicUrlModel;
import com.system.model.UserModel;
import com.system.model.UserRightModel;

public class RightConfigCacheMgr
{
	private static Logger logger = Logger.getLogger(RightConfigCacheMgr.class);
	
	//公共URL
	public static List<String> pubUrlCache = new ArrayList<String>();
	
	//所有菜单下要用到的ACTION JSP
	public static List<String> pubActionUrlCache = new ArrayList<String>();
	
	//所有系统用户
	public static List<UserModel> userListCache = new ArrayList<UserModel>();
	
	//一级菜单 
	public static List<MenuHeadModel> menuHeadListCache = new ArrayList<MenuHeadModel>();
	
	//二级菜单
	public static List<Menu1Model> menu1ListCache = new ArrayList<Menu1Model>();
	
	//三级菜单
	public static List<Menu2Model> menu2ListCache = new ArrayList<Menu2Model>();
	
	//用户权限
	public static List<UserRightModel> userRightCache = new ArrayList<UserRightModel>();
	
	static
	{
		refreshAllCache();
	}
	
	public static void init(){}
	
	public static void refreshAllCache()
	{
		refreshPubUrlCache();
		refreshUserListCache();
		refreshMenuHeadListCache();
		refreshMenu1ListCache();
		refreshMenu2ListCache();
		refreshUserRightCache();
	}
	
	private static void refreshPubUrlCache()
	{
		pubUrlCache = new ArrayList<String>();
		PublicUrlDao dao = new PublicUrlDao();
		for(PublicUrlModel model : dao.getPublicUrlList())
		{
			pubUrlCache.add(model.getUrl());
		}
		pubActionUrlCache = dao.loadPublicActionUrl();
		logger.info("refreshPubUrlCache finish");
	}
	
	
	private static void refreshUserListCache()
	{
		userListCache  = new UserDao().loadActityUserList();
		logger.info("refreshUserListCache finish");
	}
	
	private static void refreshMenuHeadListCache()
	{
		menuHeadListCache = new MenuHeadDao().loadMenuHeadList();
		logger.info("refreshMenuHeadListCache finish");
	}
	
	private static void refreshMenu1ListCache()
	{
		menu1ListCache = new Menu1Dao().loadMenu1List();
		logger.info("refreshMenu1ListCache finish");
	}
	
	private static void refreshMenu2ListCache()
	{
		menu2ListCache = new Menu2Dao().loadMenu2List();
		logger.info("refreshMenu2ListCache finish");
	}
	
	private static void refreshUserRightCache()
	{
		userRightCache = new RightAccessDao().loadUserRight();
		logger.info("refreshUserRightCache finish");
	}
}
