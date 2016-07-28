package newmosendhandle;
/*
*用于取得合作的长号码
*/
import java.sql.*;
public class SpCodeHandle{
		public Mysqldb mydb = null;
		private String spcode = "";
		private String game = "";

		public SpCodeHandle(){
			mydb = new Mysqldb();
		}
		public void searchSpcode(){
			String strSql = "select spcode from companygames where gamecode='" + this.game + "'";
			try{
				ResultSet rs = mydb.executeQuery(strSql);
				if(rs.next()){
						this.spcode = rs.getString("spcode");
				}	
				else{
						this.spcode = "10665008";	
				}
			}catch(Exception e){
				e.printStackTrace();	
			}
		}
		
		public void setGame(String game){
			this.game = game;	
		}
		public String getSpcode(){
			return this.spcode;	
		}
		
}