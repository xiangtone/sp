package unitecommand;

import java.util.*;

import com.xiangtone.util.*;



public class SCGameHelp

{

	public int execMain(String cpn,String itemaction,String ismgid,String linkid,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet) 

	{

		StringBuffer buffer = new StringBuffer();

		String contactTel =ContactTEL.getContactTel(ismgid);

		String error_serverid = "1004";

		//error_serverid = (String)ConfigManager.getInstance().getConfigItem("error_serverid","error_serverid not found!");

		//buffer.append("�Բ���,�����͵�ָ���ȷ,��˶Ժ����·���,��ѯָ��ظ�9999,��ַwww.xiangtone.com,");
		buffer.append("�Բ���,�����͵�ָ���ȷ,��˶Ժ����·���,��ַwww.5008.com.cn ��");
		buffer.append("�ͷ�:95105508");
	
		System.out.println("error_servid:" + error_serverid);
		cpnSet.add(cpn);

		cpnGet.add(cpn);

	    msgSet.add(buffer.toString());

	    costSet.add(error_serverid);
			linkidSet.add(linkid);
	    ismgSet.add(ismgid);

		return 0;	

	}

}