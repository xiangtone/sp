package unitecommand;

import java.util.*;
import companycount.*;
import mohandle.*;
import com.xiangtone.util.*;



public class GameError

{
	String retMsg = "";
	String ismgid = "01";
	public int execMain(String cpn,String itemaction,String ismgid,String linkid,Integer cpnType,String msgId,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet,Vector cpntypeSet,Vector msgIdSet) 
											
	{

		StringBuffer buffer = new StringBuffer();

		String contactTel =ContactTEL.getContactTel(ismgid);

		String serverId = "1000";
		//itemaction = toUpCase(itemaction);
		if(itemaction.toUpperCase().equals("CMCCTEST")){
			serverId = "1001";
			//北京，企业名称，服务代码，企业代码，发送MT信息时间
			String sendTime = getCurrentTimeA();
			retMsg="北京，通联天地，10669707，901763，"+sendTime;
		}
		else{
				retMsg = 	"对不起,您发送的指令不正确,请核对后重新发送,本信息免费(不含通讯费)";
		}
		msgAdd(cpn,retMsg,serverId,linkid,msgId,cpnType,cpnSet,cpnGet,msgSet,costSet,ismgSet,linkidSet,cpntypeSet,msgIdSet);
		return 0;	

	}
	public String getCurrentTimeA()
	{
		long ll= System.currentTimeMillis();
	    String sbf = new String("");
	    String sss = new String("yyyy-MM-dd HH:mm:ss");    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(sss);  
	    sbf = sdf.format(new java.util.Date(ll));  
	    String cur_time=sbf.toString();
	    return cur_time;
    }
	public void msgAdd(String cpn,String msg,String serverid,String linkId,String msgId,int cpnType,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet,Vector cpntypeSet,Vector msgIdSet){
		cpnSet.add(cpn);
		cpnGet.add(cpn);
	  msgSet.add(msg);
	  costSet.add(serverid);
		linkidSet.add(linkId);
	  ismgSet.add(ismgid);
	  msgIdSet.add(msgId);
	  cpntypeSet.add(cpnType);
	}

}