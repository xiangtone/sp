using LightDataModel;
using Shotgun.Library;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class report_UnmatchCPTrone : Shotgun.PagePlus.ShotgunPage
{
    protected PageSpliter PS;
    private List<tbl_sp_api_urlItem> urls;
    private List<tbl_spItem> urls_sp;
    private List<tbl_troneItem> urls_td;

    protected void Page_Load(object sender, EventArgs e)
    {
        var l = tbl_mrItem.GetQueries(dBase);
        l.Filter.AndFilters.Add(tbl_mrItem.Fields.cp_id, new int[] { 0, 34 }).NullToValue = 0; ;
        l.PageSize = 200;
        //l.Fields = new string[] { tbl_mrItem.Fields.id, tbl_mrItem.Fields.ori_order, tbl_mrItem.Fields.ori_trone, 
        //        tbl_mrItem.Fields.sp_api_url_id, tbl_mrItem.Fields.sp_id, tbl_mrItem.Fields.create_date, tbl_mrItem.Fields.cp_id };
        l.SortKey.Add(tbl_mrItem.Fields.id, Shotgun.Model.Filter.EM_SortKeyWord.desc);//通过ID进行排序
        PS = new PageSpliter();
        PS.NoTotalPage = false;
        if (!PS.IsFromURL)
            PS.CalcPageCount(l.TotalCount, l.PageSize);
        l.CurrentPage = PS.CurrentPage;
        var mrData = l.GetDataList();
        rpList.DataSource = mrData;

        //mrData[X].sp_api_url_id
        var urlids = mrData.GetFieldValueArray<int>(tbl_mrItem.Fields.sp_api_url_id);
        var url_list = LightDataModel.tbl_sp_api_urlItem.GetQueries(dBase);// 查询器
        url_list.Filter.AndFilters.Add(tbl_sp_api_urlItem.Fields.id, urlids);//条件  WHERE
        url_list.SortKey.Add(tbl_sp_api_urlItem.Fields.id, Shotgun.Model.Filter.EM_SortKeyWord.asc);//通过ID进行排序
        url_list.PageSize = int.MaxValue;
        urls = url_list.GetDataList();
        rpSel.DataSource = urls;

        var urlids_sp = mrData.GetFieldValueArray<int>(tbl_mrItem.Fields.sp_id);
        var url_list_sp = LightDataModel.tbl_spItem.GetQueries(dBase);// 查询器
        url_list_sp.Fields = new string[] { tbl_spItem.Fields.id, "short_name" };//查询的参数
        url_list_sp.Filter.AndFilters.Add(tbl_spItem.Fields.id, urlids_sp);//条件  WHERE
        url_list_sp.SortKey.Add(tbl_spItem.Fields.id, Shotgun.Model.Filter.EM_SortKeyWord.asc);//通过ID进行排序
        url_list_sp.PageSize = int.MaxValue;
        urls_sp = url_list_sp.GetDataList();

        var urlids_TDNM = mrData.GetFieldValueArray<int>(tbl_mrItem.Fields.trone_id);
        var url_list_td_name = LightDataModel.tbl_troneItem.GetQueries(dBase);// 查询器
        //url_list_td_name.Fields = new string[] {"trone_name" };//查询的参数
        url_list_td_name.Filter.AndFilters.Add(tbl_troneItem.Fields.id, urlids_TDNM);//条件  WHERE
        url_list_td_name.SortKey.Add(tbl_troneItem.Fields.id, Shotgun.Model.Filter.EM_SortKeyWord.asc);//通过ID进行排序
        url_list_td_name.PageSize = int.MaxValue;
        urls_td = url_list_td_name.GetDataList();

        //rpList.DataSource = url_list.GetDataList();
        int uid;
        if (int.TryParse(Request["urlId"], out uid))
            l.Filter.AndFilters.Add(tbl_mrItem.Fields.sp_api_url_id, uid);
    }
    public string GetTB(int Id)
    {
        if (Id == 0)
            return "未指定";
        var item = urls.Find(x => x.id == Id);
        if (item == null)
            return Id.ToString();
        return item.name;
    }
    public string GetTB_SP(int Id)
    {
        if (Id == 0)
            return "未指定";
        var item = urls_sp.Find(x => x.id == Id);
        if (item == null)
            return Id.ToString();
        return item.short_name;
    }
    public string GetTB_TD(int Id)
    {
        #region
        switch (Id)
        {
            case -1: return "没有可用通道";
            case -2: return "没有匹配通道";
            case -3: return "发现同步状态";
            case 0: return "未指定";
            default:
                var item = urls_td.Find(x => x.id == Id);
                if (item == null)
                    return Id.ToString();
                return item.trone_name;
        }
        #endregion
        //if (Id == -1)
        //    return "没有可用通道";
        //if (Id == -2)
        //    return "没有匹配通道";
        //if (Id == -3)
        //    return "发现同步状态";
        //if (Id == 0)
        //    return "未指定";
        //var item = urls_td.Find(x => x.id == Id);
        //if (item == null)
        //    return Id.ToString();
        //return item.trone_name;
    }

}