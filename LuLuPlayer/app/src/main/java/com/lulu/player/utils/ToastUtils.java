package com.lulu.player.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author zxc
 * @time 2016/9/23 0023 上午 10:27
 */
public class ToastUtils {

    public ToastUtils() {
    }

    private static Toast getToast(Context context, String msg, int length) {
        return Toast.makeText(context, msg, length);
    }

    public static void showShortMessage(Context context,String msg){
        getToast(context,msg,Toast.LENGTH_SHORT);
    }

    public static void showLongMessage(Context context,String msg){
        getToast(context,msg,Toast.LENGTH_LONG);
    }

}
