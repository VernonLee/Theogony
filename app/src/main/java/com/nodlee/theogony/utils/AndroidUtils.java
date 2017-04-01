package com.nodlee.theogony.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.security.Key;
import java.util.Locale;

import static android.R.attr.country;
import static android.R.attr.value;

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

    /**
     * 获取系统语言code(script language code格式)
     * @return
     */
    public static String getSystemLocale() {
        String language = Locale.getDefault().getLanguage();
        return LanguageCodeConverter.convert(language);
    }

    /**
     * 获取应用参数
     * @param context
     * @param key
     * @return
     */
    public static String getMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
       String value = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(),
                    PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取缓存大小
     * @return
     */
    public static String getCacheSize(Context context) {
        int externalCacheSize = -1;
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir.exists()) {
            externalCacheSize = (int) Math.ceil(getFileSize(externalCacheDir));
        }

        int cacheSize = -1;
        File cacheDir = context.getCacheDir();
        if (cacheDir.exists()) {
            cacheSize = (int) Math.ceil(getFileSize(cacheDir));
        }

        int totalSize = externalCacheSize + cacheSize;

        return totalSize > 0 ? String.format("%dM", totalSize) : "";
    }

    /**
     * 获取目录尺寸，单位：M
     * @param file
     * @return
     */
    private static double getFileSize(File file) {
        if (!file.exists()) return 0;

        if (file.isDirectory()) {
            File[] children = file.listFiles();
            double size = 0;
            for (File f : children) {
                size += getFileSize(f);
            }
            return size;
        } else {
            return (double) file.length() / 1024 / 1024;
        }
    }

    /**
     * 删除目录下面所有文件
     * @param file
     */
    private static boolean delete(File file) {
        if (!file.exists()) return false;

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (File file1 : childFiles) {
                delete(file1);
            }
        } else {
            file.delete();
        }
        return true;
    }

    /**
     * 清空缓存 sdcard中cache目录下
     */
    public static boolean clean(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        File cacheDir = context.getCacheDir();
        delete(externalCacheDir);
        delete(cacheDir);
        return true;
    }
}
