package comsd.commerceware.cmpp;

public class MyTools {

	// 整型-->四个字节
	public byte[] int2Bytes(int n) {
		byte[] bTmp = new byte[4];
		bTmp[0] = (byte) ((n & 0xFF000000) >> 24);
		bTmp[1] = (byte) ((n & 0xFF0000) >> 16);
		bTmp[2] = (byte) ((n & 0xFF00) >> 8);
		bTmp[3] = (byte) ((n & 0xFF));
		return bTmp;
	}

	// 字串str -->str.length个字节
	public byte[] string2Bytes(String str) {
		byte[] bTmp = null;
		try {
			bTmp = str.getBytes();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return bTmp;
	}

	// 短整形 －－> 两个字节
	public byte[] short2Bytes(short n) {
		byte[] bTmp = new byte[2];
		bTmp[0] = (byte) ((n & 0xFF00) >> 8);
		bTmp[1] = (byte) ((n & 0xFF));

		return bTmp;
	}

	// 拷贝字节组,把一个源字节组 拷贝到 目的字节组
	public int strcpy(byte dest[], byte source[], int from) {
		int i;
		for (i = 0; i < source.length; i++)
			dest[i + from] = source[i];
		return i;
	}

	public int strcpy(byte dest[], byte source, int from) {
		int i = 0;
		dest[i + from] = source;
		i++;
		return i;
	}

	public int strcpy(byte dest[], int source, int from) {
		int i;
		byte[] temp = int2Bytes(source);
		for (i = 0; i < temp.length; i++)
			dest[i + from] = temp[i];
		return i;
	}

	public int strcpy(byte dest[], short source, int from) {
		int i;
		byte[] temp = short2Bytes(source);
		for (i = 0; i < temp.length; i++)
			dest[i + from] = temp[i];
		return i;
	}

	public static void main(String args[]) {
		MyTools tools = new MyTools();
		byte[] dest = new byte[20];
		System.out.println(dest.length);
		// byte[] b = tools.int2Bytes(257);
		byte[] b = tools.short2Bytes((short) 922203);

		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
		int len1 = tools.strcpy(dest, b, 0);
		int len2 = tools.strcpy(dest, b, len1);
		for (int i = 0; i < len2; i++) {
			System.out.println(dest[i]);
		}
	}

}
