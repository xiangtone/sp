package order;

import java.sql.*;

import order.util.*;

public class OrderOperate {

	String order_str;

	String phone;

	String spicpid;

	String feecode;

	String servername;

	String spcode;

	int serverid;

	int infofee;

	int order_state;

	Mysqldb mydb;

	int action; 

	public OrderOperate(String _order_str){

		this.order_str = _order_str;

		mydb = new Mysqldb();

	}

	public int Order_Str(){

		int phone_state;//用于返回用户定购状态

		String temp_order;

		temp_order = get_phone();

		temp_order = get_spicpid(temp_order);

		temp_order = get_serviceid(temp_order);

		get_action(temp_order);

		if(action == 1){

			new_order();//开通新业务

		}

		if(action == 2){

			stop_order();//停止业务

		}

		if(action == 3){

			restart_order();

		}

		if(action == 4){

			pause_order();

		}
		action = 0;
		order_state = 0;
		return order_state;

	}

	private boolean check_user(){

		boolean order_flag = false;

		String sql_str = "select id from sms_month where cpn ='" + phone + "' and feecode = '" + feecode +"'";

		try{

			ResultSet rs = mydb.executeQuery(sql_str);

			if(rs.next()){

				order_flag = true;

			}

		}catch(Exception e){

			System.out.println(e.toString());

		}

		return order_flag;

	}

	private void new_order(){

		if(!check_user()){

			//用于检查用户是否存在

				get_servername();

				get_fee();

				Insert_Order();

				//phone_state = 

				order_state = SUCESS;

		}

		else{

			order_state = ORDERED;

		}

	}

	private void stop_order(){//停止定购关系

		if(check_user()){

			get_servername();

			get_fee();

			Delete_User();

			order_state = SUCESS;

		}

		else{

			order_state = UNORDER;

		}

	}

	private void restart_order(){

		if(check_user()){

			String sql_str = "update sms_month set flag = '0' where cpn = '" + phone + "' and feecode = '" + feecode + "'";

			try{

				mydb.executeUpdate(sql_str);

			}catch(Exception e){

				System.out.println(e.toString());

			}

			order_state = SUCESS;

		}

			order_state = UNSERVERID;

	}

	private void pause_order(){

		if(check_user()){

			String sql_str = "update sms_month set flag = 1 where cpn = '" + phone + "' and feecode = '" + feecode + "'";

			try{

				mydb.executeUpdate(sql_str);

				order_state = SUCESS;

			}catch(Exception e){

				System.out.println(e.toString());

			}

			

		}

		else{

			order_state = UNSERVERID;

		}

	}

	private void Delete_User(){

		add_monthqx();//添加到取消用户中去

		String sql_str = "delete from sms_month where cpn = '" + phone + "' and feecode = '" + feecode + "'";

		try{

			mydb.executeUpdate(sql_str);

		}catch(Exception e){

			System.out.println(e.toString());

		}

	}

	private void add_monthqx(){

		Calendar_Machine gm = new Calendar_Machine();

		String cancle_time = gm.Get_sendtime();

		String sql_str1 = "select order_time from sms_month where cpn = '" + phone + "' and feecode = '" + feecode + "'";

		String order_time = "";

		try{

			ResultSet rs = mydb.executeQuery(sql_str1);

			if(rs.next()){

				order_time = rs.getString("order_time");

			}

			String sql_str = "insert into sms_month_qx(cpn,serverid,order_time,qx_time) values('" + phone + "','" + serverid +"','" + order_time + "','" + cancle_time +"')";

			mydb.executeUpdate(sql_str);

		}catch(Exception e){

			System.out.println(e.toString());

		}

	}

	private String get_phone(){

		//System.out.println(order_str);

		int phone_index;

		int space_index;

		String phone_str;

		space_index = order_str.indexOf(" ");

		phone_str = order_str.substring(0,space_index);

		phone_index = phone_str.indexOf("=");

		phone = (phone_str.substring(phone_index+1,space_index)).trim();

		System.out.println("left:"+(order_str.substring(space_index)).trim());

		return (order_str.substring(space_index)).trim();

	}

	private String get_spicpid(String _tempspicpid){

		int spicp_index;

		int space_index;

		String spicpid_str;

		space_index = _tempspicpid.indexOf(" ");

		spicpid_str = _tempspicpid.substring(0,space_index);

		spicp_index = spicpid_str.indexOf("=");

		//System.out.println("spicpid:" + (spicpid_str.substring(spicp_index+1)).trim());

		spicpid = (spicpid_str.substring(spicp_index+1)).trim();

		//System.out.println("::::" + spicpid);

		return (_tempspicpid.substring(space_index)).trim();	

	}

	private String get_serviceid(String _serviced_str){

		int service_index;

		int space_index;

		String service_str;

		space_index = _serviced_str.indexOf(" ");

		service_str = _serviced_str.substring(0,space_index);

		service_index = _serviced_str.indexOf("=");

		feecode = (service_str.substring(service_index+1)).trim();

		return (_serviced_str.substring(space_index)).trim();

	}

	private void  get_action(String _temp_order){

		int order_index;

		int space_index;

		//int action;

		System.out.println("action str:" + _temp_order);

		String service_str;

		//space_index = _temp_order.indexOf(" ");

		order_index = _temp_order.indexOf("=");

		action = Integer.parseInt(_temp_order.substring(order_index+1));

	}

	private void get_servername(){//取得servername

		String sql_str = "select serverid,servername,infofee,spcode from sms_cost where feecode_iod ='"+feecode+"'";

		try{

			ResultSet rs = mydb.executeQuery(sql_str);

			if(rs.next()){

				servername = rs.getString("servername");

				spcode = rs.getString("spcode");

				serverid = rs.getInt("serverid");

				infofee = rs.getInt("infofee");

			}

		}catch(Exception e){

			System.out.println(e.toString());

		}

	}

	private void get_fee(){//取得包月应用的费用

		String sql_str = "select infofee from sms_cost where feecode_iod ='"+feecode+"'";

		try{

			ResultSet rs = mydb.executeQuery(sql_str);

			if(rs.next()){

				infofee = rs.getInt("infofee");

			}

		}catch(Exception e){

			System.out.println(e.toString());

		}

	}

	private void Insert_Order(){

		Calendar_Machine gm = new Calendar_Machine();

		String order_time = gm.Get_sendtime();

		String sql_str = "insert into sms_month(spcode,cpn,serverid,servername,feecode,infofee,begin_time,flag) values('" + spcode + "','" + phone + "','" + serverid + "','" + servername +"','" + feecode + "','" + infofee +"','" + order_time + "','0')";

		try{

			mydb.executeUpdate(sql_str);

		}catch(Exception e){

			System.out.println(e.toString());

		}

	}

	public void prtall(){

		System.out.println("phone:" + phone);

		System.out.println("spicpid:" + spicpid);

		System.out.println("service:" + feecode);

		System.out.println("action:" + action);

	}

	public static void main(String[] args){

		String str = "phone=1234565 spicpid=902345 service=-xljt action=1";

		OrderOperate oo = new OrderOperate(str);

		oo.Order_Str();

		oo.prtall();

	}

	static final int SUCESS = 0;//成功

	static final int ORDERED = 1;//MISC同步开通服务,但SP端已存在订购关系,且状态为开通

	static final int UNORDER = 2;//MISC同步停止服务, 但SP端不存在订购关系

	static final int UNSERVERID = 4;//错误的serverid

}

