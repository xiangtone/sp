package unitecommand;
import java.util.*;
import com.xiangtone.util.*;

public class GameSpCode
{

	public int execMain(String cpn,String itemaction,String ismgid,Vector cpnSet,Vector cpnGet,Vector msgSet,Vector costSet,Vector ismgSet) 
	{
		StringBuffer buffer = new StringBuffer();
		String contactTel =ContactTEL.getContactTel(ismgid);
		buffer.append("厦门翔通,");
		buffer.append("客服"+contactTel);
		buffer.append(",网址www.indexweb.cn;查询推荐业务请回复你的手机号码,退订回复0000");
		cpnSet.add(cpn);
		cpnGet.add(cpn);
	    msgSet.add(buffer.toString());
	    costSet.add("8003");
	    ismgSet.add(ismgid);
		return 1;	
	}
}