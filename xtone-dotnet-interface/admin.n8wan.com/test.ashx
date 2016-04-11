<%@ WebHandler Language="C#" Class="test" %>

using System;
using System.Web;
using LightDataModel;

public class test : Shotgun.PagePlus.SimpleHttpHandler<Shotgun.Database.MySqlDBClass>
{

    public override void BeginProcess()
    {
        System.Diagnostics.Stopwatch st = new System.Diagnostics.Stopwatch();
        st.Start();
        Console.SetOut(Response.Output);
        var l = tbl_phone_locateItem.GetRowByMobile(dBase, 1357083);
        var num = new int[] { 1357083, 1301234, 1331086, 1351044, 1301532, 1581542, 1301234, 1331086, 1351044, 1301532, 1581542, 1301234, 1331086, 1351044, 1301532, 1581542, 1301234, 1331086, 1351044, 1301532, 1581542 };
        Response.ContentType = "text/plain";
        foreach (var n in num)
        {
            var item = tbl_phone_locateItem.GetRowByMobile(dBase, n);
            Console.WriteLine("{2:#,###}ms {0}:{1}", n, item.name, st.ElapsedMilliseconds);
        }
    }
}