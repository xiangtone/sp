package newsmsendstatecheck;
import java.sql.*;
import dbcp.ConnectionSource;
public class CheckState implements Runnable{
	public void run(){
		//Mysqldb mydb = new Mysqldb();
		//String strSql = "select sendstate from company_mt";
		try{
			SendStateHandle ssh = new SendStateHandle();
			ConnectionSource.getConnection();
			System.out.println("自动更新状态线程开启。。。");
			while(true){

				ssh.getUnUpdateMtState();
				ssh.mydb.dbclose();
				Thread.currentThread().sleep(1000 * 20);
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
