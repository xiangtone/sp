package unitecommand;
import java.util.*;
import com.xiangtone.util.*;

public class GameSpCode
{

	public int execMain(String cpn,String itemaction,String ismgid,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet) 
	{
		StringBuffer buffer = new StringBuffer();
		String contactTel =ContactTEL.getContactTel(ismgid);
		buffer.append("������ͨ,");
		buffer.append("�ͷ�"+contactTel);
		buffer.append(",��ַwww.indexweb.cn;��ѯ�Ƽ�ҵ����ظ�����ֻ�����,�˶��ظ�0000");
		cpnSet.add(cpn);
		cpnGet.add(cpn);
	    msgSet.add(buffer.toString());
	    costSet.add("8003");
	    ismgSet.add(ismgid);
		return 1;	
	}
}