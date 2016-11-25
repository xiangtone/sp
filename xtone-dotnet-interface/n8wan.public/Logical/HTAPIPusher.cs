using LightDataModel;
using Shotgun.Model.Filter;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace n8wan.Public.Logical
{
    public class HTAPIPusher : CPPusher
    {
        /// <summary>
        /// 主要用于API订单匹配验证
        /// </summary>
        tbl_sp_trone_apiItem _apiMatchAPI;
        private tbl_api_orderItem _apiOrder;

        /// <summary>
        /// 加载浩天API回调匹配信息
        /// </summary>
        /// <returns></returns>
        public override bool LoadCPAPI()
        {
            if (Trone == null)
                return false;
            tbl_sp_trone_apiItem m = tbl_sp_trone_apiItem.GetRowByTroneId(dBase, Trone.id);
            if (m == null)
                return false;
            _apiMatchAPI = m;
            return true;
        }


        public override bool DoPush()
        {

            if (_apiMatchAPI == null)
                return false;

            if (PushObject.cp_id > 0 && PushObject.cp_id != 34)
            {//已经关联的订单
                _apiOrder = LoadApiOrder(PushObject.GetValue(EPushField.ApiOrderId));
            }
            else
            {//未匹配的新订单
                _apiOrder = LoadApiOrder();
            }

            if (_apiOrder == null)
                return false;

            //var tOrder = tbl_trone_orderItem.GetQueries(dBase);
            //tOrder.Filter.AndFilters.Add(tbl_trone_orderItem.Fields.id, _apiOrder.trone_order_id);
            //tOrder.Filter.AndFilters.Add(tbl_trone_orderItem.Fields.disable, false);
            //var m = tOrder.GetRowByFilters();
            var m = tbl_trone_orderItem.GetRowByIdWithCache(dBase, _apiOrder.trone_order_id);
            if (m == null || m.disable)
                return false;
            this.CP_Id = m.cp_id;
            SetConfig(m);//找到对应的渠道上量(相当于执行 base.LoadCPAPI())
            if (PushObject is tbl_mrItem)
            {
                var mr = ((tbl_mrItem)PushObject);
                mr.api_order_id = _apiOrder.id;
                mr.user_md10 = System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(string.Format("{0}_{1}_{2}", _apiOrder.imsi, _apiOrder.imei, _apiOrder.mobile), "MD5");
                if (string.IsNullOrEmpty(mr.mobile) && !string.IsNullOrEmpty(_apiOrder.mobile))
                    mr.mobile = _apiOrder.mobile;
                if (string.IsNullOrEmpty(mr.imsi) && !string.IsNullOrEmpty(_apiOrder.imsi))
                    mr.imsi = _apiOrder.imsi;
                if (mr.province_id == 32 && (!string.IsNullOrEmpty(mr.mobile) || !string.IsNullOrEmpty(mr.imsi)))
                    BaseSPCallback.FillAreaInfo(dBase, mr);
            }
            //if (!LoadCPAPI())
            //    return false;



            return base.DoPush();
        }

        /// <summary>
        /// 根据ID，加载指令的订单
        /// </summary>
        /// <param name="p"></param>
        /// <returns></returns>
        private tbl_api_orderItem LoadApiOrder(string id)
        {
            if (string.IsNullOrEmpty(id))
                return null;
            tbl_mrItem mr = (tbl_mrItem)PushObject;
            var l = tbl_api_orderItem.GetQueries(dBase);
            l.TableDate = mr.mr_date;
            l.Filter.AndFilters.Add(tbl_api_orderItem.Fields.PrimaryKey, id);
            return l.GetRowByFilters();

        }

        private tbl_api_orderItem LoadApiOrder()
        {
            var l = tbl_api_orderItem.GetQueries(dBase);
            //l.Filter.AndFilters.Add(tbl_api_orderItem.Fields.trone_id, TroneId);
            string ptr;
            switch (_apiMatchAPI.match_field_E)
            {//订单匹配条件生成
                case tbl_sp_trone_apiItem.EMathcField.Cpprams:
                    ptr = this.PushObject.GetValue(EPushField.cpParam);
                    if (string.IsNullOrEmpty(ptr))
                        return null;//同步配置错，SP并没有回传透传
                    l.Filter.AndFilters.Add(tbl_api_orderItem.Fields.api_exdata, this.PushObject.GetValue(EPushField.cpParam));
                    break;
                case tbl_sp_trone_apiItem.EMathcField.LinkId:
                    l.Filter.AndFilters.Add(tbl_api_orderItem.Fields.sp_linkid, this.PushObject.GetValue(EPushField.LinkID));
                    break;
                case tbl_sp_trone_apiItem.EMathcField.Msg:
                    l.Filter.AndFilters.Add(tbl_api_orderItem.Fields.msg, this.PushObject.GetValue(EPushField.Msg));
                    l.Filter.AndFilters.Add(tbl_api_orderItem.Fields.port, this.PushObject.GetValue(EPushField.port));
                    break;
                case tbl_sp_trone_apiItem.EMathcField.Msg_Not_Equal://同步指令与上行指令不一至时，使用“port,msg”拼接用逗号分隔，并在sp透传查找
                    l.Filter.AndFilters.Add(tbl_api_orderItem.Fields.api_exdata,
                        string.Format("{0},{1}", PushObject.GetValue(EPushField.port), this.PushObject.GetValue(EPushField.Msg)));
                    break;
            }
            l.Filter.AndFilters.Add(tbl_api_orderItem.Fields.api_id, _apiMatchAPI.id);
            l.Filter.AndFilters.Add(tbl_api_orderItem.Fields.trone_id, Trone.id);
            l.Filter.AndFilters.Add(tbl_api_orderItem.Fields.status, new int[] { 1011, 1013, 2023 });//一次成，2次成功，二次超时
#if DEBUG
            var t = l.GetRowByFilters();
            System.Diagnostics.Debug.Write("查找API_Order:");
            System.Diagnostics.Debug.Write(l.LastSqlExecute);
            System.Diagnostics.Debug.WriteLine("IsFound:{0}", t != null);

            return t;
#else
            return l.GetRowByFilters();//查到订单号
#endif
        }

        protected override void SendQuery()
        {
            if (string.IsNullOrEmpty(API_PushUrl))
            {
                WriteLog(-1, "No Push URL");
                return;
            }


            var ptrs = new Dictionary<string, string>();
            ptrs.Add("mobile", PushObject.GetValue(Logical.EPushField.Mobile));
            ptrs.Add("servicecode", PushObject.GetValue(Logical.EPushField.ServiceCode));
            ptrs.Add("linkid", PushObject.GetValue(Logical.EPushField.LinkID));
            ptrs.Add("msg", PushObject.GetValue(Logical.EPushField.Msg));
            //ptrs.Add("status", PushObject.GetValue(Logical.EPushField.Status));
            ptrs.Add("port", PushObject.GetValue(Logical.EPushField.port));

            ptrs.Add("price", (Trone.price * 100).ToString("0"));
            ptrs.Add("cpparam", _apiOrder.ExtrData);
            ptrs.Add("provinceId", PushObject.GetValue(EPushField.province));
            ptrs.Add("paycode", _apiOrder.trone_order_id.ToString("100000"));
            ptrs.Add("ordernum", string.Format("{0:yyyyMM}{1}", _apiOrder.FirstDate, _apiOrder.id));
            ptrs.Add("virtualMobile", base.GetVirtualMobile());

            string qs = UrlEncode(ptrs);


            string url;
            if (API_PushUrl.Contains('?'))
                url = API_PushUrl + "&" + qs;
            else
                url = API_PushUrl + "?" + qs;

            asyncSendData(url, null);

        }


    }
}
