using LightDataModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace n8wan.Public.Logical
{
    public class AutoMapPush : CPPusher
    {
        private List<LightDataModel.tbl_trone_orderItem> _allCfg;


        public override bool LoadCPAPI()
        {
            if (Trone == null)
                return false;
            var l = LightDataModel.tbl_trone_orderItem.GetQueries(dBase);
            l.Filter.AndFilters.Add(LightDataModel.tbl_trone_orderItem.Fields.trone_id, Trone.id);
            l.Filter.AndFilters.Add(LightDataModel.tbl_trone_orderItem.Fields.disable, 0);
            l.PageSize = int.MaxValue;
            _allCfg = l.GetDataList();

            if (_allCfg == null || _allCfg.Count == 0)
            {
                var m = CreateDefaultTrone();
                _allCfg = new List<tbl_trone_orderItem>();
                _allCfg.Add(m);
            }
            //base.SetConfig(_allCfg[0]);
            return SetSuccess();
        }


        public override bool DoPush()
        {
            bool isRecord = false;
            tbl_trone_orderItem defCfg = null;
            if (PushObject.cp_id > 0 && PushObject.cp_id != 34)
            {
                defCfg = tbl_trone_orderItem.GetRowById(dBase, PushObject.trone_order_id);
                if (defCfg==null)
                    return SetErrorMesage("已经绑定的渠道业务信息丢失");
                base.SetConfig(defCfg);
                return base.DoPush();
            }
            foreach (var m in _allCfg)
            {
                if (m.is_unknow)
                {
                    if (defCfg != null)
                        base.WriteLog(-3, "存在多个默认CP！cfgId:" + m.id.ToString());
                    defCfg = m;
                    continue;
                }
                if (!IsMatch(m))
                    continue;
                if (isRecord)
                {
                    base.WriteLog(-3, "配置有冲突：cfgId:" + m.id.ToString());
                    continue;
                }
                isRecord = true;
                base.SetConfig(m);
                base.DoPush();
            }
            if (isRecord)
                return true;
            if (PushObject.cp_id == 34)
                return true;
            if (defCfg == null)
                defCfg = CreateDefaultTrone();
            base.SetConfig(defCfg);
            base.DoPush();
            return true;

        }

        private tbl_trone_orderItem CreateDefaultTrone()
        {
            var ret = new tbl_trone_orderItem();
            ret.cp_id = 34; //未知CP的ID
            ret.create_date = DateTime.Now;
            ret.disable = false;
            ret.is_dynamic = false;
            ret.is_unknow = true;
            ret.order_num = "*";
            ret.order_trone_name = "未分配指令";
            ret.push_url_id = 47; //未知CP推送URL
            ret.trone_id = Trone.id;
            dBase.SaveData(ret);
            return ret;
        }

        private bool IsMatch(LightDataModel.tbl_trone_orderItem m)
        {
            Regex rx;
            string spMsg = base.PushObject.GetValue(Logical.EPushField.Msg);
            if (spMsg == null)
                spMsg = string.Empty;
            if (m.is_dynamic)
            {//CP可模糊的指令
                rx = Library.GetRegex(m.order_num);
                return rx.IsMatch(spMsg);
            }
            //CP精确指令
            return spMsg.Equals(m.order_num, StringComparison.OrdinalIgnoreCase);
        }

    }


}
