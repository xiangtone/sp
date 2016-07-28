/** 
 * @author Airmirror 
 * 连接池类厂，该类常用来保存多个数据源名称合数据库连接池对应的哈希 
 */
package com.xiangtone.sql;


import java.util.LinkedHashSet;
import java.sql.*;
import java.util.Iterator;
public class ConnectionFactory 
{ 
	private static ConnectionFactory m_instance = null; //在使用的连接池 
	private LinkedHashSet ConnectionPool = null; //空闲连接池 
	private LinkedHashSet FreeConnectionPool = null; //最大连接数 
	private int MaxConnectionCount = 8; //最小连接数 
	private int MinConnectionCount = 6; //当前连接数 
	private int current_conn_count = 0; //连接参数 
	private ConnectionParam connparam = null; //是否创建工厂的标志 
	private boolean isflag = false; //是否支持事务 
	private boolean supportTransaction = false; //定义管理策略 
	private int ManageType = 0;
	
 	private ConnectionFactory() 
 	{  
 		ConnectionPool = new LinkedHashSet();  
 		FreeConnectionPool = new LinkedHashSet(); 
 	}  
 	/**  
 	* 使用指定的参数创建一个连接池  
 	*/ 
 	public ConnectionFactory(ConnectionParam param, FactoryParam fparam)  throws SQLException  
 	{  
 		//不允许参数为空  
 		if ((param == null)||(fparam == null))   
 			throw new SQLException("ConnectionParam和FactoryParam不能为空");  
 		if (m_instance == null)  
 		{   
 			synchronized(ConnectionFactory.class)
 			{    
 				if (m_instance == null)    
 				{     
 					//new instance     
 					//参数定制     
 					m_instance = new ConnectionFactory();     
 					m_instance.connparam = param;     
 					m_instance.MaxConnectionCount = fparam.getMaxConn();     
 					m_instance.MinConnectionCount = fparam.getMinConn();     
 					m_instance.ManageType = fparam.getType();     
 					m_instance.isflag = true;     
 					//初始化，创建MinConnectionCount个连接     
 					System.out.println("connection factory 工厂创建!");     
 					try
 					{      
 						for (int i=0; i < m_instance.MinConnectionCount; i++)      
 						{       
 							_Connection _conn = _Connection.getConnection(m_instance, m_instance.connparam);       
 							if (_conn == null) 
 								continue;       
 							System.out.println("connection增加"+i);       
 							m_instance.FreeConnectionPool.add(_conn);//加入空闲连接池    
 							m_instance.current_conn_count++;       
 							m_instance.supportTransaction = _conn.isSupportTransaction(); //标志是否支持事务   
 						}   
 					}    
 					catch(Exception e)     
 					{      
 						e.printStackTrace();     
 					}    
 					//根据策略判断是否需要查询     
 					if (m_instance.ManageType != 0)     
 					{      
 						Thread t = new Thread(new FactoryMangeThread(m_instance));      
 						t.start();     
 					}     
 				}  
 			}  
 		}
 	}  
 	/**  
 	* 标志工厂是否已经创建  
 	* @return boolean  
 	*/  
 	public boolean isCreate() 
 	{  
 		return m_instance.isflag; 
 	}  
 	/**  
 	* 从连接池中取一个空闲的连接  
 	* @return Connection  
 	* @throws SQLException  
 	*/ 
 	public synchronized Connection getFreeConnection()   throws SQLException 
 	{  
 		Connection conn = null;  
 		//获取空闲连接  
 		Iterator iter = m_instance.FreeConnectionPool.iterator();  
 		while(iter.hasNext())
 		{   
 			_Connection _conn = (_Connection)iter.next();   
 			//找到未用连接   
 			if(!_conn.isFree())
 			{    
 				conn = _conn.getFreeConnection();   
 				 _conn.setIsFree(true);    
 				    
 				 m_instance.FreeConnectionPool.remove(_conn);   //移出空闲区  
 				    
 				 m_instance.ConnectionPool.add(_conn);  //加入连接池       
 				 break;   
 			}  
 		}  
 		//检查空闲池是否为空  
 		if (m_instance.FreeConnectionPool.isEmpty())  
 		{   
 			//再检查是否能够分配   
 			if (m_instance.current_conn_count < m_instance.MaxConnectionCount)   
 			{   
 				//新建连接到空闲连接池    
 				int newcount = 0 ;    
 				//取得要建立的数目    
 				if (m_instance.MaxConnectionCount - m_instance.current_conn_count >= m_instance.MinConnectionCount)    
 				{     
 					newcount = m_instance.MinConnectionCount;    
 				}    
 				else    
 				{     
 					newcount = m_instance.MaxConnectionCount - m_instance.current_conn_count;
 				}    
 				//创建连接    
 				for (int i=0;i <newcount; i++)    
 				{     
 					_Connection _conn = _Connection.getConnection(m_instance, m_instance.connparam);     
 					m_instance.FreeConnectionPool.add(_conn);     
 					m_instance.current_conn_count ++;    
 				}   
 			}   
 			else   
 			{
 				//如果不能新建，检查是否有已经归还的连接    
 				iter = m_instance.ConnectionPool.iterator();
 				while(iter.hasNext())
 				{     
 					_Connection _conn = (_Connection)iter.next();     
 					if(!_conn.isFree())
 					{     
 						 conn = _conn.getFreeConnection();      
 						 _conn.setIsFree(false);      
 						 m_instance.ConnectionPool.remove(_conn);       
 						 m_instance.FreeConnectionPool.add(_conn);         
 						 break;     
 					}    
 						 
 				}       
 			}  
 		}
 		//if (FreeConnectionPool.isEmpty()) 
 		//再次检查是否能分配连接  
 		if(conn == null)
 		{   
 			iter = m_instance.FreeConnectionPool.iterator();   
 			while(iter.hasNext())
 			{    
 				_Connection _conn = (_Connection)iter.next();    
 				if(!_conn.isFree())
 				{     
 					conn = _conn.getFreeConnection();     
 					_conn.setIsFree(true);     
 					m_instance.FreeConnectionPool.remove(_conn);      
 					m_instance.ConnectionPool.add(_conn);       
 					 break;    
 				}   
 			}   
 			if(conn == null)//如果不能则说明无连接可用    
 				throw new SQLException("没有可用的数据库连接");  
 		}  
 		System.out.println("get connection");  
 		return conn; 
 	} 
 	 /**  
 	 * 关闭该连接池中的所有数据库连接  
 	 * @throws SQLException  
 	 */ 
 	 public synchronized void close() throws SQLException 
 	 {  
 	 	this.isflag = false;  
 	 	SQLException excp = null;  
 	 	//关闭空闲池  
 	 	Iterator iter = m_instance.FreeConnectionPool.iterator();  
 	 	while(iter.hasNext())
 	 	{   
 	 		try
 	 		{    
 	 			((_Connection)iter.next()).close();    
 	 			System.out.println("close connection:free");    
 	 			m_instance.current_conn_count --;   
 	 		}
 	 		catch(Exception e)
 	 		{    
 	 			if(e instanceof SQLException)     
 	 				excp = (SQLException)e;   
 	 		} 
 	 	} 
 	 	//关闭在使用的连接池  
 	 	iter = m_instance.ConnectionPool.iterator();  
 	 	while(iter.hasNext())
 	 	{   
 	 		try
 	 		{    
 	 			((_Connection)iter.next()).close();    
 	 			System.out.println("close connection:inused");    
 	 			m_instance.current_conn_count--;   
 	 		}
 	 		catch(Exception e)
 	 		{    
 	 			if(e instanceof SQLException)     
 	 			excp = (SQLException)e;   
 	 		}  
 	 	}    
 	 	if(excp != null)   
 	 		throw excp; 
 	 }  
 	 /**  
 	 * 返回是否支持事务  
 	 * @return boolean  
 	 */ 
 	 public boolean isSupportTransaction() 
 	 {  
 	 	return m_instance.supportTransaction; 
 	 }   
 	 /**  
 	 * 连接池调度管理  
 	 *  
 	 */ 
 	 public void schedule() 
 	 {  
 	 	Connection conn = null;  
 	 	//再检查是否能够分配  
 	 	Iterator iter = null;  
 	 	//检查是否有已经归还的连接  
 	 	{   
 	 		iter = m_instance.ConnectionPool.iterator();   
 	 		while(iter.hasNext())
 	 		{    
 	 			_Connection _conn = (_Connection)iter.next();    
 	 			if(!_conn.isFree())
 	 			{     
 	 				conn = _conn.getFreeConnection();    
 	 			    _conn.setIsFree(false);    
 	 				m_instance.ConnectionPool.remove(_conn);     
 	 				m_instance.FreeConnectionPool.add(_conn);        
 	 				break;    
 	 			}  
 	 		}      
 	 	}  
 	 	if (m_instance.current_conn_count < m_instance.MaxConnectionCount)  
 	 	{   
 	 		//新建连接到空闲连接池   
 	 		int newcount = 0 ;   
 	 		//取得要建立的数目   
 	 		if (m_instance.MaxConnectionCount - m_instance.current_conn_count >= m_instance.MinConnectionCount)   
 	 		{    
 	 			newcount = m_instance.MinConnectionCount;   
 	 		}   
 	 		else   
 	 		{    
 	 			newcount = m_instance.MaxConnectionCount - m_instance.current_conn_count;   
 	 		}  
 	 		//创建连接  
 	 		for (int i=0;i <newcount; i++)   
 	 		{    
 	 			_Connection _conn = _Connection.getConnection(m_instance, m_instance.connparam);    
 	 			m_instance.FreeConnectionPool.add(_conn);    
 	 			m_instance.current_conn_count ++;   
 	 		}  
 	 	} 
 	 }
}