package tempreportlog;
import java.sql.*;

public class MtsMtHandle {
	Mysqldb mydb;
	public int    submitSeq;
  public String submitMsgID;
  public int    submitResult;
  public String ismgID;
	public MtsMtHandle(){
		mydb = new Mysqldb();
	}
	public void insertMtlog(String strSql){
		try{
					mydb.executeUpdate(strSql);
					mydb.dbclose();
		}catch(Exception e){
			System.out.println(e.toString());
		}

	}
	public void updateSubmitSeq(){//用于更新下行submit_msgId
		try{
					String strSql = "";
					updateMtsMtMsgId();//用于将下行msgid更新于下行表中
					//strSql = "update sms_mtlog set submit_seq = 0 ,submit_msgid='"+this.submitMsgID+"',submit_result="+this.submitResult+" where submit_seq = "+this.submitSeq+" and ismgid ='"+this.ismgID+"' order by id desc limit 1";
					//System.out.println(strSql);
					strSql = "delete from sms_mtlog where submit_seq = '"+this.submitSeq+"'";
					mydb.executeUpdate(strSql);
					mydb.dbclose();
					
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	private void updateMtsMtMsgId(){
		try{
			String strSql = "select linkid from sms_mtlog where submit_seq='" + this.submitSeq + "'";	
			ResultSet rs = mydb.executeQuery(strSql);
			String tableName =  FormatSysTime.getCurrentTime("yyyyMMdd") + "company_mt";
			if(rs.next()){
				String linkId = rs.getString("linkid");
				
				strSql = "update " + tableName +" set submit_linkid='0',submitmsgid='" + this.submitMsgID + "' where linkid='" + linkId + "' limit 1";	
				mydb.executeUpdate(strSql);
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	public void setMT_submitSeq(int submitSeq) { this.submitSeq=submitSeq;}
  public void setMT_submitMsgID(String submitMsgID) { this.submitMsgID=submitMsgID;}
  public void setMT_submitResult(int submitResult) {this.submitResult=submitResult;}
  public void setMT_ismgID(String ismgID) {this.ismgID=ismgID;}
}
