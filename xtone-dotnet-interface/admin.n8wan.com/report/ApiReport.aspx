<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ApiReport.aspx.cs" Inherits="report_ApiReport" MasterPageFile="~/MasterPage.master" %>

<asp:Content runat="server" ContentPlaceHolderID="head">
    <script src="../scripts/jquery-3.1.1.js"></script>
    <script type="text/javascript">
        var data = [{ "trone_Id": "", "trone_order_id": "", "cp_id": "", "sp_trone_id": "", "sp_id": "", "sp_trone_name": "", "trone_name": "", "price": "", "sp_name": "", "cp_name": "" }];

        function page_onload() {

            var d = new Date();
            var ds = d.getFullYear() + "-" + (d.getMonth() + 1) + "-";
            var html = "";
            for (var i = d.getDate() ; i > 0 ; i--) {
                html += "<option value=\"" + ds + i.toString() + "\">" + ds + i.toString() + "</option>";
            }

            $("#date").html(html);
            date_onchange($("#date")[0], null);
            //var js = [{ "trone_order_id": "4740", "time": "17-02-04 10:00:00", "status": "1011", "count": "799" }, { "trone_order_id": "4740", "time": "17-02-04 10:00:00", "status": "1020", "count": "6" }, { "trone_order_id": "4740", "time": "17-02-04 10:00:00", "status": "1022", "count": "1073" }]
            //ondata(js);
            //"../ajax/getApiData.ashx?method=initHead", null, initHead, "json");
        }

        function date_onchange(sender, e) {
            sender.form.disabled = true;
            $.get(
               {
                   "dataType": "json",

                   "url": "../ajax/getApiData.ashx?method=initHead&date=" + sender.value,
                   "success": initHead,
                   "error": function (e) { alert(e) },
                   "complete": function (e) { sender.form.disabled = false; }
               }
               );
        }

        function initHead(e) {
            var sp = $("#sp");
            var spTroneId = $("#spTroneId");
            var troneId = $("#troneId");
            var cp = $("#cp");
            var troneOrderId = $("select[name='troneOrderId']");
            data = e;
            ClearSelector(troneOrderId[0])
            for (var i = 0; i < e.length; i++) {
                var item = e[i];
                AppendOption(sp[0], item.sp_id, item.sp_name);
                AppendOption(cp[0], item.cp_id, item.cp_name);
                AppendTroneOrderOption(item);
            }
        }
        function AppendOption(sel, val, txt) {
            for (var i = 0; i < sel.options.length; i++) {
                if (sel.options[i].value == val)
                    return;
            }
            var opt = document.createElement("option");
            opt.value = val;
            opt.text = txt;
            sel.add(opt);
        }

        function sp_onchange(sender, e) {
            var sp = $("#sp");
            var spTroneId = $("#spTroneId");
            var troneId = $("#troneId");
            var cp = $("#cp");
            var troneOrderId = $("select[name='troneOrderId']");
            var spid = sender.value;
            var cpid = cp.val();
            e = data;
            ClearSelector(spTroneId[0], troneId[0], troneOrderId[0]);
            var cps = [];
            for (var i = 0; i < e.length; i++) {
                var item = e[i];
                if (spid != "0" && item.sp_id != spid)
                    continue;
                AppendOption(spTroneId[0], item.sp_trone_id, item.sp_trone_name);
                AppendOption(troneId[0], item.trone_Id, item.trone_name);
                cps.push(item.cp_id);

                if (cpid != "0" && item.cp_id != cpid)
                    continue;
                AppendTroneOrderOption(item);

            }
            hideOption(cp[0], cps);
        }
        function AppendTroneOrderOption(item) {
            var txt = (100000 + parseInt(item.trone_order_id)).toString()
                    + " - " + item.price
                    + " - " + item.sp_trone_name
                    + " - " + item.cp_name;

            AppendOption(document.getElementsByName("troneOrderId")[0], item.trone_order_id, txt);
        }

        function hideOption(sel, vals) {
            for (var i = 1; i < sel.options.length; i++) {
                var ifound = false;
                var v = sel.options[i].value;
                if (vals == null)
                    ifound = true;
                else {
                    for (var x = 0; x < vals.length; x++) {
                        if (v == vals[x]) {
                            ifound = true;
                            break;
                        }
                    }
                }
                sel.options[i].style.color = ifound ? "" : "#ccc";
            }

        }

        function sptrone_onchange(sender, e) {
            var sp = $("#sp");
            var spTroneId = $("#spTroneId");
            var troneId = $("#troneId");
            var cp = $("#cp");
            var troneOrderId = $("select[name='troneOrderId']");
            var sptone = sender.value;
            var cpid = cp.val();
            e = data;
            var cps = [];

            ClearSelector(troneId[0], troneOrderId[0]);
            for (var i = 0; i < e.length; i++) {
                var item = e[i];
                if (sptone != "0" && item.sp_trone_id != sptone)
                    continue;
                AppendOption(troneId[0], item.trone_Id, item.price + " - " + item.trone_name);
                if (cpid != "0" && item.cp_id != cpid)
                    continue;
                cps.push(item.cp_id);
                AppendTroneOrderOption(item);
            }
            hideOption(cp[0], cps);
        }

        function trone_onchange(sender, e) {
            var sp = $("#sp");
            var spTroneId = $("#spTroneId");
            var troneId = $("#troneId");
            var cp = $("#cp");
            var troneOrderId = $("select[name='troneOrderId']");
            var trone = sender.value;
            var cpid = cp.val();
            e = data;
            var cps = [];

            ClearSelector(troneOrderId[0]);
            for (var i = 0; i < e.length; i++) {
                var item = e[i];
                if (trone != "0" && item.trone_Id != trone)
                    continue;
                if (cpid != "0" && item.cp_id != cpid)
                    continue;
                cps.push(item.cp_id);
                AppendTroneOrderOption(item);
            }
            hideOption(cp[0], cps);


        }


        function cp_onchange(sender, e) {
            var sp = $("#sp");
            var spTroneId = $("#spTroneId");
            var troneId = $("#troneId");
            var cp = $("#cp");
            var troneOrderId = $("select[name='troneOrderId']");
            var cpid = sender.value;

            e = data;
            var tId = troneId.val()
            var stId = spTroneId.val();
            var spid = sp.val();

            if (cpid == "0") {
                hideOption(sp[0], null);
                if (tId != "0") {
                    troneId[0].onchange();
                } else if (stId != "0")
                    spTroneId[0].onchange();

                sp[0].onchange();


                return;
            }

            ClearSelector(troneOrderId[0]);

            var sps = [];
            for (var i = 0; i < e.length; i++) {
                var item = e[i];
                if (item.cp_id != cpid) {
                    continue;
                }
                if (tId != "0" && tId != item.trone_Id)
                    continue;
                if (stId != "0" && stId != item.sp_trone_id)
                    continue;
                if (spid != "0" && spid != item.sp_id)
                    continue;

                sps.push(item.sp_id);
                //AppendOption(cp[0], item.cp_id, item.cp_name);
                AppendTroneOrderOption(item);
            }
            hideOption(sp[0], sps);
            //hideOption(sender, null);
        }



        function ClearSelector() {
            for (var i = 0; i < arguments.length; i++) {
                arguments[i].options.length = 1;
            }
        }

        function frm_onsubmit(sender, e) {

            var type = sender["type"].value;
            var paycode = sender["troneOrderId"].value;
            if (paycode == "0") {
                paycode = "";
                var opts = sender["troneOrderId"].options;
                for (var i = 1; i < opts.length; i++) {
                    paycode += "," + opts[i].value;
                }
                if (paycode != "")
                    paycode = paycode.substring(1);
            }

            var tab = document.getElementById("tab");



            tab.innerHTML = "<tr><td colspan=\"" + $(".datagrid th").length + "\">paycode(s)：" + paycode + "Loading.... </td></tr>";

            var url = "../ajax/getApiData.ashx?method=getdata&type=" + type + "&paycode=" + paycode + "&date=" + sender["date"].value;
            $.get(
            {
                "dataType": "json",
                "url": url,
                "success": ondata,
                "error": function (e) { alert(e) }
            }
            );

        }

        function ondata(e) {
            var html = "";
            var lpaycode = "0";

            var lastItem = null;
            hasdata = false;

            var summer = new Object();
            for (var i = 0; i < e.length; i++) {
                var item = e[i];


                //if (lpaycode != item.trone_order_id) {
                //    html = html.replace(/rowspan="\$"/gi, "rowspan=\"" + row.toString() + "\"")
                //    html += GetBaseTone(item.trone_order_id);
                //    row = 1;
                //    lpaycode = item.trone_order_id;
                //}
                //else
                //    row++;

                if (lastItem == null || lastItem.type == item.type) {
                    summer[item.status] = item.count;
                    hasdata = true;
                }
                else {
                    html += "<tr>";
                    //html = html.replace(/rowspan="\$"/gi, "rowspan=\"" + row.toString() + "\"")
                    html += GetBaseTone(lastItem.trone_order_id);

                    //html = html.replace(/rowspan="\#"/gi, "rowspan=\"" + trow.toString() + "\"")
                    html += "<td>" + lastItem.type + "</td>";
                    html += GetSummerData(summer);
                    summer = new Object();
                    html += "</tr>"
                    hasdata = false;

                    summer[item.status] = item.count;
                    hasdata = lastItem != null;

                }
                lastItem = item;
            }
            if (hasdata) {

                html += "<tr>";
                //html = html.replace(/rowspan="\$"/gi, "rowspan=\"" + row.toString() + "\"")
                html += GetBaseTone(lastItem.trone_order_id);

                //html = html.replace(/rowspan="\#"/gi, "rowspan=\"" + trow.toString() + "\"")
                html += "<td>" + lastItem.type + "</td>";
                html += GetSummerData(summer);
                summer = new Object();
                html += "</tr>"
            }


            document.getElementById("tab").innerHTML = html;

        }

        function GetBaseTone(paycode) {
            var html = "";
            for (var i = 0; i < data.length; i++) {
                if (data[i].trone_order_id != paycode)
                    continue;
                var item = data[i];
                html = "<td>" + item.sp_name + "</td>"
                        + "<td>" + item.sp_trone_name + "</td>"
                        + "<td>" + item.price + "</td>"
                        + "<td>" + item.cp_name + "</td>";
                return html;
            }
            return "<td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td>"
        }

        function GetSummerData(s) {
            var s1011 = 0, s1013 = 0, s1xxx = 0, s2xxx = 0;
            var m1xxx = "", m2xxx = "";
            var sum = 0;
            for (var k in s) {
                var v = parseInt(s[k]);
                sum += v;
                switch (k) {
                    case "1011": s1011 += v; break; //一次成功
                    case "1013": s1013 += v; break;//二次成功
                    case "1009": s1xxx += v; break;//省份错误
                    default:
                        var t = parseInt(k);
                        if (t < 2000) {
                            s1xxx += v;
                            m1xxx += k + ":" + v + ",";
                        }
                        else if (t < 3000) {
                            s2xxx += v;
                            m2xxx += k + ":" + v + ",";
                        }

                }
            }
            if (m1xxx.length > 1)
                m1xxx.length--;
            if (m2xxx.length > 1)
                m2xxx.length--;

            html = "<td>" + s1011.toString() + "</td>";
            html += "<td>" + s1013.toString() + "</td>";
            html += "<td title=\"" + m1xxx + "\">" + s1xxx.toString() + "</td>";
            html += "<td title=\"" + m2xxx + "\">" + s2xxx.toString() + "</td>";
 
            html += "<td>" + Math.round((s1011 + s1013) * 100 / sum, 2) + "%</td>"
            html += "<td>" + Math.round((s1013) * 100 / sum, 2) + "%</td>"
            return html;
        }

    </script>

