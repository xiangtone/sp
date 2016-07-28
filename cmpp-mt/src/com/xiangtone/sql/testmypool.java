/* 
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
import java.sql.*;
public class testmypool 
{
 	public void test1() 
 	{  
 		String user = "root";  
 		String password = "";  
 		//String driver = "sun.jdbc.odbc.JdbcOdbcDriver";  
 		String driver = "org.gjt.mm.mysql.Driver";
 		//String url = "jdbc:odbc:gfqh2"; 
 		String url = "jdbc:mysql://192.168.1.154:3306/sms?user=root&password=&useUnicode=true&characterEncoding=GBK";
     
 		ConnectionParam param = new ConnectionParam(driver,url,user,password);  
 		ConnectionFactory cf = null; 
 		//new ConnectionFactory(param, new FactoryParam());  
 		try
 		{   
 			cf = new ConnectionFactory(param,new FactoryParam());   
 			Connection conn1 = cf.getFreeConnection();   
 			Connection conn2 = cf.getFreeConnection();   
 			Connection conn3 = cf.getFreeConnection();   
 			Statement stmt = conn1.createStatement();   
 			ResultSet rs = stmt.executeQuery("select * from requests");  
 			 if (rs.next())   
 			 {   
 			 	 System.out.println("conn1 y");     
 			 }   
 			 else   
 			 {    
 			 	System.out.println("conn1 n");     
 			 }    
 			 stmt.close();   
 			 conn1.close();    
 			 Connection conn4 = cf.getFreeConnection();   
 			 Connection conn5 = cf.getFreeConnection();  
 			  stmt = conn5.createStatement();   
 			  rs = stmt.executeQuery("select * from requests");   
 			  if (rs.next())   
 			  {    
 			  	System.out.println("conn5 y");     
 			  }   
 			  else   
 			  {   
 			  	 System.out.println("conn5 n");     
 			  }    
 			  conn2.close();   
 			  conn3.close();   
 			  conn4.close();   
 			  conn5.close();   
 			  }  
 		catch(Exception e)  
 		{   
 			e.printStackTrace();  
 		}  
 		finally
 		{   
 			try
 			{    
 			  	cf.close();   
 			}   
 			catch(Exception e)   
 			{    
 			  		e.printStackTrace();   
 			}  
 		}
 	} 
 	 public static void main(String[] args) 
 	 { 
 	 	 String user = "root";
 	 	 String password ="";
 	 	 String driver="org.gjt.mm.mysql.Driver";
 	 	 String url = "jdbc:mysql://192.168.1.154:3306/sms_public";
 	 	 
 	 	 ConnectionParam param = new ConnectionParam(driver,url,user,password);  
 	 	 ConnectionFactory cf = null;//new ConnectionFactory(param,new FactoryParam());  
 	 	 try
 	 	 {   
 	 	 	cf = new ConnectionFactory(param,new FactoryParam());   
 	 	 	ConnectionFactory cf1= new ConnectionFactory(param,new FactoryParam());   
 	 	 	Connection conn1 = null;   
 	 	 	long time = System.currentTimeMillis();   
 	 	 	for (int i=0; i <10;i++)   
 	 	 	{    
 	 	 		conn1 = cf.getFreeConnection();    
 	 	 		Statement stmt = conn1.createStatement();    
 	 	 		ResultSet rs = stmt.executeQuery("select * from sms_user");    
 	 	 		if (rs.next())    
 	 	 		{     
 	 	 			System.out.println("conn1 y");      
 	 	 		}   
 	 	 		else    
 	 	 		{     
 	 	 			System.out.println("conn1 n");      
 	 	 		}     
 	 	 		conn1.close();     
 	 	 	}   
 	 	 	System.out.println("pool:" + (System.currentTimeMillis()-time));   
 	 	 	time = System.currentTimeMillis();  
 	 	 	 Class.forName(param.getDriver()).newInstance();   
 	 	 	 for (int i=0; i <10;i++)   
 	 	 	 {    
 	 	 	 	String strDBCon="jdbc:mysql://192.168.1.154/sms_public?user=root&password=&useUnicode=true&characterEncoding=gb2312";
			  //conn1 = DriverManager.getConnection(param.getUrl(),param.getUser(), param.getPassword());       
 	 	 	    conn1 = DriverManager.getConnection(strDBCon);
 	 	 	 	Statement stmt = conn1.createStatement();    
 	 	 	 	ResultSet rs = stmt.executeQuery("select * from sms_user");    
 	 	 	 	if (rs.next())    
 	 	 	 	{     
 	 	 	 		System.out.println("conn1 y");      
 	 	 	 	}    
 	 	 	 	else    
 	 	 	 	{     
 	 	 	 		System.out.println("conn1 n");      
 	 	 	 	}     
 	 	 	 	//conn1.close();     
 	 	 	 }     
 	 	 	 System.out.println("no pool:" + (System.currentTimeMillis()-time));  
 	 	 }  
 	 	 catch(Exception e)  
 	 	 {   
 	 	 	e.printStackTrace();  
 	 	 }  
// 	 	 finally
// 	 	 {   
// 	 	 	try
// 	 	 	{    
// 	 	 		cf.close();   
// 	 	 	}   
// 	 	 	catch(Exception e)   
// 	 	 	{    
// 	 	 		e.printStackTrace();   
// 	 	 	}  
// 	 	 }
 	 }
}
