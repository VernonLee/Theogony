package com.nodlee.amumu.champions;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * 英雄联盟英雄数据库入口，用来处理APPKEY逻辑，
 * Created by nodlee on 16/6/15.
 */
public class Amumu {

    static String sAppKey;

    public static void init(Context context) {

        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                            context.getPackageName(),
                            PackageManager.GET_META_DATA);

            String appKey = appInfo.metaData.getString("app_key");
            if (appKey == null) {
                throw new NullPointerException("APP KEY can not be null");
            }

            sAppKey = appKey;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
