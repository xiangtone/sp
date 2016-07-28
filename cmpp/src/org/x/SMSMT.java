package org.x;
/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
import java.io.*;
import java.util.*;

import com.xiangtone.sql.Mysqldb;

import java.sql.*;

/**
*
*
*/
public class SMSMT
{
	
    	
    	public int vcpID;
    	public String ismgID;
    	public String spCode;
    	
    	public String destCpn;   //接收方的手机号
    	public String feeCpn;    //发送方的手机号
    	public String serverID;   // 内容属于那个项目的
    	public String serverName;
    	public String infoFee;    //费用的值(以分为单位)
    	public String feeCode;
    	public String feeType;    // 收费的方式
    	public String sendContent;
    	public int    mediaType;
    	public String sendTime;   //
    	
    	public String corpID;
    	//    	add at 061123
    	public String linkID;
    	public String msgId;//add at 08-11-27
    	public int cpnType;
    	//
    	
    	
    	public int    submitSeq;
    	public String submitMsgID;
    	public int    submitResult;
    	
    	public int tp_pid;
    	public int tp_udhi;
    	public int data_coding;
    	public int report_flag;
    	
    	
    	private Mysqldb db;
    	ResultSet rs = null;
    	private String strSql;
    	
    	
    	/**
    	* set and get method
    	*
    	*/
    	public int    getMT_vcpID() {return vcpID;}
    	public String getMT_ismgID() { return ismgID;}
    	public String getMT_spCode() { return spCode;}
    	
    	public String getMT_destCpn() { return destCpn;}
    	public String getMT_feeCpn() { return feeCpn;}
    	public String getMT_serverID() { return serverID;}
    	public String getMT_serverName() { return serverName;}
    	public String getMT_infoFee() { return infoFee;}
    	public String getMT_feeCode() { return feeCode;}
    	public String getMT_feeType() { return feeType;}
    	public String getMT_sendContent() { return sendContent;}
    	public int    getMT_mediaType() { return mediaType;}
    	public String getMT_sendTime() { return sendTime;}
    	public int    getMT_submitSeq() { return submitSeq;}
    	public String getMT_submitMsgID() { return submitMsgID;}
    	public int    getMT_submitResult() { return submitResult;}
    	public String getMT_msgId(){return msgId;}
    	
    	
    	//
    	
    	public void setMT_vcpID(int vcpID) { this.vcpID = vcpID;}
    	public void setMT_ismgID(String ismgID) {this.ismgID=ismgID;}
    	public void setMT_spCode(String spCode) { this.spCode=spCode;}
    	       
    	public void setMT_destCpn(String destCpn) { this.destCpn=destCpn;}
    	public void setMT_feeCpn(String feeCpn) { this.feeCpn=feeCpn;}
    	public void setMT_serverID(String serverID) { this.serverID=serverID;}
    	public void setMT_serverName(String serverName) { this.serverName=serverName;}
    	public void setMT_infoFee(String infoFee) { this.infoFee=infoFee;}
    	public void setMT_feeCode(String feeCode) { this.feeCode=feeCode;}
    	public void setMT_feeType(String feeType) { this.feeType=feeType;}
    	public void setMT_sendContent(String sendContent) { this.sendContent=sendContent;}
    	public void setMT_mediaType(int mediaType) { this.mediaType=mediaType;}
    	public void setMT_sendTime(String sendTime) { this.sendTime=sendTime;}
    	public void setMT_submitSeq(int submitSeq) { this.submitSeq=submitSeq;}
    	public void setMT_submitMsgID(String submitMsgID) { this.submitMsgID=submitMsgID;}
    	public void setMT_submitResult(int submitResult) {this.submitResult=submitResult;}
        public void setMT_corpID(String _corpid){this.corpID = _corpid;}
        //add at 061123
        public void setMT_linkID(String _linkid){ this.linkID = _linkid;}
        public void setMT_cpnType(int _cpntype){ this.cpnType = _cpntype;}
        public void setMT_msgID(String msgId){
        	this.msgId = msgId;	
        }
        /**
        * Constructor
        *
        */
        public SMSMT()
	    {
			db = new Mysqldb();
	    }
	    /**
	    *
	    *
	    */
	    
