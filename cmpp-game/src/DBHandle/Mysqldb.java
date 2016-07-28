package DBHandle;
import java.sql.*;
import java.io.*;
import com.xiangtone.util.*;

public class Mysqldb {
	 String sDBDriver="org.gjt.mm.mysql.Driver";
	 Connection conn=null;
	 ResultSet  rs=null;
	 Statement  stmt=null;

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
     public Mysqldb(String dbIp,String dbName,String dbUser,String dbPwd,String dbPort)
 	{
    	 this.dbIp = dbIp;
    	 this.dbName = dbName;
    	 this.dbUser = dbUser;
    	 this.dbPwd = dbPwd;
    	 this.dbPort = dbPort;
 		try
 		{
 			Class.forName(sDBDriver);
 		}
 		catch(java.lang.ClassNotFoundException e)
 		{
 			System.out.println(e.toString());
 		}
 		
 		String xiangtone_dbip   = this.dbIp;//(String)ConfigManager.getInstance().getConfigItem(this.dbIp,this.dbIp + " dbip not found!");
 	    String xiangtone_dbport = this.dbPort;//(String)ConfigManager.getInstance().getConfigItem(this.dbPort,this.dbPort + " dbport not found!");
 		String xiangtone_dbname = this.dbName;//(String)ConfigManager.getInstance().getConfigItem(this.dbName,this.dbName + "dbname not found!");
 		String xiangtone_dbuser = this.dbUser;//(String)ConfigManager.getInstance().getConfigItem(this.dbUser,this.dbUser + " dbuser not found!");
 		String xiangtone_dbpwd  = this.dbPwd;//(String)ConfigManager.getInstance().getConfigItem(this.dbPwd,this.dbPwd + " dbpwd not found!");
 		
 			
 		sDBCon = "jdbc:mysql://"+xiangtone_dbip+":"+xiangtone_dbport+"/"+xiangtone_dbname;
 		sDBCon+= "?user="+xiangtone_dbuser+"&password="+xiangtone_dbpwd+"&useUnicode=true&characterEncoding=GBK";
 		System.out.println(sDBCon);
 		try{
 			conn=DriverManager.getConnection(sDBCon);
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
