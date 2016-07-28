package companymocpnprovdept;

public class MoProvince {
	
	private String miscid="";
	private String postcode="";
	private String city="";
	private String province="";
	private String mobile="";
	private String cpn="";
	private int id;
	private String tableName="";
	public void setMiscid(String miscid){
		this.miscid = miscid;
	}
	public void setPostCode(String postcode){
		this.postcode = postcode;
	}
	public void setCity(String city){
		this.city = city;
	}
	public void setProvince(String province){
		this.province = province;
	}
	public void setMobile(String mobile){
		this.mobile = mobile.substring(0,7);
	}
	public void setCpn(String cpn){
		this.cpn = cpn;
		//this.mobile = cpn.substring(0,7);
	}
	public void setId(int id){
		this.id = id;
	}
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public String getMiscId(){
		return this.miscid;
	}
	public String getPostCode(){
		return this.postcode;
	}
	public String getCity(){
		return this.city;
	}
	public String getProvince(){
		return this.province;
	}
	public String getMobile(){
		return this.mobile;
	}
	public String getCpn(){
		return this.cpn;
	}
	public int getId(){
		return this.id;
	}
	public String tableName(){
		return this.tableName;
	}
}