	    public void insertMTLog()
	    {
//	    	if(db == null)
//	    	{
//	    		db = new mysqldb();
//	    	}
			try
			{
				strSql = "insert into sms_mtlog set ";
				strSql+=" vcpid="+vcpID;
				strSql+=",ismgid='"+ismgID+"'";
				strSql+=",comp_msgid='"+submitMsgID+"'";//用于标识下发的信息的Id
				strSql+=",corpid='"+corpID+"'";
				strSql+=",spcode='"+spCode+"'";
				strSql+=",destcpn='"+destCpn+"'";
				strSql+=",feecpn='"+feeCpn+"'";
				strSql+=",serverid='"+serverID+"'";
				strSql+=",servername='"+serverName+"'";
				strSql+=",infofee='"+infoFee+"'";
				strSql+=",feetype='"+feeType+"'";
				strSql+=",feecode='"+feeCode+"'";
				strSql+=",content='"+sendContent+"'";
				strSql+=",linkid='"+linkID+"'";
				strSql+=",mediatype="+mediaType;
				strSql+=",sendtime='"+sendTime+"'";
				strSql+=",submit_msgid='"+submitMsgID+"'";
				strSql+=",submit_result="+submitResult;
				strSql+=",submit_seq="+submitSeq;
				System.out.println(strSql);
				db.execUpdate(strSql);
			}
			catch(Exception e)
			{
				System.out.println(strSql);
				System.out.println(e.toString());
				
			}
		}
		/**
		*
		*
		*/
	    public void updateSubmitSeq(String ismgid,int seq,String msg_id,int submit_result)
        {
        	try 
      	    {
      	    	strSql = "update sms_mtlog set submit_seq = 0 ,submit_msgid='"+msg_id+"',submit_result="+submit_result+" where submit_seq = "+seq+" and ismgid ='"+ismgid+"' order by id desc limit 1";
        	    db.execUpdate(strSql);
        	}
        	catch(Exception e)
        	{
        		System.out.println(strSql);
        		System.out.println(e.toString());
        		
        	}	
      }  
       public void updateSubmitSeq()
        {
        	try 
      	    {
      	    	strSql = "update sms_mtlog set submit_seq = 0 ,submit_msgid='"+this.submitMsgID+"',submit_result="+this.submitResult+" where submit_seq = "+this.submitSeq+" and ismgid ='"+this.ismgID+"' order by id desc limit 1";
        	    db.execUpdate(strSql);
        	}
        	catch(Exception e)
        	{
        		System.out.println(strSql);
        		System.out.println(e.toString());
        		
        	}	
      }  
      
      
       public void insertMCLog(int card_flag)
	    {
//	    	if(db == null)
//	    	{
//	    		db = new mysqldb();
//	    	}
			try
			{
				strSql = "insert into sms_mclog set ";
				strSql+=" vcpid="+vcpID;
				strSql+=",ismgid='"+ismgID+"'";
				strSql+=",corpid='"+corpID+"'";
				strSql+=",spcode='"+spCode+"'";
				strSql+=",destcpn='"+destCpn+"'";
				strSql+=",feecpn='"+feeCpn+"'";
				strSql+=",serverid='"+serverID+"'";
				strSql+=",servername='"+serverName+"'";
				strSql+=",infofee='"+infoFee+"'";
				strSql+=",feetype='"+feeType+"'";
				strSql+=",feecode='"+feeCode+"'";
				strSql+=",content='"+sendContent+"'";
				strSql+=",mediatype="+mediaType;
				strSql+=",sendtime='"+sendTime+"'";
				strSql+=",submit_seq="+submitSeq;
				strSql+=",card_flag="+card_flag;
				System.out.println(strSql);
				db.execUpdate(strSql);
			}
			catch(Exception e)
			{
				System.out.println(strSql);
				System.out.println(e.toString());
				
			}
		}  
}