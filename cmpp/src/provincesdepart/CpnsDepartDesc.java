package provincesdepart;

import java.sql.*;

public class CpnsDepartDesc implements Runnable{

	Mysqldb mydb;

	public CpnsDepartDesc(){

		

	}

	public void run(){

		while(true){

			System.out.println("start to depart cpn province");

				DepartHandle();

				try {

					

					

					System.out.println("sleep 5min");

					Thread.sleep(1000*60*5);

				} catch (InterruptedException e) {

					// TODO Auto-generated catch block

					e.printStackTrace();

				}

		}	

	}

	public void DepartHandle(){

		mydb = new Mysqldb();

		ProvincesHandle ph = new ProvincesHandle();

		//String strSql = "select id,destcpn from sms_mtlog WHERE province = '0000' OR province = ''";

		String strSql = "select id,destcpn from sms_mtlogbackup WHERE province = '' group by destcpn order by id desc limit 10000";



		try{

			ResultSet rs = mydb.executeQuery(strSql);

			String cpn = "";

			int cpnId = 0;

			int i = 0;

			while(rs.next()){

				cpnId = rs.getInt("id");

				cpn = rs.getString("destcpn");

				System.out.println("check cpn:" + cpn);

				if(cpn.length() < 11){

					continue;	

				}

				

				ph.provinceHandle(cpnId,cpn);

				i++;

				if((i%1000) == 0){

					System.out.println("start to sleep");

					Thread.currentThread().sleep(5000);

				}

				

			}

			

		}catch(Exception e){

			System.out.println("exception :"+e);

		}

		mydb.dbclose();

		ph.promydb.dbclose();

	}

	public static void main(String[] args){

		CpnsDepartDesc cd = new CpnsDepartDesc();

		new Thread(cd).start();

		//cd.DepartHandle();

	}

}

