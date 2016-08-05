package org.x;

/**
*Copyright 2003 Xiamen Xiangtone Co. Ltd.
*All right reserved.
*/
import java.io.*;
import java.sql.*;

/**
*
*
*/
public interface CMPPSend {
	/**
	 * send text sms
	 *
	 */
	public void sendTextSMS();

	/**
	 * send binary picture sms
	 *
	 */

	public void sendBinaryPicSMS();

	/**
	 * send binary ring sms
	 *
	 */

	public void sendBinaryRingSMS();

	/**
	 * send smc sms
	 *
	 */
	public void sendTextSMCSMS();

}