package comsd.commerceware.cmpp;

import java.net.Socket;

public class ConnDesc {
	public ConnDesc() {
	}

	public Socket sock; // 连接
	public int seq; // 序号
	public int status;
}
