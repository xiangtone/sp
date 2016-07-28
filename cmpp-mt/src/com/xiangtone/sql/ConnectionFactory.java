/** 
 * @author Airmirror 
 * ���ӳ��೧�����ೣ��������������Դ���ƺ����ݿ����ӳض�Ӧ�Ĺ�ϣ 
 */
package com.xiangtone.sql;


import java.util.LinkedHashSet;
import java.sql.*;
import java.util.Iterator;
public class ConnectionFactory 
{ 
	private static ConnectionFactory m_instance = null; //��ʹ�õ����ӳ� 
	private LinkedHashSet ConnectionPool = null; //�������ӳ� 
	private LinkedHashSet FreeConnectionPool = null; //��������� 
	private int MaxConnectionCount = 8; //��С������ 
	private int MinConnectionCount = 6; //��ǰ������ 
	private int current_conn_count = 0; //���Ӳ��� 
	private ConnectionParam connparam = null; //�Ƿ񴴽������ı�־ 
	private boolean isflag = false; //�Ƿ�֧������ 
	private boolean supportTransaction = false; //���������� 
	private int ManageType = 0;
	
 	private ConnectionFactory() 
 	{  
 		ConnectionPool = new LinkedHashSet();  
 		FreeConnectionPool = new LinkedHashSet(); 
 	}  
 	/**  
 	* ʹ��ָ���Ĳ�������һ�����ӳ�  
 	*/ 
 	public ConnectionFactory(ConnectionParam param, FactoryParam fparam)  throws SQLException  
 	{  
 		//���������Ϊ��  
 		if ((param == null)||(fparam == null))   
 			throw new SQLException("ConnectionParam��FactoryParam����Ϊ��");  
 		if (m_instance == null)  
 		{   
 			synchronized(ConnectionFactory.class)
 			{    
 				if (m_instance == null)    
 				{     
 					//new instance     
 					//��������     
 					m_instance = new ConnectionFactory();     
 					m_instance.connparam = param;     
 					m_instance.MaxConnectionCount = fparam.getMaxConn();     
 					m_instance.MinConnectionCount = fparam.getMinConn();     
 					m_instance.ManageType = fparam.getType();     
 					m_instance.isflag = true;     
 					//��ʼ��������MinConnectionCount������     
 					System.out.println("connection factory ��������!");     
 					try
 					{      
 						for (int i=0; i < m_instance.MinConnectionCount; i++)      
 						{       
 							_Connection _conn = _Connection.getConnection(m_instance, m_instance.connparam);       
 							if (_conn == null) 
 								continue;       
 							System.out.println("connection����"+i);       
 							m_instance.FreeConnectionPool.add(_conn);//����������ӳ�    
 							m_instance.current_conn_count++;       
 							m_instance.supportTransaction = _conn.isSupportTransaction(); //��־�Ƿ�֧������   
 						}   
 					}    
 					catch(Exception e)     
 					{      
 						e.printStackTrace();     
 					}    
 					//���ݲ����ж��Ƿ���Ҫ��ѯ     
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
 	* ��־�����Ƿ��Ѿ�����  
 	* @return boolean  
 	*/  
 	public boolean isCreate() 
 	{  
 		return m_instance.isflag; 
 	}  
 	/**  
 	* �����ӳ���ȡһ�����е�����  
 	* @return Connection  
 	* @throws SQLException  
 	*/ 
 	public synchronized Connection getFreeConnection()   throws SQLException 
 	{  
 		Connection conn = null;  
 		//��ȡ��������  
 		Iterator iter = m_instance.FreeConnectionPool.iterator();  
 		while(iter.hasNext())
 		{   
 			_Connection _conn = (_Connection)iter.next();   
 			//�ҵ�δ������   
 			if(!_conn.isFree())
 			{    
 				conn = _conn.getFreeConnection();   
 				 _conn.setIsFree(true);    
 				    
 				 m_instance.FreeConnectionPool.remove(_conn);   //�Ƴ�������  
 				    
 				 m_instance.ConnectionPool.add(_conn);  //�������ӳ�       
 				 break;   
 			}  
 		}  
 		//�����г��Ƿ�Ϊ��  
 		if (m_instance.FreeConnectionPool.isEmpty())  
 		{   
 			//�ټ���Ƿ��ܹ�����   
 			if (m_instance.current_conn_count < m_instance.MaxConnectionCount)   
 			{   
 				//�½����ӵ��������ӳ�    
 				int newcount = 0 ;    
 				//ȡ��Ҫ��������Ŀ    
 				if (m_instance.MaxConnectionCount - m_instance.current_conn_count >= m_instance.MinConnectionCount)    
 				{     
 					newcount = m_instance.MinConnectionCount;    
 				}    
 				else    
 				{     
 					newcount = m_instance.MaxConnectionCount - m_instance.current_conn_count;
 				}    
 				//��������    
 				for (int i=0;i <newcount; i++)    
 				{     
 					_Connection _conn = _Connection.getConnection(m_instance, m_instance.connparam);     
 					m_instance.FreeConnectionPool.add(_conn);     
 					m_instance.current_conn_count ++;    
 				}   
 			}   
 			else   
 			{
 				//��������½�������Ƿ����Ѿ��黹������    
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
 		//�ٴμ���Ƿ��ܷ�������  
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
 			if(conn == null)//���������˵�������ӿ���    
 				throw new SQLException("û�п��õ����ݿ�����");  
 		}  
 		System.out.println("get connection");  
 		return conn; 
 	} 
 	 /**  
 	 * �رո����ӳ��е��������ݿ�����  
 	 * @throws SQLException  
 	 */ 
 	 public synchronized void close() throws SQLException 
 	 {  
 	 	this.isflag = false;  
 	 	SQLException excp = null;  
 	 	//�رտ��г�  
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
 	 	//�ر���ʹ�õ����ӳ�  
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
 	 * �����Ƿ�֧������  
 	 * @return boolean  
 	 */ 
 	 public boolean isSupportTransaction() 
 	 {  
 	 	return m_instance.supportTransaction; 
 	 }   
 	 /**  
 	 * ���ӳص��ȹ���  
 	 *  
 	 */ 
 	 public void schedule() 
 	 {  
 	 	Connection conn = null;  
 	 	//�ټ���Ƿ��ܹ�����  
 	 	Iterator iter = null;  
 	 	//����Ƿ����Ѿ��黹������  
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
 	 		//�½����ӵ��������ӳ�   
 	 		int newcount = 0 ;   
 	 		//ȡ��Ҫ��������Ŀ   
 	 		if (m_instance.MaxConnectionCount - m_instance.current_conn_count >= m_instance.MinConnectionCount)   
 	 		{    
 	 			newcount = m_instance.MinConnectionCount;   
 	 		}   
 	 		else   
 	 		{    
 	 			newcount = m_instance.MaxConnectionCount - m_instance.current_conn_count;   
 	 		}  
 	 		//��������  
 	 		for (int i=0;i <newcount; i++)   
 	 		{    
 	 			_Connection _conn = _Connection.getConnection(m_instance, m_instance.connparam);    
 	 			m_instance.FreeConnectionPool.add(_conn);    
 	 			m_instance.current_conn_count ++;   
 	 		}  
 	 	} 
 	 }
}