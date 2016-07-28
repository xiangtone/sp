package tablecreate;

 import java.sql.*;

/******************************************************

 *                   ���ݿ⴦����                     *

 ******************************************************/



public class Mysqldb

{



	String sDBDriver="org.gjt.mm.mysql.Driver";

	Connection conn=null;

	ResultSet rs=null;

	Statement stmt=null;

        String DBCon="";//"jdbc:mysql://192.168.1.41/smscompanymts?user=smsplatform&password=Z6sxtfn.ZHRpm88J&useUnicode=true&characterEncoding=gbk";

	public Mysqldb(String db)

	{

  		try

  		{
				DBCon="jdbc:mysql://192.168.1.41/" + db + "?user=smsplatform&password=Z6sxtfn.ZHRpm88J&useUnicode=true&characterEncoding=gbk";

   			Class.forName(sDBDriver);    //װ����������

   			conn=DriverManager.getConnection(DBCon);

    		        stmt=conn.createStatement();

   	                }

  		catch(Exception e)

  		{

  			System.err.println("Unable to load driver:" + e.getMessage());

  			e.printStackTrace();

  		        }



 	}



	/******************************************************

	 *            update .insert,delete �Ȳ���            *

	 ******************************************************/

 public void executeUpdate(String sql)

 {

    	try

    	{

    		stmt.executeUpdate(sql);

     	        }

  	catch(SQLException ex)

  	{

   		     System.err.println("aq.executeInsert:" + ex.getMessage());

    		     System.err.println("aq.executeInsert:" + sql);

    		     ex.printStackTrace();

    	             }

       }



/**************************************************

 *                 SELECT ����                    *

 **************************************************/

 public ResultSet executeQuery(String sql)

 {

         rs=null;

  	 try

  	{

   		rs=stmt.executeQuery(sql);

   		}

    	 catch(SQLException ex)

    	 {

    		System.err.println("aq.executeQuery:" + ex.getMessage());

    		System.err.println("aq.executeQuery: " + sql);

    		ex.printStackTrace();

    	        }

    	return rs;

    }

/*************************************************

 *                   �ر���������                *

 *************************************************/

   public void dbclose()

   {

		if (stmt != null)

		{

			try

			{

				stmt.close();

			         }

			catch(Exception e)

			{

				e.printStackTrace();

			        }

		         }

		if(conn !=null)

		{
			try{
				conn.close();
			 }
			catch(Exception e){
				e.printStackTrace();
			}
		}
   }

 }