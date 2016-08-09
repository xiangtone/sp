package org.xtone.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * 配置文件读取工具类
 * @author cwq
 *
 */
public class ConfigTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getValue("host"));

	}
	private static String ConfigName = "config";
	
	public ConfigTool() {
		super();
		add(ConfigName);
	}
	private static Map<String,Properties> map = new HashMap<String, Properties>();
	
	private static Properties loadProperties(String name){
		System.out.println("================ loadProperties " + name + " ================");
		Properties p = new Properties();
		try {
			InputStream is = ConfigTool.class.getClassLoader().getResourceAsStream(name);
			p.load(is);
			is.close();
		}catch (Exception e) {
			System.out.println(">>> loadProperties file error="+name);
			e.printStackTrace();
		}
		return p;
	}
	
	public static void add(String name){
		if(map.containsKey(name)){
			return ;
		}
		map.put(name, loadProperties(name+".properties"));
	}
	public static String getValue(String key){
		return getValue(ConfigName,key);
	}
	public static String getValue(String name,String key){
		return getValue(name, key, "");
	}
	public static String getValue(String name,String key,String def){
		Properties p = map.get(name);
		if(p==null){
			add(name);
			p = map.get(name);
		}
		if(p==null){
			return def;
		}
		return p.getProperty(key, def);
	}
}
