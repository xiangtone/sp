package mohandle;
/*
 * 用于取得各个合作方的上行的Url
 */
import java.io.InputStream;
import java.util.Properties;

public class CompanyUrl {
	String configFile;
	String companyId;
	String companyUrl;
	public CompanyUrl(String configFile,String companyId){
		this.configFile = configFile;
		this.companyId = companyId;
	}
	public boolean loadParam(){
		Properties props;
		try{
			InputStream ins = getClass().getResourceAsStream(configFile);
			if(ins != null){
				props = new Properties();
				props.load(ins);
			}
			else{
				System.out.println("Can not read the properties file:" + configFile);
				return false;
			}
			companyUrl = props.getProperty(this.companyId,"paramName").trim();

		}catch(Exception e){
			System.out.println("read profile.ini file error!"+e);
    		return false;
		}
		return true;
	}
	public String getCompanyUrl(){
		return this.companyUrl;
	}
}
