package smsunitl.configfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	final private static String PFILE = "tlconfig.ini";
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
			m_props.load(getResourceAsStream(filePath));
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

	public static void main(String[] args) {
		System.out.println(ConfigManager.getInstance().getConfigData("jhhdlost"));
	}
}