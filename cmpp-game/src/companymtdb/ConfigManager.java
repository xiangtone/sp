/**
 *Writter by airmirror
 *2003-12-14 
 */


package companymtdb;
import java.util.Properties; 
import java.io.FileInputStream; 
import java.io.File; 

public class ConfigManager 
{ 
	
	/** 
	* �����ļ�ȫ�� 
	*/
	final private static String PFILE = System.getProperty("user.dir") + File.separator + "config.ini"; 
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
	private static ConfigManager m_instance = null;
	/** 
	* ˽�еĹ����ӣ����Ա�֤����޷�ֱ��ʵ���� 
	*/ 
	private ConfigManager() 
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
	synchronized public static ConfigManager getInstance() 
	{ 
		if(m_instance ==null)
		{
			 m_instance=new ConfigManager(); 
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