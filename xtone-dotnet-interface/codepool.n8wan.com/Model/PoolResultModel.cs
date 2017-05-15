using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Threading.Tasks;


namespace n8wan.codepool.Model
{
    /// <summary>
    /// 响应结果
    /// </summary>
    [DataContract]
    public class PoolResultModel
    {
        [JsonProperty("status")]
        public ErrorCode Status { get; set; }

        [JsonProperty("description")]
        public string Description { get; set; }

        [JsonProperty("orderNum", NullValueHandling = NullValueHandling.Ignore)]
        public string OrderNum { get; set; }

        [JsonProperty("jsonResult", NullValueHandling = NullValueHandling.Ignore)]
        public JToken Action { get; set; }


        public override string ToString()
        {
            Newtonsoft.Json.JsonSerializerSettings jss = null;
            return Newtonsoft.Json.JsonConvert.SerializeObject(this, Newtonsoft.Json.Formatting.None, jss);
            //using (var stm = new MemoryStream())
            //{
            //    var dc = new DataContractJsonSerializer(this.GetType());
            //    dc.WriteObject(stm, this);
            //    return ASCIIEncoding.UTF8.GetString(stm.ToArray());
            //}
        }
    }
}
