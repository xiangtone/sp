package org.x;
/**

*Copyright 2003 Xiamen Xiangtone Co. Ltd.

*All right reserved.

*/



public class gdxiangtone_game

{

	public static void main(String args[])

	{

		 try

		 {

		  	System.out.print("创世广东短信游戏线程开始....");

		  	VCPServer server = new VCPServer(7900);

          	server.start();

          

          	//Thread.currentThread().sleep(2000);

          

          	System.out.println("游戏定时触发发送线程开始...."); 

         	 GameServer game = new GameServer();

          	new Thread(game).start();

        }

        catch(Exception e)

        {

        	System.out.println("系统出错...");

        	e.printStackTrace();

        	System.exit(0);

        }

	}

}

