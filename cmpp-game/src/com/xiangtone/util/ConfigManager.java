/**
 *Writter by airmirror
 *2003-12-14 
 */

package com.xiangtone.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	final private static String PFILE = "config.ini";
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
	private static Properties m_props = null;
	/**
	 * ������ܴ��ڵ�Ωһ��һ��ʵ��
	 */
	private static ConfigManager m_instance = null;

	/**
	 * ˽�еĹ����ӣ����Ա�֤����޷�ֱ��ʵ����
	 */
	private ConfigManager() {
		// m_file = new File(PFILE);
		// m_lastModifiedTime = m_file.lastModified();
		// if(m_lastModifiedTime == 0)
		// {
		// System.err.println(PFILE + " file does not exist!");
		// }
		// m_props = new Properties();
		// try
		// {
		// m_props.load(new FileInputStream(PFILE));
		// }
		// catch(Exception e)
		// {
		// e.printStackTrace();
		// }
		init(PFILE);
	}

	public static InputStream getResourceAsStream(String resource) throws IOException {
		InputStream in = null;
		ClassLoader loader = ConfigManager.class.getClassLoader();
		try {
			if (loader != null) {
				in = loader.getResourceAsStream(resource);
			}
			if (in == null) {
				in = ClassLoader.getSystemResourceAsStream(resource);
			}
			if (in == null) {
				File file = new File(System.getProperty("user.dir") + "/" + resource);
				if (file.exists()) {
					in = new FileInputStream(System.getProperty("user.dir") + "/" + resource);
				}
			}
			if (in == null) {
				String filePath = Thread.currentThread().getContextClassLoader().getResource("").toString()
						.replaceAll("file:", "") + resource;
				if (filePath.indexOf(":") == 2)
					filePath = filePath.substring(1, filePath.length());
				File file = new File(filePath);
				if (file.exists()) {
					in = new FileInputStream(filePath);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (in == null)
			throw new IOException("Could not find resource " + resource);
		return in;
	}

	private static void init(String filePath) {

		m_props = new Properties();
		try {
			// filePath = (filePath == null || filePath.length() == 0) ?
			// CONFIG_PATH : filePath;
			// if(filePath.indexOf(":") == 2)
			// filePath = filePath.substring(1, filePath.length());
			// System.out.println(filePath);
			// prop.load(new FileInputStream(filePath));
			m_props.load(getResourceAsStream(filePath));
			// File file = new File(CONFIG_PATH);
			// lastModifyTime = file.lastModified();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getProperty(String key) {
		String result = "";
		if (m_props == null) {
			init(PFILE);
		}
		try {
			// File file = new File(CONFIG_PATH);
			// long tempTime = file.lastModified();
			// if (tempTime > lastModifyTime) {
			// prop.clear();
			// init("");
			// }
			if (m_props.containsKey(key)) {
				result = m_props.getProperty(key);
			}
		} catch (Exception exce) {
			exce.printStackTrace();
		}
		return result;
	}

	public static String getConfigData(String key) {
		return getProperty(key);
	}

	public static String getConfigData(String key, String defaultValue) {
		return getProperty(key).length() == 0 ? defaultValue : getProperty(key);
	}

	/**
	 * ��̬��������
	 * 
	 * @return ����ConfigManager ��ĵ�һʵ��
	 */
	synchronized public static ConfigManager getInstance() {
		if (m_instance == null) {
			m_instance = new ConfigManager();
		}
		return m_instance;
	}

	// /**
	// * ��ȡһ�ض���������
	// *
	// * @param name �����������
	// * @param defaultVal �������Ĭ��ֵ
	// * @return �������ֵ���������ڣ��� Ĭ��ֵ���������ڣ�
	// */
	// final public Object getConfigItem(String name,Object defaultVal)
	// {
	// long newTime = m_file.lastModified();
	// // ��������ļ��Ƿ���������
	// //����������ǳ���Ա�ֶ����޸Ĺ�
	// // ����ǣ����¶�ȡ���ļ�
	//
	// if(newTime == 0)
	// {
	// // �����ļ�������
	// if(m_lastModifiedTime == 0)
	// {
	// System.err.println(PFILE + " file does not exist!");
	// }
	// else
	// {
	// System.err.println(PFILE + " file was deleted!!");
	// }
	// return defaultVal;
	// }
	// else if(newTime > m_lastModifiedTime)
	// {
	// // Get rid of the old properties
	// m_props.clear();
	// try
	// {
	// m_props.load(new FileInputStream(PFILE));
	// }
	// catch(Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// m_lastModifiedTime = newTime;
	// Object val = m_props.getProperty(name);
	// if( val == null )
	// {
	// System.out.println("error:"+defaultVal);
	// return defaultVal;
	// }
	// else
	// {
	// return val;
	// }
	// }
}