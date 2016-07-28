package  com.xiangtone.sql;
import java.sql.*;
public class test
{
	public static void main(String[] args)
	{
		try
		{
			Mysqldb db = new Mysqldb("localhost","3306","sms_xt_public","root","");
			ResultSet rs = db.executeQuery("select * from sms_user");
			for(int i=0;i<10;i++)
			{
				rs = db.executeQuery("select * from sms_company");
				System.out.println("i:"+i);
		       }
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}

		
		