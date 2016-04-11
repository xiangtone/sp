﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sdk_Request.Logical
{
    /// <summary>
    /// API请求常用错误信息
    /// </summary>
    public enum API_ERROR
    {
        /// <summary>
        /// 前端系统错误(不在此系统使用)
        /// </summary>
        FAWAD_SYSTEM_ERROR = 1010,
        /// <summary>
        /// 屏蔽地区
        /// </summary>
        AREA_CLOSE = 1009,
        /// <summary>
        /// 第一步操作返回成功
        /// </summary>
        OK = 1011,
        STEP2_OK = 1013,
        /// <summary>
        /// 内部错误，通常指内部(对接)程序出错了
        /// </summary>
        INNER_ERROR = 1020,
        /// <summary>
        /// 内部错误，通常指程序没按要求对接
        /// </summary>
        INNER_CONFIG_ERROR=1021,
        /// <summary>
        /// 取指令失败
        /// </summary>
        GET_CMD_FAIL =1022,
        /// <summary>
        /// 网关超时，一般是与SP通迅时出错了
        /// </summary>
        GATEWAY_TIMEOUT=1023,
        /// <summary>
        /// 计费点错误（通道指没有该金额）
        /// </summary>
        ERROR_PAY_POINT=1024,
        /// <summary>
        /// 未知格式数据（可能是SP服务出错或SP变更了输出格式)
        /// </summary>
        UNKONW_RESULT=1025,
        /// <summary>
        /// 验证码错误（SP结果查转换）
        /// </summary>
        VERIFY_CODE_ERROR=1026,
        /// <summary>
        /// SP黑名单用户（SP结果查转换）
        /// </summary>
        BLACK_USER=1027,
        /// <summary>
        /// 用户话费不足（SP结果查转换）
        /// </summary>
        NOT_ENOUGH_MONEY=1028

    }
}
