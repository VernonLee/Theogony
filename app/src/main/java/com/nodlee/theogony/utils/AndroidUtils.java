package com.nodlee.theogony.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Vernon Lee on 15-11-19.
 */
public class AndroidUtils {

    public static void showSnackBar(View view, String text) {
        if (view == null || TextUtils.isEmpty(text)) return;
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackBar(View view, int textResId) {
        if (view == null || textResId == 0) return;
        Snackbar.make(view, textResId, Snackbar.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int textResId) {
        if (context == null || textResId == 0) return;
        Toast.makeText(context, textResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String text) {
        if (context == null || text == null) return;
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static float dpToPx(float dp, Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        Resources res = context.getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        return dp * metrics.density;
    }

    public static String getAppVersion(Context context) {
        String appVersion = "<未知版本>";
        try {
            PackageInfo info = context.getPackageManager()
                                      .getPackageInfo(context.getPackageName(), 0);
            appVersion = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    public static int getDayNightMode(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        return context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    }
}
