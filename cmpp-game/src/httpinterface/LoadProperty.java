package httpinterface;
/**
 * @author tang
 * @since 07-03-01
 * 目的：装载不同的feecode所对应的不同的url
 */
import java.io.*;

import java.util.*;

import java.util.Properties;

import java.util.Enumeration;
public class LoadProperty {
	Properties props ;
	File f;
	public void loadparam(){
		try{
			f = new File("http.properties");
			FileInputStream ins = new  FileInputStream(f);
			if(ins != null){
				props = new Properties();
				props.load(ins);
				//System.out.print(props);
			}

			//InputStream ins = getClass().getResourceAsStream("http.properties");

		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	public String getUrl(String gameid){
		String str_url = "";
		str_url = props.getProperty(gameid, "noparam");
		return str_url;
	}
	public static void main(String[] args){
		LoadProperty lp = new LoadProperty();
		lp.loadparam();
		System.out.println("url is:" +lp.getUrl("js"));
	}
}

