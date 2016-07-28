package smsmtscount;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Scanner;
import DBHandle.MysqldbT;
public class CompanysMtsDateCountHandle implements Runnable{
	ArrayList companysList;
	ArrayList gamesList;
	MysqldbT mydb;
	int countDate;
	public void run(){
		//while(true){
			String dbIp = "xiangtone_dbip";
			String dbName = "companymts";
			String dbUser = "xiangtone_dbuser";
			String dbPwd = "xiangtone_dbpwd";
			String dbPort = "xiangtone_dbport";
			//while(true){
				try{
					System.out.println("start count mts");
					mydb = new MysqldbT(dbIp,dbName,dbUser,dbPwd,dbPort);
					this.companysGamesList();
					this.mydb.close();
					System.out.println("count mts finished");
					//Thread.currentThread().sleep(1000 * 60 * 5);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			//}
		//}
	}
	public void companysGamesList(){
		companysList = new ArrayList();
		gamesList = new ArrayList();
		String tableName = this.getGamesCompanysCountDate(0) + "company_mt";
		String countDate = this.getGamesCompanysCountDate(1);
		String strSql = "select company,game from " + tableName + " group by company,game";
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			String companyName = "";
			while(rs.next()){
				companyName = rs.getString("company");
				if(!companyName.equals("")){
					companysList.add(companyName);
					gamesList.add(rs.getString("game"));
				}
				
			}
			countCompanysMts(tableName,countDate);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void countCompanysMts(String tableName,String countDate){
		int companysNum = this.companysList.size();
		String company = "";
		String game = "";
		int gameMts = 0;
		int succMts = 0;//���гɹ�����
		int distMts = 0;//���п۷�����
		int cpns = 0;//�����ֻ�����
		int succpns = 0;//���гɹ��ֻ�����
		//company game mtnum countdate succmts distmts succpns 
		for(int i = 0;i < companysNum;i++){
			company = (String)this.companysList.get(i);
			game = (String)this.gamesList.get(i);
			gameMts = countMtsNum(company,game,tableName);//��Ӧ������˾��Ӧҵ����������
			succMts = countSuccMtsNum(0,company,game,tableName);//0Ϊ����ȫ���ɹ���������
			distMts = countSuccMtsNum(1,company,game,tableName);//1��־λΪ����ȫ���Ʒ���������
			cpns = countCpnNum(0,company,game,tableName);
			succpns = countCpnNum(1,company,game,tableName);
			if(checkCountDate(company,game,countDate)){
				this.updateMtsCount(company,game,countDate,gameMts,succMts,distMts,cpns,succpns);
			}
			else{
				this.insertNewMtsCount(company,game,countDate,gameMts,succMts,distMts,cpns,succpns);
			}
		}
	}
	private int countMtsNum(String company,String game,String tableName){
		String strSql = "select count(*) as count from " + tableName + " where company='" + company + "' and game='" + game + "'";
		int gameMtsNum = 0;
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				gameMtsNum = rs.getInt("count");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return gameMtsNum;
	}
	private int countSuccMtsNum(int type,String company,String game,String tableName){
		//�ú���ȡ�óɹ����е�����
		String strSql = "";
		if(type == 0){
			strSql = "select count(*) as count from " + tableName + " where company='" + company + "' and game='" + game + "' and sendstate='DELIVRD'";

		}
		else{
			strSql = "select count(DISTINCT linkid) as count from " + tableName + " where company='" + company + "' and game='" + game + "' and sendstate='DELIVRD'";
		}
				int succMtsNum = 0;
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				succMtsNum = rs.getInt("count");
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return succMtsNum;
	}
	private int countCpnNum(int type,String company,String game,String tableName ){
		String strSql = "";
		if(type == 0){
			strSql = "select count(DISTINCT cpn) as count from " + tableName + " where company='" + company + "' and game='" + game + "'";

		}
		else{
			strSql = "select count(DISTINCT cpn) as count from " + tableName + " where company='" + company + "' and game='" + game + "' and sendstate='DELIVRD'";
		}
				int succCpnsNum = 0;
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				succCpnsNum = rs.getInt("count");
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return succCpnsNum;
	}
	//company,game,countDate,gameMts,succMts,distMts,cpns,succpns
	//company game mtnum countdate succmts distmts succpns 
	private void updateMtsCount(String company,String game,String countDate,int gameMts,int succMts,int distMts,int cpns,int succpns){
		String strSql = "update company_mtcount set ";
		strSql += " mtnum='" + gameMts + "'";
		strSql += " ,mtcpns='" + cpns + "'";
		strSql += " ,succmts='" + succMts + "'";
		strSql += " ,distmts='" + distMts + "'";
		strSql += " ,succpns='" + succpns + "'";
		strSql += " where company='" + company + "'";
		strSql += " and game='" + game + "'";
		strSql += " and countdate='" + countDate + "'";
		this.mydb.executeUpdate(strSql);
		
	}
	private void insertNewMtsCount(String company,String game,String countDate,int gameMts,int succMts,int distMts,int cpns,int succpns){
		String strSql = "insert into  company_mtcount set ";
		strSql += " company='" + company + "'";
		strSql += " ,game='" + game + "'";
		strSql += " ,countdate='" + countDate + "'";
		strSql += " ,mtnum='" + gameMts + "'";
		strSql += " ,mtcpns='" + cpns + "'";
		strSql += " ,succmts='" + succMts + "'";
		strSql += " ,distmts='" + distMts + "'";
		strSql += " ,succpns='" + succpns + "'";

		this.mydb.executeInsert(strSql);
	}
	private boolean checkCountDate(String company,String game,String countDate){
		String strSql = "select id from company_mtcount where company='" + company + "' and game='" + game + "' and countdate='" + countDate + "'";
		boolean existFlag = false;
		try{
			ResultSet rs = this.mydb.executeQuery(strSql);
			if(rs.next()){
				existFlag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return existFlag;
	}
	//��ͳ�Ƶ��������ڰ�Сʱͳ��
	public String getGamesCompanysCountDate(int type){
		String strDate = "";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH,-this.countDate);
		if(type == 0){
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd"); 
		    strDate = format.format(cal.getTime());
		}
		else if(type == 1){
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		    strDate = format.format(cal.getTime());
		}
	    return strDate;
	}
	public static void main(String[] args){
		CompanysMtsDateCountHandle mtsDateCountHandle = new CompanysMtsDateCountHandle();
		Scanner scanner = new Scanner(System.in);
		System.out.println("������Ҫͳ�ƺ��������е����ڡ���7Ϊ����ǰ������");
		mtsDateCountHandle.countDate = scanner.nextInt();
		new Thread(mtsDateCountHandle).start();
	}
}
