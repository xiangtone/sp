package com.lulu.player.common;

import com.lulu.player.BuildConfig;

/**
 * @author zxc
 * @time 2016/9/23 0023 上午 10:28
 */
public final class HttpUrl {

    public static final String CONTEXT = "lv_base/";

    public static final String BASE_URL = "http://" + BuildConfig.HOST + ":" + BuildConfig.PORT + "/";

    public static final String GET_USER_INFO = CONTEXT + "AppInit";

    public static final String GET_VIDEO_LIST = CONTEXT + "VideoList";

    public static final String POST_ORDER_INFO = CONTEXT + "OrderInfo";

}
