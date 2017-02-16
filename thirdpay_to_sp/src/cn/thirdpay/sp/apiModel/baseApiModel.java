
package cn.thirdpay.sp.apiModel;

/**
 * 响应客户端的基础数据
 * 
 * @author Administrator
 *
 */
public class baseApiModel
{
	private int		status;
	private String	message;

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}
