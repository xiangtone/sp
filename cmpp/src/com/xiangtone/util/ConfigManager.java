/**
 *Writter by airmirror
 *2003-12-14 
 *单实例却有静态方法
 */

package com.xiangtone.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigManager {

	final private static Logger LOG = Logger.getLogger(ConfigManager.class);

	final private static String PFILE = "config.ini";
	/**
	 * 对应于属性文件的文件对象变量
	 */
	private File mFile = null;
	/**
	 * 属性文件的最后修改日期
	 */
	private long mLastModifiedTime = 0;
	/**
	 * 属性文件所对应的属性对象变量
	 */
	private static Properties mProps = null;
	/**
	 * 本类可能存在的惟一的一个实例
	 */
	private static ConfigManager mInstance = null;

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
				LOG.debug("load config from loader.getResourceAsStream:" + resource);
			}
			if (in == null) {
				in = ClassLoader.getSystemResourceAsStream(resource);
				LOG.debug("load config from ClassLoader.getSystemResourceAsStream:" + resource);
			}
			if (in == null) {
				File file = new File(System.getProperty("user.dir") + "/" + resource);
				if (file.exists()) {
					in = new FileInputStream(System.getProperty("user.dir") + "/" + resource);
				}
				LOG.debug(
						"load config from System.getProperty(\"user.dir\"):" + System.getProperty("user.dir") + "/" + resource);
				// ClassLoader.getSystemResourceAsStream(System.getProperty("user.dir")+"/"+resource);
			}
			if (in == null) {
				String filePath = Thread.currentThread().getContextClassLoader().getResource("").toString().replaceAll("file:",
						"") + resource;
				if (filePath.indexOf(":") == 2)
					filePath = filePath.substring(1, filePath.length());
				File file = new File(filePath);
				if (file.exists()) {
					in = new FileInputStream(filePath);
				}
				LOG.debug("load config from filePath:" + filePath);
				// ClassLoader.getSystemResourceAsStream(System.getProperty("user.dir")+"/"+resource);
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

		mProps = new Properties();
		try {
			mProps.load(getResourceAsStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getProperty(String key) {
		String result = "";
		if (mProps == null) {
			init(PFILE);
		}
		try {
			// File file = new File(CONFIG_PATH);
			// long tempTime = file.lastModified();
			// if (tempTime > lastModifyTime) {
			// prop.clear();
			// init("");
			// }
			if (mProps.containsKey(key)) {
				result = mProps.getProperty(key);
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
		if (mInstance == null) {
			mInstance = new ConfigManager();
		}
		return mInstance;
	}

}

