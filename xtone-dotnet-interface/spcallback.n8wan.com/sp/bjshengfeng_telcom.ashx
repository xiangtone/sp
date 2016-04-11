<%@ WebHandler Language="C#" Class="bjshengfeng_telcom" %>

using System;
using System.Web;
using System.Text;
using System.Security.Cryptography;
using System.IO;

public class bjshengfeng_telcom : n8wan.Public.Logical.BaseSPCallback
{
    const string KEY = "d19e28f480344079";
    const string IV = @"L+\~f4,Ir)b$=pkf";
    string[] spData;
    public string AESDecrypt(string encryptStr)
    {
        //var aes = new AEScls();
        //return aes.AESDecrypt(encryptStr);
        byte[] bKey = Encoding.UTF8.GetBytes(KEY);
        byte[] bIV = Encoding.UTF8.GetBytes(IV);
        byte[] byteArray = Convert.FromBase64String(encryptStr);

        string decrypt = null;
        Rijndael aes = Rijndael.Create();
        MemoryStream mStream = null;
        try
        {
            mStream = new MemoryStream();
            using (CryptoStream cStream = new CryptoStream(mStream, aes.CreateDecryptor(bKey, bIV), CryptoStreamMode.Write))
            {
                cStream.Write(byteArray, 0, byteArray.Length);
                cStream.FlushFinalBlock();
                decrypt = Encoding.UTF8.GetString(mStream.ToArray());
            }
        }
        catch { }
        finally
        {
            if (mStream != null)
                mStream.Dispose();
            aes.Dispose();
        }

        return decrypt;
    }

    protected override bool OnInit()
    {
        var pims = Request["Prim"];
        if (string.IsNullOrEmpty(pims))
            return false;
        var data = AESDecrypt(pims);
        spData = data.Split(new char[] { '~' });

        return true;
    }

    public override string GetParamValue(string Field)
    {
        if (spData == null || spData.Length == 0)
            return null;
        switch (Field.ToLower())
        {
            case "mchno": return spData[0];
            case "mobile": return spData[3];
            case "fee": return spData[2];
            case "orderid": return spData[4];
            case "mobiletype": return spData[1];
        }
        return base.GetParamValue(Field);
    }


}