/*
 *Copyright (c) 2003-2004 xmxiangtone, Inc. All rights reserved.
 *
 *
 */
package com.xiangtone.sql;
import java.lang.reflect.*;
import java.sql.*;

/** 
* @author youyongming 
* �������ݿ����ӵĴ����� 
*/
public class _Connection implements InvocationHandler
{ 
	
	private Connection conn = null; //��������
	//���������Ӵ��������
	private Statement statRef = null;
	private PreparedStatement prestatRef = null;
	private boolean supportTransaction = false;//�Ƿ�֧�������־
	private boolean isFree = false; //���ݿ��æ״̬ 
	long lastAccessTime = 0; //���һ�η���ʱ�� l
	//����Ҫ�ӹܵĺ��������� 
	String CREATESTATE = "createStatement"; 
	String CLOSE = "close"; 
	String PREPARESTATEMENT = "prepareStatement"; 
	String COMMIT = "commit"; 
	String ROLLBACK = "rollback";
	
 	/**  
 	* ���캯��������˽�У���ֹ��ֱ�Ӵ���  
 	* @param param ���Ӳ���  
 	*/ 
 	private _Connection(ConnectionParam param) 
 	{  
 		//��¼����    
 		try
 		{   
 			//��������   
 			Class.forName(param.getDriver()).newInstance();   
 			conn = DriverManager.getConnection(param.getUrl(),param.getUser(), param.getPassword());
 			DatabaseMetaData dm = null;   
 			dm = conn.getMetaData();   
 			supportTransaction = dm.supportsTransactions();//�ж��Ƿ�֧������   
 			System.out.println("support Transaction:"+supportTransaction);
 			System.out.println("Conn:"+conn);  
 		}  
 		catch(Exception e)  
 		{   
 			e.printStackTrace();  
 		} 
 	}
 	/* (non-Javadoc)  
 	* @see java.lang.reflect.InvocationHandler#invoke��
 	*(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])  
 	*/ 
 	public Object invoke(Object proxy, Method method, Object[] args)  throws Throwable 
 	{   
 		Object obj = null;   
 		//�ж��Ƿ������close�ķ������������close�������������Ϊ����״̬   
 		if(CLOSE.equals(method.getName()))   
 		{    
 			//���ò�ʹ�ñ�־    
 			setIsFree(false);    
 			//����Ƿ��к������������������������Դ    
 			if (statRef != null)     
 				statRef.close();    
 			if (prestatRef != null)     
 				prestatRef.close();    
 			return null;   
 		}  
 		//�ж���ʹ����createStatement���   
 		if (CREATESTATE.equals(method.getName()))   
 		{    
 			obj = method.invoke(conn, args);    
 			statRef = (Statement)obj;
 			//��¼���    
 			return obj;   
 		}   
 		//�ж���ʹ����prepareStatement���   
 		if (PREPARESTATEMENT.equals(method.getName()))   
 		{    
 			obj = method.invoke(conn, args);    
 			prestatRef = (PreparedStatement)obj;    
 			return obj;   
 		}
 			   
 		//�����֧�����񣬾Ͳ�ִ�и�����Ĵ���   
 		if ((COMMIT.equals(method.getName())||ROLLBACK.equals(method.getName()))
 			&& (!isSupportTransaction()))   
 	   		 return null;     
 	    obj = method.invoke(conn, args);    
 	    //�������һ�η���ʱ�䣬�Ա㼰ʱ�����ʱ������   
 	    lastAccessTime = System.currentTimeMillis();   
 	    return obj; 
 	}
 	/**  
 	* �������ӵĹ�����ֻ���ù�������  
 	* @param factory Ҫ���ù���������һ������ȷ��ʼ��  
 	* @param param ���Ӳ���  
 	* @return ����  
 	*/ 
 	static public _Connection getConnection(ConnectionFactory factory, ConnectionParam param) 
 	{  
 		if (factory.isCreate())
 		//�ж��Ƿ���ȷ��ʼ���Ĺ���  
 		{   
 			_Connection _conn = new _Connection(param);   
 			return _conn;  
 		}  
 		else   
 			return null; 
 	}
 	/**
 	*
 	*
 	*/  
 	public Connection getFreeConnection() 
 	{ 
 		 //�������ݿ�����conn�Ľӹ��࣬�Ա��סclose����  
 		 Connection conn2 = (Connection)Proxy.newProxyInstance(conn.getClass().getClassLoader(),conn.getClass().getInterfaces(),this);  
 		   //System.out.println("conn2"+conn2);
 		 return conn2; 
    }
    /**  
    * �÷��������Ĺر������ݿ������  
    * @throws SQLException  
    */ 
    void close() throws SQLException
    {  
    	//����������conn��û�б��ӹܵ����ӣ����һ������close�������ֱ�ӹر�����  
    	conn.close(); 
    }    
    public void setIsFree(boolean value) {  isFree = value; }  
    public boolean isFree() {  return isFree; }  
    /**  
    * �ж��Ƿ�֧������  
    * @return boolean  
    */ 
    public boolean isSupportTransaction() {  return supportTransaction; }
}
