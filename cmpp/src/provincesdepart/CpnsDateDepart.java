package provincesdepart;

import java.sql.*;
import java.util.ArrayList;
public class CpnsDateDepart implements Runnable{

	Mysqldb mydb;
	int cpnsNum = 0;
	public CpnsDateDepart(){

		

	}

	public void run(){

		while(true){

			System.out.println("start to depart cpn province");

				DepartHandle();
				System.out.println("the num is:" + cpnsNum);
				if(this.cpnsNum < 1000){
					System.out.println("depart finished");
					System.exit(0);
				}
				try {

					System.out.println("sleep 5min");

					Thread.sleep(1000*2);

				} catch (InterruptedException e) {

					// TODO Auto-generated catch block

					e.printStackTrace();

				}
		}	

	}

	public void DepartHandle(){

		mydb = new Mysqldb();

		ProvincesDateHandle ph = new ProvincesDateHandle();

		//String strSql = "select id,destcpn from sms_mtlog WHERE province = '0000' OR province = ''";

		String strSql = "select id,destcpn from sms_mtlogbackup  WHERE province = '' group by destcpn limit 2000";
		ArrayList cpnsList = new ArrayList();
		System.out.println(strSql);
		try{

			ResultSet rs = mydb.executeQuery(strSql);

			String cpn = "";

			int cpnId = 0;

			int i = 0;

			while(rs.next()){

				cpnId = rs.getInt("id");

				cpn = rs.getString("destcpn");
				if(cpn.length() > 11){
					cpn = cpn.substring(2,13);	
					
				}
				System.out.println("check cpn:" + cpn);

				if(cpn.length() < 11){

					continue;	

				}
				//ph.provinceHandle(cpnId,cpn);
         cpnsList.add(cpn);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
				i++;


			}
			this.cpnsNum = cpnsList.size();
			System.out.println("the size num is:" + this.cpnsNum);
			ph.provinceHandle(cpnsList);
			

		}catch(Exception e){

			System.out.println("exception :"+e);

		}

		mydb.dbclose();

		ph.promydb.dbclose();

	}

	public static void main(String[] args){

		CpnsDateDepart cd = new CpnsDateDepart();

		new Thread(cd).start();

		//cd.DepartHandle();

	}

}

