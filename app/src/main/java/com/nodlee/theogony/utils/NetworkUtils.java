package com.nodlee.theogony.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class NetworkUtils {
    private static final String CHARSET = "utf-8";

    public static boolean isNetworkAvailable(Context context) {
       ConnectivityManager conn = (ConnectivityManager) context
               .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static String urlEncode(String str) {
        String result = "";
        try {
            if (str != null && str.length() > 0) {
                result = URLEncoder.encode(str, CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
