package org.xtone.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileTool {
	static Logger log = Logger.getLogger(FileTool.class); 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		readTxt("f:/phone.txt");
//		try {
//			readTxtByUrl("http://localhost:8080/UnicomeRed/phone.txt");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		StringBuilder sb = new StringBuilder();
		String content = sb.toString();
		try {
			writeTxt(content, "e:/test.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 读取磁盘文件
	 * @param path
	 * @return
	 */
	public static List<String> readTxt(String path) {
		List<String> list=new ArrayList<String>();
		try {
			String encoding = "GBK";
			File file = new File(path);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTXT = null;
				while ((lineTXT = bufferedReader.readLine()) != null) {
					String phone = lineTXT.trim();
//					System.out.println(phone);
					list.add(phone);
				}
				read.close();
			} else {
				log.error("找不到指定的文件！");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 读取网络文本文件
	 * @param urlStr http://localhost:8080/web/phone.txt
	 * @return List<String>
	 * @throws Exception
	 * @author cwq
	 */
	public static List<String> readTxtByUrl(String urlStr) throws Exception{
		List<String> list=new ArrayList<String>();
		String encoding = "GBK";
		URL url = new URL(urlStr);
		HttpURLConnection conn;
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		int code = conn.getResponseCode();
		if(200==code){
			InputStreamReader read =new InputStreamReader(conn.getInputStream(),encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTXT = null;
			while ((lineTXT = bufferedReader.readLine()) != null) {
				String phone = lineTXT.trim();
				list.add(phone);
			}
			read.close();
		}else{
			log.error("http error code: " + code);
		}
		return list;
	}
	/**
	 * 写入文件
	 * @param content
	 * @param path
	 * @throws Exception
	 * @author cwq
	 */
	public static void writeTxt(String content,String path) throws Exception{
		
		OutputStreamWriter out=null;
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		FileOutputStream fileOutputStream=new FileOutputStream(file);
		out= new OutputStreamWriter(fileOutputStream,"GBK");
		out.write(content);
		out.close();

	}
}
