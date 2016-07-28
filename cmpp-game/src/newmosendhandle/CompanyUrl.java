package newmosendhandle;
/*
 * 用于取得各个合作方的上行的Url
 */
import java.io.InputStream;
import java.io.*;
import java.util.Properties;

public class CompanyUrl {
	String configFile;
	String companyId;
	String companyUrl;
	public CompanyUrl(String configFile,String companyId){
		this.configFile = configFile;
		this.companyId = companyId;
		//System.out.println("the company id is:" + companyId);
		System.out.println("the urlconfig is:" + this.configFile);
	}
	public boolean loadParam(){
		Properties props;
		try{
			//System.out.println(new File(this.configFile).getAbsolutePath());
			InputStream ins = getClass().getResourceAsStream(this.configFile);
			if(ins != null){
				props = new Properties();
				props.load(ins);
			}
			else{
				return false;
			}
			companyUrl = props.getProperty(this.companyId,"paramName").trim();
		}catch(Exception e){
			e.printStackTrace();
    		return false;
		}
		return true;
	}
	public String getCompanyUrl(){
		return this.companyUrl;
	}
}
