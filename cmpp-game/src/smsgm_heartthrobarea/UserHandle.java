package smsgm_heartthrobarea;
import java.sql.*;
import java.util.*;

public class UserHandle {
	User user;
	Mysqldb mydb;
	public UserHandle(){
		mydb = new Mysqldb();
	}
	public void setUser(User user){
		this.user = user;
	}
	public boolean checkUser(){//检测用户是否存在
		boolean checkFlag = false;
		try{
			String strSql = "select id from user where cpn='" + this.user.getCpn()+"'";
			ResultSet rs = mydb.executeQuery(strSql);
			if(rs.next()){
				checkFlag = true;
				this.updateLoginDate();
			}
			else{
				this.newUser();
			}
		}catch(Exception e){

			e.printStackTrace();
		}
		return checkFlag;
	}
	public void newUser(){
		String strSql = "insert into user set cpn='" + this.user.getCpn() + "',logindate=now(),regdate=now()";
		try{
			this.mydb.executeUpdate(strSql);
		}catch(Exception e){
			System.out.println("new user Exception");
			e.printStackTrace();
		}
		this.mydb.dbclose();
	}
	public void updateLoginDate(){
		String strSql = "update user set logindate=now() where cpn = '" + this.user.getCpn() + "'";
		try{
			this.mydb.executeUpdate(strSql);
		}catch(Exception e){
			System.out.println("user update logindate error:");
			e.printStackTrace();
		}
		this.mydb.dbclose();
	}
	/*
	 * 取得最近1条信息
	 */
	public String getNews(){
		ArrayList newsList = new ArrayList();
		this.getNewsList(newsList);
		String retMsg = "";
		int newNum = newsList.size();
		if(newNum > 0){
			Random r = new Random();
			retMsg = (String)newsList.get(r.nextInt(newNum));
		}
		else{
			retMsg = "对不起，暂时还没有新数据。";
		}
		return retMsg;
	}
	public void getNewsList(ArrayList newsList){
		String strSql = "select content from news order by id";
		String retMsg = "";
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			while(rs.next()){
				newsList.add(rs.getString("content"));
				//retMsg += rs.getString("content") + "/n";
			}
		}catch(Exception e){
			System.out.println("news have exception");
			e.printStackTrace();
			//retMsg = "对不起，暂时还没有新数据。";
		}
		this.mydb.dbclose();
	}
	/*
	 * 添加用户新的信息
	 */
	public void WriteEnjoy(){
		String strSql = "insert news set cpn='" + this.user.getCpn()+"',content='" + this.user.getUserInput() + "',writedate=now()";
		try{
			this.mydb.executeUpdate(strSql);
		}catch(Exception e){
			System.out.println("write hava exception ");
			e.printStackTrace();
		}
		this.mydb.dbclose();
	}
}