</asp:Content>
<asp:Content runat="server" ContentPlaceHolderID="body">
    <form onsubmit="frm_onsubmit(this,null);return false;">
        日期：
        <select id="date" name="date" onchange="date_onchange(this,null)">
        </select>
        SP:<select id="sp" onchange="sp_onchange(this,null)"><option value="0">*全部*</option>
        </select>
        SP业务:<select id="spTroneId" onchange="sptrone_onchange(this,null)"><option value="0">*全部*</option>
        </select>
        SP通道:<select id="troneId" onchange="trone_onchange(this,null)"><option value="0">*全部*</option>
        </select>
        CP:<select id="cp" onchange="cp_onchange(this,null)"><option value="0">*全部*</option>
        </select>
        PayCode:<select name="troneOrderId"><option value="0">*全部*</option>
        </select>
        <br />
        分组方式：<label><input type="radio" name="type" value="0" checked="checked" />时间</label>
        <input type="submit" value="确定" />
    </form>
    <table class="datagrid">
        <tr>
            <th>sp</th>
            <th>业务</th>
            <th>价格</th>
            <th>CP</th>
            <th>分类</th>
            <th>1011</th>
            <th>1013</th>
            <th>1XXX</th>
            <th>2XXX</th>
            <th>指令</th>
            <th>验证码</th>

        </tr>
        <tbody id="tab">
        </tbody>
    </table>

</asp:Content>


