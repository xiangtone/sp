package unitecommand;
import java.util.*;
import com.xiangtone.util.*;

public class GameHelp{


	public int execMain(String cpn,String itemaction,String ismgid,String linkid,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet,Vector linkidSet) 
	{
		String msg = "";
		String tel = ContactTEL.getContactTel(ismgid);
		String item = itemaction.toUpperCase();
		String _linkid = linkid;
	    if(item.equals("1"))
		{
			msg = "��������,�ʷ�5Ԫ/��,��BW��ͨ����0000ȡ��ҵ�񣬸��������������"+tel;
			
		}
		else if(item.equals("2"))
		{
			msg = "�����Ե,�ʷ�6Ԫ/��,��QY��ͨ����0000ȡ��ҵ�񣬸��������������"+tel;
			
		}
		else if(item.equals("3"))
		{
			msg = "ս������,�ʷ�5Ԫ/��,��SG��ͨ����0000ȡ��ҵ�񣬸��������������"+tel;
			
		}
		else if(item.equals("4"))
		{
			msg = "���鼦��,�ʷ�5Ԫ/��,��TPP��ͨ����0000ȡ��ҵ�񣬸��������������"+tel;

		}
		else if(item.equals("5"))
		{
			msg = "�ְ�ȭ��,�ʷ�0.2Ԫ/��,��JB��ͨ����0000ȡ��ҵ�񣬸��������������"+tel;

		}
		else if(item.equals("6"))
		{
			msg = "��ɫռ��,�ʷ�0.3Ԫ/��,��ZB��ͨ����0000ȡ��ҵ�񣬸��������������"+tel;

		}
		else if(item.equals("7"))
		{
			msg = "�Ҳ��Ҳ�,�ʷ�5Ԫ/��,��WC��ͨ����0000ȡ��ҵ�񣬸��������������"+tel;

		}
		else if(item.equals("8"))
		{
			msg = "����ư�,�ʷ�8Ԫ/��,��BAR��ͨ����0000ȡ��ҵ�񣬸��������������"+tel;

			
		}
		else if(item.equals("9"))
		{
			msg = "����ţ,�ʷ�0.2Ԫ/��,��NBA��ͨ����0000ȡ��ҵ�񣬸��������������"+tel;
			
		}
		else if(item.equals("M"))
		{
			msg="��ͨ��԰(ֱ�ӻظ�����˽�����)��\n6 ��ɫռ��\n7�Ҳ��Ҳ�\n8 ����ư�\n9����ţ";
		}
		else 
		{
			msg="��ͨ��԰(ֱ�ӻظ�����˽�����)��\n1 ��������\n2 �����Ե\n3 ս������\n4 ���鼦��\n5 �ְ�ȭ��\nM ����";
		
		}
		cpnSet.add(cpn);
		cpnGet.add(cpn);
	    msgSet.add(msg);
	    costSet.add("1000");
	    ismgSet.add(ismgid);
	    linkidSet.add(_linkid);
		return 0;	
	}
}
