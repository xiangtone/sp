package smsendstatecheck;
import java.sql.*;
public class CheckState implements Runnable{
	public void run(){
		//Mysqldb mydb = new Mysqldb();
		//String strSql = "select sendstate from company_mt";
		try{
			SendStateHandle ssh = new SendStateHandle();
			System.out.println("�Զ�����״̬�߳̿���������");
			while(true){

				ssh.getUnUpdateMtState();
				ssh.mydb.dbclose();
				Thread.currentThread().sleep(1000*60);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		CheckState cs = new CheckState();
		new Thread(cs).start();
	}
}
