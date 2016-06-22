<%@ WebHandler Language="C#" Class="cdcsd_music" %>

using System;
using System.Web;

public class cdcsd_music : n8wan.Public.Logical.BaseSPCallback
{
    string[] _data;
    protected override bool OnInit()
    {

        //MSISDN=18371144024&revdate=20160530132602817&rescode=000000&resdesc=[UMS]操作成功
        //&msg=CPBY@004328451696718790@szhaot413105@600967020000006196@sdk@1000@szhaot413105
        //&sign=2534529579361f49fcaecdbfb48253ba&cpid=szhaot'


        var msg = Request["msg"];
        if (string.IsNullOrEmpty(msg))
            return false;
        this._data = msg.Split(new char[] { '@' });


        return base.OnInit();
    }

    public override string GetParamValue(string Field)
    {

        //0    1                  2            3                  4   5    6
        //CPBY@004328451696718790@szhaot413105@600967020000006196@sdk@1000@szhaot413105
        switch (Field.ToLower())
        {
            case "price":
                return _data.Length >= 4 ? _data[4] : string.Empty;
            case "code":
                return _data.Length >= 3 ? _data[3] : string.Empty;
            case "cpparam":
                return _data.Length >= 2 ? _data[2] : string.Empty;
        }

        return base.GetParamValue(Field);
    }
    protected override void WriteError(string msg)
    {
        Response.Write("OK");
    }

    protected override void WriteSuccess()
    {
        Response.Write("OK");
    }
}