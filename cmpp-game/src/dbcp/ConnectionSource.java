package dbcp;

import org.apache.commons.dbcp.BasicDataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import java.sql.SQLException;

import java.sql.Connection;

import java.util.Properties;

public class ConnectionSource {

	private static BasicDataSource dataSource = null;







    public ConnectionSource() {

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

            p.setProperty("driverClassName", "org.gjt.mm.mysql.Driver");

            p.setProperty("url", "jdbc:mysql://211.136.85.187:3306/smscompanymts");

            p.setProperty("password", "c4PLqPYsHECKDL7m");

            p.setProperty("username", "smscompanymts");

            p.setProperty("maxActive", "20");

            p.setProperty("maxIdle", "10");

            p.setProperty("maxWait", "100");

            p.setProperty("removeAbandoned", "false");

            p.setProperty("removeAbandonedTimeout", "120");

            p.setProperty("testOnBorrow", "true");

            p.setProperty("logAbandoned", "true");



            dataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);







        } catch (Exception e) {

            //

        }

    }

    public static synchronized Connection getConnection() throws  SQLException {

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

