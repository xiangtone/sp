/**
 *Writter by airmirror
 *2003-12-14 
 */

package smsunitl.configfile;
import java.util.Properties; 
import java.io.FileInputStream; 
import java.io.File; 

public class CompanysConfigManager 
{ 
	
	/** 
	* �����ļ�ȫ�� 
	*/
	final private static String PFILE = "/home/smsdevp/qwsms/tlconfig.ini";//System.getProperty("user.dir") + File.separator + "compconfig.ini"; 
	/** 
	* ��Ӧ�������ļ����ļ�������� 
	*/ 
	private File m_file = null; 
	/** 
	* �����ļ�������޸����� 
	*/ 
	private long m_lastModifiedTime = 0; 
	/** 
	* �����ļ�����Ӧ�����Զ������ 
	*/ 
	private Properties m_props = null; 
	/** 
	* ������ܴ��ڵ�Ωһ��һ��ʵ�� 
	*/ 
	private static CompanysConfigManager m_instance = null;
	/** 
	* ˽�еĹ����ӣ����Ա�֤����޷�ֱ��ʵ���� 
	*/ 
	private CompanysConfigManager() 
	{ 
		m_file = new File(PFILE); 
		m_lastModifiedTime = m_file.lastModified(); 
		if(m_lastModifiedTime == 0) 
		{ 
			System.err.println(PFILE + " file does not exist!"); 
		} 
		m_props = new Properties(); 
		try 
		{ 
			m_props.load(new FileInputStream(PFILE)); 
		} 
		catch(Exception e) 
		{ 
			e.printStackTrace(); 
		} 
	} 
	/** 
	* ��̬�������� 
	* @return ����ConfigManager ��ĵ�һʵ�� 
	*/ 
	synchronized public static CompanysConfigManager getInstance() 
	{ 
		//System.out.println("path is:" + PFILE);
		if(m_instance ==null)
		{
			 m_instance=new CompanysConfigManager(); 
		}
		return m_instance; 
	} 
     
	/** 
	* ��ȡһ�ض��������� 
	* 
	* @param name ����������� 
	* @param defaultVal �������Ĭ��ֵ 
	* @return �������ֵ���������ڣ��� Ĭ��ֵ���������ڣ� 
	*/ 
	final public Object getConfigItem(String name,Object defaultVal) 
    { 
		long newTime = m_file.lastModified(); 
		// ��������ļ��Ƿ��������� 
		//����������ǳ���Ա�ֶ����޸Ĺ� 
		// ����ǣ����¶�ȡ���ļ�

		if(newTime == 0) 
		{ 
			// �����ļ������� 
			if(m_lastModifiedTime == 0) 
			{ 
				System.err.println(PFILE + " file does not exist!"); 
			} 
			else 
			{ 
				System.err.println(PFILE + " file was deleted!!"); 
			} 
			return defaultVal; 
		} 
		else if(newTime > m_lastModifiedTime) 
		{ 
			// Get rid of the old properties 
			m_props.clear(); 
			try 
			{ 
				m_props.load(new FileInputStream(PFILE)); 
			} 
			catch(Exception e) 
			{ 
				e.printStackTrace(); 
			} 
		} 
		m_lastModifiedTime = newTime; 
		Object val = m_props.getProperty(name); 
		if( val == null ) 
		{ 
			return defaultVal; 
		} 
		else 
		{ 
			return val; 
		} 
	}
} 