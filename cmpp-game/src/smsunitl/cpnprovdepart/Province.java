package smsunitl.cpnprovdepart;

public class Province {
/*
 * id  miscid  pressoorder_url  spid  province 
 */
	private int id;
	private String miscId;
	private String postCode;
	public void setId(int id){
		this.id = id;
	}
	public void setMiscId(String miscId){
		this.miscId = miscId;
	}
	public void setPostCode(String postCode){
		this.postCode = postCode;	
	}
	public int getId(){
		return this.id;
	}
	public String getMiscId(){
		return this.miscId;
	}
	public String getPostCode(){
		return this.postCode;	
	}
}
