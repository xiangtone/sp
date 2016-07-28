package dbcp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DbcpConnectionSource {
	private static BasicDataSource dataSource = null;
	public static String dbName = "";


    public DbcpConnectionSource() {
    }



    public static void init() {



        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (Exception e) {
                //
            }
            dataSource = null;
        }



        try {
            Properties p = new Properties();
            p.setProperty("driverClassName", "com.mysql.jdbc.Driver");
            p.setProperty("url", "jdbc:mysql://192.168.1.41:3306/" + dbName);
            p.setProperty("password", "Z6sxtfn.ZHRpm88J");
            p.setProperty("username", "smsplatform");
            p.setProperty("maxActive", "30");
            p.setProperty("maxIdle", "2");
            p.setProperty("maxWait", "30000");
            p.setProperty("removeAbandoned", "false");
            p.setProperty("removeAbandonedTimeout", "50");
            p.setProperty("testOnBorrow", "true");
            p.setProperty("logAbandoned", "true");
						//p.setProperty("show_sql", "true");
            dataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);



        } catch (Exception e) {
            //
        }
    }
    public static synchronized Connection getConnection() throws  SQLException {
    	//System.out.println("the source is:" + dataSource);
        if (dataSource == null) {
            init();
        }
        Connection conn = null;
        if (dataSource != null) {
            conn = dataSource.getConnection();
        }
        return conn;
    }
}
