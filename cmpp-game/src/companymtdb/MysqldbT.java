package companymtdb;
import java.sql.*;
import java.io.*;
import java.util.ArrayList;
//import com.xiangtone.util.*;
import dbcp.*;
public class MysqldbT {
	 String sDBDriver="com.mysql.jdbc.Driver";
	 Connection conn = null;
	 ResultSet  rs=null;
	 public Statement  stmt=null;
	 String sDBCon="";
     String strDate = "";
     /*
      * 以下相应的都是填写配置文件里面相应的名称
      */
     String dbIp = "";//设置数据库ip
     String dbName = "";//设置数据库名称
     String dbUser = "";//设置数据库用户名
     String dbPwd = "";//设置数据库密码
     String dbPort = "";//设置数据库端口
     //connectURI, "root", "password", "com.mysql.jdbc.Driver", 5, 100, 30, 10000
     public MysqldbT(String dbIp,String dbName,String dbUser,String dbPwd,String dbPort)
 	{
    	 this.dbIp = dbIp;
    	 this.dbName = dbName;
    	 this.dbUser = dbUser;
    	 this.dbPwd = dbPwd;
    	 this.dbPort = dbPort;
    	 /*
 		try
 		{
 			Class.forName(sDBDriver);
 		}
 		catch(java.lang.ClassNotFoundException e)
 		{
 			System.out.println(e.toString());
 		}
 		*/
		String xiangtone_dbip   = (String)ConfigManager.getInstance().getConfigItem(this.dbIp,this.dbIp + " dbip not found!");
	    String xiangtone_dbport = (String)ConfigManager.getInstance().getConfigItem(this.dbPort,this.dbPort + " dbport not found!");
		String xiangtone_dbname = (String)ConfigManager.getInstance().getConfigItem(this.dbName,this.dbName + "dbname not found!");
		String xiangtone_dbuser = (String)ConfigManager.getInstance().getConfigItem(this.dbUser,this.dbUser + " dbuser not found!");
 		String xiangtone_dbpwd  = (String)ConfigManager.getInstance().getConfigItem(this.dbPwd,this.dbPwd + " dbpwd not found!");
 		
 			
 		sDBCon = "jdbc:mysql://"+xiangtone_dbip+":"+xiangtone_dbport+"/"+xiangtone_dbname;
 		//sDBCon+= "?user="+xiangtone_dbuser+"&password="+xiangtone_dbpwd+"&useUnicode=true&characterEncoding=GBK";
 		System.out.println(sDBCon);
 		////////////////////////
 		//connectURI, "root", "password", "com.mysql.jdbc.Driver", 5, 100, 30, 10000
 		//db = DbcpBean.get//new DbcpBean(sDBCon,xiangtone_dbuser,xiangtone_dbpwd,sDBDriver,5,100,30,10000);   
 		////////////////////////
 		
 		try{
 			//System.out.println("the dbcp connection is:" + db.getConn());
 			//db = new DbcpBean(sDBCon,xiangtone_dbuser,xiangtone_dbpwd,sDBDriver,5,100,30,10000);
 			//conn=db.getConn();//DriverManager.getConnection(sDBCon);
 			conn = ConnectionSource.getConnection();
 			System.out.println("the conn is:" + conn);
 			//if(conn == null){
 			//	db = new DbcpBean(sDBCon,xiangtone_dbuser,xiangtone_dbpwd,sDBDriver,5,100,30,10000);
 			//	conn=db.getConn();
 			//}
 	 		stmt=conn.createStatement();
 		}catch(Exception e){
 			e.printStackTrace();
 		}
		
 		
 	}
     
     public void executeUpdate(String sql){
 		try{
 			//conn=DriverManager.getConnection(sDBCon);
 			//stmt=conn.createStatement();
 	     	stmt.executeUpdate(sql);
 	     	//conn.close();
 	    }
 	    catch(SQLException ex) 
 	    {
           System.out.println(ex.toString());
 	    }
 	    
 	    return;
 	}
 	
 	public void executeInsert(String sql){
 	
 		try{
 			//conn=DriverManager.getConnection(sDBCon);
 			//stmt=conn.createStatement();
 	     	stmt.executeUpdate(sql);
 	     	//conn.close();
 	    }
 	    catch(SQLException ex) 
 	    {
           System.out.println(ex.toString());
 	    }
 	    
 	    return;
 	}
 	public void executeDelete(String sql){
 	 	
 		try{
 			//conn=DriverManager.getConnection(sDBCon);
 			//stmt=conn.createStatement();
 	     	stmt.executeUpdate(sql);
 	     	//conn.close();
 	    }
 	    catch(SQLException ex) 
 	    {
           System.out.println(ex.toString());
 	    }
 	    
 	    return;
 	}
 	public ResultSet executeQuery(String sql){
 		rs = null;
 		try{
 			//conn=DriverManager.getConnection(sDBCon);
 			//stmt=conn.createStatement();
 		  	rs=stmt.executeQuery(sql);
 		  	//conn.close();
 		  	
 		}
 		catch(SQLException ex)
 		{
 		 	System.out.println("error:"+sql);
 		 	System.out.println(ex.toString());
 			System.out.println("execQuery:"+stmt);  
 		}
 		return rs;
 	}
 	//add at 10-04-08
 	public void batchUpdate(ArrayList sqlList){
 		String sql = "";
 		try{
 			for(int i = 0;i < sqlList.size();i++){
 	 			sql = (String)sqlList.get(i);
 	 			stmt.addBatch(sql);
 	 		}
 			stmt.executeBatch();
 		}catch(Exception e){
 			e.printStackTrace();
 		}
 		
 	}
 	public void setBin(String sql, byte[] ndata,int len)
 	{
 		if(len >140 )
 			len=140;
 		java.sql.PreparedStatement pstm=null;
 	   try{
 	     	pstm = conn.prepareStatement(sql);
 			ByteArrayInputStream in= new ByteArrayInputStream(ndata);
 			pstm.setBinaryStream(1,in,len);
 			int rows = pstm.executeUpdate();
 	     	System.out.println("rows:"+rows);
 	     	pstm.close();
 	   }catch(SQLException ex) {
 	    	System.err.println("aq.executeDelete:" + ex.getMessage());
 	    	System.err.println("aq.executeDelete: " + sql);
 	   }
 	} 
 	public void close() throws Exception{
 			if (stmt != null)
 			{
 				stmt.close();
 				stmt = null;
 			}
 			
 			if(conn != null){
 				conn.close();
 				conn = null;
 			}
 			
 	}
 	public String str_replace(String str)
 	{
 	   char ch='\'';
 	   String strResult = "";
 	   int n1=-1,n2=0;
 	   while(true){
 	       n1 = str.indexOf(ch,n2);
 	       //System.out.println("n1:"+n1);
 	       if( n1 == -1){
 	           strResult += str.substring(n2);
 	           break;
 	       }else{
 	           strResult += str.substring(n2,n1)+'\\'+ch;
 	       }
 	       n2 = n1+1;
 	   }
 	   
 	   //if( strResult.substring(strResult.length()-1).equals("\\") )
 	    //   strResult+="\\";
 	   
 	   return strResult;
 	}	
 	
     
    public void setDbIp(String dbIp){
    	this.dbIp = dbIp;
    }
    public void setDbName(String dbName){
    	this.dbName = dbName;
    }
    public void setDbUser(String dbUser){
    	this.dbUser = dbUser;
    }
    public void setDbPwd(String dbPwd){
    	this.dbPwd = dbPwd;
    }
    public void setDbPort(String dbPort){
    	this.dbPort = dbPort;
    }
    
}
