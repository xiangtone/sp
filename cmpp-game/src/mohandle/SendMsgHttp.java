//package com.bhb.sms.busyness.weifu.http30;
package mohandle;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
//import com.bhb.sms.busyness.weifu.util.Log;


/**
 * 发送信息
 * <p>Title: tv互动</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: PROVIDER</p>
 * @author fandy
 * @version: 1.0
 */
public class SendMsgHttp {

    private String url;
    private String method;
    private Hashtable heads;
    private byte[] body;
    private String resEncode;
    private int respCode;
    private byte[] respData;

    public SendMsgHttp(String url) {
        this.url = url;
        this.heads = new Hashtable();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setHead(String name, String value) {
        heads.put(name, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setBody(String body) {
        try {
            setBody(body, null);
        }
        catch (Exception e) {}
    }

    public void setBody(String body, String encode) throws UnsupportedEncodingException {
        if (encode != null && !encode.equals("")) {
            this.body = body.getBytes(encode);
        }
        else {
            this.body = body.getBytes();
        }
    }

    public String getContentEncoding() {
        return this.resEncode;
    }

    public int getContentLength() {
        return this.respData.length;
    }

    public int getResponseCode() {
        return this.respCode;
    }

    public byte[] getResponseData() {
        return this.respData;
    }

    public String getResponseString() {
        String ret = null;
        if (this.resEncode == null) {
            ret = new String(this.respData);
        }
        else {
            try {
                ret = new String(this.respData, this.resEncode);
            }
            catch (Exception e) {}
        }
        return ret;
    }

    public String exec() throws IOException {
        String theReturn = "-1";
        HttpURLConnection conn = null;
        OutputStream os = null;
        InputStream is = null;

        URL urlObject = new URL(url);

        conn = (HttpURLConnection) (urlObject.openConnection());


        if (this.method != null) {
            conn.setRequestMethod(this.method);
        }

        Iterator iter = this.heads.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            conn.setRequestProperty( (String) entry.getKey(), (String) entry.getValue());
        }

        if (body != null) {
            conn.setDoOutput(true);
            os = conn.getOutputStream();
            os.write(body);
            os.flush();
        }

        this.respCode = conn.getResponseCode();
        this.resEncode = conn.getContentEncoding();


        is = conn.getInputStream();

        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        theReturn = bf.readLine();
				//System.out.println("the return value is:" + theReturn);

        try {
            os.close();
        }
        catch (Exception e) {
            theReturn = "-3";
        }
        try {
            is.close();
        }
        catch (Exception e) {
            theReturn = "-4";
        }
        try {
            conn.disconnect();
        }
        catch (Exception e) {
            theReturn = "-5";
        }

        return theReturn;
    }

}
