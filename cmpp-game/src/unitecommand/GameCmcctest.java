package unitecommand;

import java.util.*;

import com.xiangtone.util.*;
import java.text.SimpleDateFormat;


public class GameCmcctest

{

	public int execMain(String cpn,String itemaction,String ismgid,String linkid,Integer cpnType,String msgId,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet,Vector cpntypeSet,Vector msgIdSet) 

	{

		StringBuffer buffer = new StringBuffer();

		String contactTel =ContactTEL.getContactTel(ismgid);

		String error_serverid = "1003";

		//error_serverid = (String)ConfigManager.getInstance().getConfigItem("error_serverid","error_serverid not found!");

		//buffer.append("�Բ���,�����͵�ָ���ȷ,��˶Ժ����·���,��ѯָ��ظ�9999,��ַwww.xiangtone.com,");
		buffer.append("����,��������,");
		buffer.append("10665008,916006,"+getCurrentTimeA());
		
		System.out.println("error_servid:" + error_serverid);
		cpnSet.add(cpn);

		cpnGet.add(cpn);

	    msgSet.add(buffer.toString());

	    costSet.add(error_serverid);
			linkidSet.add(linkid);
	    ismgSet.add(ismgid);
	    msgIdSet.add(msgId);
	    cpntypeSet.add(cpnType);

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

}