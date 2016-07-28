package org.x;
import com.xiangtone.sql.Mysqldb;

/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
public class MtSubmitSeq{
	public int    submitSeq;
  public String submitMsgID;
  public int    submitResult;
  public String ismgID;
  private Mysqldb db;
  
  public MtSubmitSeq(){
  	db = new Mysqldb();
  }
  public void updateSubmitSeq(){
  	try{
  		String strSql = "update sms_mtlog set submit_seq = 0 ,submit_msgid='"+this.submitMsgID+"',submit_result="+this.submitResult+" where submit_seq = "+this.submitSeq+" and ismgid ='"+this.ismgID+"' order by id desc limit 1";
    	db.execUpdate(strSql);
    	db.close();
  	}catch(Exception e){
  		System.out.println(e.toString());
  	}
  	
    
  }
	public void setMT_submitSeq(int submitSeq) { this.submitSeq=submitSeq;}
  public void setMT_submitMsgID(String submitMsgID) { this.submitMsgID=submitMsgID;}
  public void setMT_submitResult(int submitResult) {this.submitResult=submitResult;}
  public void setMT_ismgID(String ismgID) {this.ismgID=ismgID;}
}