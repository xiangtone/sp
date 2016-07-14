<%@ WebHandler Language="C#" Class="jj83" %>

using System;
using System.Web;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json.Linq;
using System.Text.RegularExpressions;

/// <summary>
/// 微品-小额
/// </summary>
public class jj83 : sdk_Request.Logical.APIRequestGet
{
    protected override sdk_Request.Model.SP_RESULT GetSpCmd()
    {
        string url = "http://test.hxbapi.vpclub.cn/MicroPayment/PayUrl?";

        Dictionary<string, string> ptr = new Dictionary<string, string>();
        ptr["merchantid"] = PayModel.channelid;
        ptr["username"] = PayModel.appid;
        ptr["mobilephone"] = OrderInfo.mobile;
        ptr["amount"] = PayModel.paycode;

        string data = string.Empty;
        string md5Val = string.Empty;
        foreach (var kv in ptr)
        {
            data += string.Format("{0}={1}&", kv.Key, kv.Value);
            md5Val += "|" + kv.Value;
        }
        md5Val = md5Val.Substring(1);
        data += "sign=" + MD5(AesEncrypt(md5Val, "hjgdgmJDLJALDFJL"));

        url += data;

        string html = GetHTML(url);

        var jobj = JObject.Parse(html);
        var jval = jobj["code"];
        if (!jval.Value<string>().Equals("0000"))
        {
            jval = jobj["message"];
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL, jval == null ? null : jval.ToString());
            return null;
        }

        jval = jobj["payurl"];
        url = jval.Value<string>();

        this.Cookies = new System.Net.CookieContainer();
        html = GetHTML(url);



        var mc = Regex.Match(html, "订单号：(\\d+)");

        if (!mc.Success)
        {
            SetError(sdk_Request.Logical.API_ERROR.GET_CMD_FAIL);
            return null;
        }
        
        OrderInfo.spLinkId = mc.Groups[1].Value;

        mc = Regex.Match(html, @"pageFormXE[\s\S]+?</form>", RegexOptions.IgnoreCase);
        html = mc.Value;
        var mcs = Regex.Matches(html, "<input.+?name=\"(.+?)\".+?value=\"(.+?)\"", RegexOptions.IgnoreCase);
        data = string.Empty;
        foreach (Match m in mcs)
        {
            data += string.Format("&{0}={1}", m.Groups[1].Value, m.Groups[2].Value);
        }
        data = data.Substring(1);
        mc = Regex.Match(html, @"action.+?([\w/]+\.xhtml)");
        var uri = new Uri(new Uri(url), mc.Groups[1].Value);
        html = PostHTML(uri.ToString(), data);


        return new sdk_Request.Model.SP_RESULT();
    }


    /// <summary>
    ///  AES 加密
    /// </summary>
    /// <param name="str"></param>
    /// <param name="key"></param>
    /// <returns></returns>
    public static string AesEncrypt(string str, string key)
    {
        if (string.IsNullOrEmpty(str)) return null;
        Byte[] toEncryptArray = Encoding.UTF8.GetBytes(str);

        System.Security.Cryptography.RijndaelManaged rm = new System.Security.Cryptography.RijndaelManaged
        {
            Key = Encoding.UTF8.GetBytes(key),
            Mode = System.Security.Cryptography.CipherMode.ECB,
            Padding = System.Security.Cryptography.PaddingMode.PKCS7
        };

        System.Security.Cryptography.ICryptoTransform cTransform = rm.CreateEncryptor();
        Byte[] resultArray = cTransform.TransformFinalBlock(toEncryptArray, 0, toEncryptArray.Length);

        return Convert.ToBase64String(resultArray, 0, resultArray.Length);
    }

    /// <summary>
    ///  AES 解密
    /// </summary>
    /// <param name="str"></param>
    /// <param name="key"></param>
    /// <returns></returns>
    public static string AesDecrypt(string str, string key)
    {
        if (string.IsNullOrEmpty(str)) return null;
        Byte[] toEncryptArray = Convert.FromBase64String(str);

        System.Security.Cryptography.RijndaelManaged rm = new System.Security.Cryptography.RijndaelManaged
        {
            Key = Encoding.UTF8.GetBytes(key),
            Mode = System.Security.Cryptography.CipherMode.ECB,
            Padding = System.Security.Cryptography.PaddingMode.PKCS7
        };

        System.Security.Cryptography.ICryptoTransform cTransform = rm.CreateDecryptor();
        Byte[] resultArray = cTransform.TransformFinalBlock(toEncryptArray, 0, toEncryptArray.Length);

        return Encoding.UTF8.GetString(resultArray);
    }



    private static string MD5(string source)
    {
        using (var md5 = new System.Security.Cryptography.MD5CryptoServiceProvider())
        {
            byte[] data = Encoding.Default.GetBytes(source);
            byte[] md5data = md5.ComputeHash(data);

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < md5data.Length; i++)
            {
                result.Append(md5data[i].ToString("x").PadLeft(2, '0'));
            }
            return result.ToString();
        }
    }

}