/** 
* Created on 2003-5-13 
* 
* To change the template for this generated file go to 
* Window>Preferences>Java>Code Generation>Code and Comments 
*/
package com.xiangtone.sql;
/** 
* @author youyongming 
* 
*/
//连接池工厂参数
public class FactoryParam 
{ 
	 
	private int MaxConnectionCount = 4; //最大连接数
	private int MinConnectionCount = 2; //最小连接数 
	//回收策略 
	private int ManageType = 0; 
	public FactoryParam() 
	{ 
	}  
	/**  
	* 构造连接池工厂参数的对象  
	* @param max 最大连接数  
	* @param min 最小连接数  
	* @param type 管理策略  
	*/ 
	public FactoryParam(int max, int min, int type) 
	{  
		this.ManageType = type;  
		this.MaxConnectionCount = max;  
		this.MinConnectionCount = min; 
	} 
	/**  
	* 设置最大的连接数  
	* @param value  
	*/ 
	public void setMaxConn(int value) 
	{  
		this.MaxConnectionCount = value; 
	} 
	/**  
	* 获取最大连接数  
	* @return  */ 
	public int getMaxConn() 
	{  
		return this.MaxConnectionCount; 
	} 
	/**  
	* 设置最小连接数  
	* @param value  
	
	*/ 
	public void setMinConn(int value) 
	{  
		this.MinConnectionCount = value; 
	} 
	/**  
	* 获取最小连接数  
	* @return  
	*/ 
	public int getMinConn() 
	{  
		return this.MinConnectionCount; 
	} public int getType() 
	{ 
		return this.ManageType; 
	}
}