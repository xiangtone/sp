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
	 * 对应于属性文件的文件对象变量
	 */
	private File m_file = null;
	/**
	 * 属性文件的最后修改日期
	 */
	private long m_lastModifiedTime = 0;
	/**
	 * 属性文件所对应的属性对象变量
	 */
	private static Properties m_props = null;
	/**
	 * 本类可能存在的惟一的一个实例
	 */
	private static ConfigManager m_instance = null;

	/**
	 * 私有的构造子，用以保证外界无法直接实例化
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
	 * 静态工厂方法
	 * 
	 * @return 返还ConfigManager 类的单一实例
	 */
	synchronized public static ConfigManager getInstance() {
		if (m_instance == null) {
			m_instance = new ConfigManager();
		}
		return m_instance;
	}

	// /**
	// * 读取一特定的属性项
	// *
	// * @param name 属性项的项名
	// * @param defaultVal 属性项的默认值
	// * @return 属性项的值（如此项存在）， 默认值（如此项不存在）
	// */
	// final public Object getConfigItem(String name,Object defaultVal)
	// {
	// long newTime = m_file.lastModified();
	// // 检查属性文件是否被其他程序
	// //（多数情况是程序员手动）修改过
	// // 如果是，重新读取此文件
	//
	// if(newTime == 0)
	// {
	// // 属性文件不存在
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