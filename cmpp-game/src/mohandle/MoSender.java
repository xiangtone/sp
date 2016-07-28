package mohandle;
import java.net.*;
import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;    
import org.apache.log4j.PropertyConfigurator;

public class MoSender {
	private int companyId = 0;
	private Mo mo = null;
	String url = "";
	String sendStat = "";
	private ArrayList mosList;
	public void sentMo2Company(){
		Logger myLogger = Logger.getLogger("MsgSendLogger");
		Logger mySonLogger = Logger.getLogger("myLogger.mySonLogger"); 
		PropertyConfigurator.configure("log4j.properties"); 
		  
		  System.out.println("arraylist is:" + mosList.size());
		CompanyUrl companyUrl = new CompanyUrl("config.ini","sjnz");
		companyUrl.loadParam();
		String compUrl = companyUrl.getCompanyUrl();
		System.out.println("compUrl is:" + compUrl);
		String moXmlStr = MosXmlOrg();//organizeMo();
		
		System.out.println("xml is:" + moXmlStr);
		try{
			String status = "-1";
				 /*
			 SendMsgHttp proxy = new SendMsgHttp(compUrl);
			 proxy.setBody(moXmlStr.getBytes("UTF-8"));
			 String status = "-1";
		
       status = proxy.exec();
       if (status == null || !status.equals("0")) {
                status = proxy.exec();
       }
       */
       this.sendStat = status;
			myLogger.info("compUrl is:" + compUrl + "\n mo:" + moXmlStr + "\n recStat is:" + this.sendStat);
			/*
			URL url = new URL(compUrl);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);

			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			String moXml = URLEncoder.encode(moXmlStr);
			out.write("mo=" + moXml);
			out.flush();
			//out.close();
			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			BufferedReader bReader = new BufferedReader(in);
			String result = bReader.readLine();
			if(result == null){
				out.write("mo=" + moXml);
				out.flush();
			}
			else{
				while((result = bReader.readLine()) != null){
					if(result.length() > 0){
						System.out.println(result);
						//moSendResult(result);
					}
				}
			}
			out.close();
			in.close();
			*/
		}catch(Exception e){
			myLogger.error(e.toString());
			e.printStackTrace();
		}
	}
	
	private String organizeMo(){
		StringBuffer moBuffer = new StringBuffer();
		String moXmlHead = "<?xml version=\"1.0\"  encoding=\"UTF-8\"?>";
			   moXmlHead += "<pwd_message_list version=\"3.0\">";
			   moXmlHead += "<message_type>SMS_MO</message_type>";
			   moXmlHead += "<data>";

			   String moXmlHeadEnd = "</data></pwd_message_list>";

			   moBuffer.append(moXmlHead);
			   moBuffer.append("<message>");
			   moBuffer.append("<msgid>" + mo.getMsgId() + "</msgid>");
			   moBuffer.append("<mobile>"+ mo.getMobile() + "</mobile>");
			   moBuffer.append("<mobiletype>" + mo.getMobileType() +"</mobiletype>");
			   moBuffer.append("<userType>" + mo.getUserType() +"</userType>");
			   moBuffer.append("<gwid>" + mo.getGwId() + "</gwid>");
			   moBuffer.append("<spcode>" + mo.getSpCode() +"</spcode>");
			   moBuffer.append("<serviceType>"+ mo.getServiceType() +"</serviceType>");
			   moBuffer.append("<linkid>"+ mo.getLinkId() +"</linkid>");
			   moBuffer.append("<momsg>" + mo.getMoMsg() +"</momsg>");
			   moBuffer.append("<feeflag>" + mo.getFeeFlag() +"</feeflag>");
			   moBuffer.append("<cpproductID>" + mo.getCpProductId() +"</cpproductID>");
			   moBuffer.append("</message>");
			   moBuffer.append(moXmlHeadEnd);

			   return moBuffer.toString();
	}
	
	public String MosXmlOrg(){
		String mosXml = "";
		String moXmlHead = "<?xml version=\"1.0\"  encoding=\"UTF-8\"?>";
		   moXmlHead += "<pwd_message_list version=\"3.0\">";
		   moXmlHead += "<message_type>SMS_MO</message_type>";
		   moXmlHead += "<data>";

		   String moXmlHeadEnd = "</data></pwd_message_list>";
		   for(int i = 0;i < this.mosList.size();i++){
			   mosXml += moXml((Mo)this.mosList.get(i));
		   }
		   mosXml = moXmlHead + mosXml + moXmlHeadEnd;

		   //System.out.println(mosXml);
		return mosXml;
	}
	private String moXml(Mo mo){
		StringBuffer moBuffer = new StringBuffer();
		moBuffer.append("<message>");
		   moBuffer.append("<msgid>" + mo.getMsgId() + "</msgid>");
		   moBuffer.append("<mobile>"+ mo.getMobile() + "</mobile>");
		   moBuffer.append("<mobiletype>" + mo.getMobileType() +"</mobiletype>");
		   moBuffer.append("<userType>" + mo.getUserType() +"</userType>");
		   moBuffer.append("<gwid>" + mo.getGwId() + "</gwid>");
		   moBuffer.append("<spcode>" + mo.getSpCode() +"</spcode>");
		   moBuffer.append("<serviceType>"+ mo.getServiceType() +"</serviceType>");
		   moBuffer.append("<linkid>"+ mo.getLinkId() +"</linkid>");
		   moBuffer.append("<momsg>" + mo.getMoMsg() +"</momsg>");
		   moBuffer.append("<feeflag>" + mo.getFeeFlag() +"</feeflag>");
		   moBuffer.append("<cpproductID>" + mo.getCpProductId() +"</cpproductID>");
		   moBuffer.append("</message>");
		  return moBuffer.toString();
	}
	public void setCompanyId(int companyId){
		this.companyId = companyId;
	}
	public void setCompanyMo(Mo mo){
		this.mo = mo;
	}
	public void setMosList(ArrayList mosList){
		this.mosList = mosList;
	}
	public String getStatus(){
		return this.sendStat;
	}

}
