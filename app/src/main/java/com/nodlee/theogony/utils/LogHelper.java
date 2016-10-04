package com.nodlee.theogony.utils;

import android.util.Log;

/**
 * 作者：nodlee
 * 时间：16/10/4
 * 说明：日志工具类
 */
public class LogHelper {
    private static boolean DEBUG = true;

    public static void LOGD(String tag, String message) {
       if (DEBUG) {
           Log.d(tag, message);
       }
    }

    public static void LOGI(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void LOGW(String tag, String message) {
        if (DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void LOGE(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }
}
