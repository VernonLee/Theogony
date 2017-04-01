package com.nodlee.theogony.utils;

import android.util.Log;

/**
 * 作者：nodlee
 * 时间：16/10/4
 * 说明：日志工具类
 */
public class LogHelper {
    private static final String TAG = "theogony";
    private static boolean DEBUG = true;

    public static void LOGD(String message) {
       if (DEBUG) {
           Log.d(TAG, message);
       }
    }

    public static void LOGI(String message) {
        if (DEBUG) {
            Log.i(TAG, message);
        }
    }

    public static void LOGW(String message) {
        if (DEBUG) {
            Log.w(TAG, message);
        }
    }

    public static void LOGE(String message) {
        if (DEBUG) {
            Log.e(TAG, message);
        }
    }
}
