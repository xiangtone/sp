using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace n8wan.Public
{
    public static class Library
    {
        /// <summary>
        /// 通配符转正则，默认不区分大小写
        /// </summary>
        /// <param name="mStr"></param>
        /// <returns></returns>
        public static Regex GetRegex(string mStr)
        {
            mStr = Regex.Replace(mStr, @"([\.\[\]\|\^\$\<\>])", @"\$1");

            if (mStr.Contains('?'))
                mStr = Regex.Replace(mStr, @"\?{1,}", e => string.Format(".{{0,{0}}}", e.Value.Length));

            if (mStr.Contains('*'))
                mStr = Regex.Replace(mStr, @"\*{1,}", ".{0,}");
            mStr = "^" + mStr + "$";
            return new Regex(mStr, RegexOptions.IgnoreCase);
        }


        /// <summary>
        ///反编译IMSI获得手机前7位 判断区域
        /// </summary>
        ///<param name="imsi">Imsi</param>
        public static string GetPhoneByImsi(string imsi)
        {

            string h0;
            string h1;
            string h2;
            string h3;
            const string s56789 = "56789";
            string strDigit;

            if (string.IsNullOrEmpty(imsi))
            {
                return "13800000000";
            }

            if (imsi.StartsWith("46000"))
            {
                //
                h1 = imsi.Substring(5, 1);
                h2 = imsi.Substring(6, 1);
                h3 = imsi.Substring(7, 1);
                var st = imsi.Substring(8, 1);
                h0 = imsi.Substring(9, 1);

                if (s56789.IndexOf(st, StringComparison.Ordinal) >= 0)
                {
                    return "13" + st + "0" + h1 + h2 + h3;
                }
                else
                {
                    var tempint = (int.Parse(st) + 5);

                    return "13" + tempint + h0 + h1 + h2 + h3;
                }
            }


            if (imsi.StartsWith("46002"))
            {
                strDigit = imsi.Substring(5, 1);
                h0 = imsi.Substring(6, 1);
                h1 = imsi.Substring(7, 1);
                h2 = imsi.Substring(8, 1);
                h3 = imsi.Substring(9, 1);

                if (strDigit.Equals("0"))
                {
                    return "134" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("1"))
                {
                    return "151" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("2"))
                {
                    return "152" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("3"))
                {
                    return "150" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("5"))
                {
                    return "183" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("6"))
                {
                    return "182" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("7"))
                {
                    return "187" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("8"))
                {
                    return "158" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("9"))
                {
                    return "159" + h0 + h1 + h2 + h3;
                }
            }

            if (imsi.StartsWith("46003"))
            {
                strDigit = imsi.Substring(5, 1);
                h0 = imsi.Substring(6, 1);
                h1 = imsi.Substring(7, 1);
                h2 = imsi.Substring(8, 1);
                h3 = imsi.Substring(9, 1);

                if (!strDigit.Equals("0")) return "153" + h0 + h1 + h2 + h3;
                if (h0 == "9" && h1 + h2 == "00")
                {
                    return "13301" + h3 + imsi.Substring(10, 1);
                }
                return "133" + h0 + h1 + h2 + h3;
            }
            if (imsi.StartsWith("46011"))
            {
                //strDigit = imsi.Substring(5, 1);
                h0 = imsi.Substring(6, 1);
                h1 = imsi.Substring(7, 1);
                h2 = imsi.Substring(8, 1);
                h3 = imsi.Substring(9, 1);
                return "177" + h0 + h1 + h2 + h3;
            }

            if (imsi.StartsWith("46007"))
            {
                //
                strDigit = imsi.Substring(5, 1);
                h0 = imsi.Substring(6, 1);
                h1 = imsi.Substring(7, 1);
                h2 = imsi.Substring(8, 1);
                h3 = imsi.Substring(9, 1);

                if (strDigit.Equals("5"))
                {
                    return "178" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("7"))
                {
                    return "157" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("8"))
                {
                    return "188" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("9"))
                {
                    return "147" + h0 + h1 + h2 + h3;
                }
            }


            if (imsi.StartsWith("46001"))
            {
                //中国联通，只有46001这一个IMSI号码段
                h1 = imsi.Substring(5, 1);
                h2 = imsi.Substring(6, 1);
                h3 = imsi.Substring(7, 1);
                h0 = imsi.Substring(8, 1);
                strDigit = imsi.Substring(9, 1);	//for A
                if (strDigit.Equals("0") || strDigit.Equals("1"))
                {
                    return "130" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("9"))
                {
                    return "131" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("2"))
                {
                    return "132" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("3"))
                {
                    return "156" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("4"))
                {
                    return "155" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("6"))
                {
                    return "186" + h0 + h1 + h2 + h3;
                }
                else if (strDigit.Equals("7"))
                {
                    return "145" + h0 + h1 + h2 + h3;
                }
            }

            return string.Empty;
        }
        /// <summary>
        /// 下载远程代码(不带ContentType值)
        /// </summary>
        /// <param name="url">目标网址</param>
        /// <param name="postdata">post数据,NULL时为GET</param>
        /// <param name="timeout">超时时间,单位为毫秒,默认:3秒</param>
        /// <param name="encode">编码方式,默认utf8</param>
        /// <returns></returns>
        public static string DownloadHTML(string url, string postdata, int timeout, string encode)
        {
            return DownloadHTML(url, postdata, timeout, encode, null);
        }

        /// <summary>
        /// 下载远程代码
        /// </summary>
        /// <param name="url">目标网址</param>
        /// <param name="postdata">post数据,NULL时为GET</param>
        /// <param name="timeout">超时时间,单位为毫秒,默认:3秒</param>
        /// <param name="encode">编码方式,默认utf8</param>
        /// <param name="ContentType">默认为空</param>
        /// <returns></returns>
        public static string DownloadHTML(string url, string postdata, int timeout, string encode, string ContentType)
        {

            Encoding ec = null;
            if (string.IsNullOrEmpty(encode))
                ec = ASCIIEncoding.UTF8;
            else
                ec = ASCIIEncoding.GetEncoding(encode);

            System.Net.HttpWebRequest web = null;
            Stream stm = null;
            web = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(url);
             
            web.Timeout = timeout < 1 ? 2888 : timeout;
            web.AllowAutoRedirect = false;
            web.AutomaticDecompression = System.Net.DecompressionMethods.GZip;
            web.ServicePoint.UseNagleAlgorithm = false;
            if (postdata != null)
            {
                web.ServicePoint.Expect100Continue = false;
                web.Method = System.Net.WebRequestMethods.Http.Post;

                if (!string.IsNullOrEmpty(ContentType))
                    web.ContentType = ContentType;

                var bin = ec.GetBytes(postdata);
                using (  stm = web.GetRequestStream())
                {
                    stm.Write(bin, 0, bin.Length);
                }
             stm = null;
           }

            StreamReader reader = null;
             System.Net.WebResponse rsp =  web.GetResponse();
             try
             {
                 stm = rsp.GetResponseStream();
                 {
                     using (var rd = new System.IO.StreamReader(stm, ec))
                         return rd.ReadToEnd();
                 }
             }
             finally
             {
                 if (reader != null)
                     reader.Dispose();
                 if (stm != null)
                     stm.Dispose();
                 if (rsp != null){
                     try
                     {
                         rsp.Close();
                     }
                     catch { }
                 }
             }

        }

        static string syncUrlFix;
        public static string syncUrlPerfix()
        {
            if (syncUrlFix != null)
                return syncUrlFix;
            syncUrlFix = System.Configuration.ConfigurationManager.AppSettings["syncUrlPerfix"];
            if (syncUrlFix == null)
                syncUrlFix = string.Empty;
            return syncUrlFix;
        }
    }
}
