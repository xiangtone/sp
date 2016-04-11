<%@ WebHandler Language="C#" Class="ReMatch" %>

using System;
using System.Web;

public class ReMatch : Shotgun.PagePlus.SimpleHttpHandler<Shotgun.Database.MySqlDBClass>
{
    public override void BeginProcess()
    {
        Ajax = new Shotgun.Library.simpleAjaxResponser();
        int id = int.Parse(Request["id"]);

        var m = LightDataModel.tbl_mrItem.GetRowById(dBase, id);
        LightDataModel.tbl_troneItem trone = null;
        if (!m.IsMatch)
        {
            trone = n8wan.Public.Logical.BaseSPCallback.FillToneId(dBase, m);
            if (trone == null)
            {
                Ajax.message = "匹对失败";
                return;
            }
            //m.trone_id = tid;
            m.IsMatch = true;
            dBase.SaveData(m);
        }
        else
            trone = LightDataModel.tbl_troneItem.GetRowById(dBase, m.trone_id);

        var apiPush = new n8wan.Public.Logical.HTAPIPusher()
        {
            dBase = dBase,
            Trone = trone,
            LogFile = Server.MapPath(string.Format("~/PushLog/{0:yyyyMMdd}.log", DateTime.Today))
        };
        if (apiPush.LoadCPAPI())
        {
            apiPush.PushObject = m;
            if (apiPush.DoPush())
            {
                Ajax.state = Shotgun.Library.emAjaxResponseState.ok;
                return;
            }
        }

        var cp = new n8wan.Public.Logical.AutoMapPush();
        cp.dBase = dBase;
        cp.Trone = trone;
        //cp.UnionUserId = -1;
        cp.LogFile = Server.MapPath(string.Format("~/PushLog/{0:yyyyMMdd}.log", DateTime.Today));

        if (!cp.LoadCPAPI())
            return;

        cp.PushObject = m;
        cp.DoPush();
        Ajax.state = Shotgun.Library.emAjaxResponseState.ok;
    }
}
 