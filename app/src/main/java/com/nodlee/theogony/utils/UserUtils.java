package com.nodlee.theogony.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class UserUtils {
    /** 第一次使用App标识 */
    private static final String PREF_FIRST_BLOOD = "pref_first_blood";
    /** 当前源数据语言 */
    private static final String PREF_LOL_STATIC_DATA_LOCALE = "pref_lol_static_data_locale";
    /** 当前元数据版本 */
    private static final String PREF_LOL_STATIC_DATA_VERSION = "pref_lol_static_data_version";
    /** 是否是夜间模式 */
    private static final String PREF_NIGHT_YES = "pref_night_yes";

    public static void setFirstBlood(Context context, boolean isFirstBlood) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(PREF_FIRST_BLOOD, isFirstBlood).commit();
    }

    public static boolean isFirstBlood(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_FIRST_BLOOD, true);
    }

    public static void setLolStaticDataLocal(Context context, String locale) {
        PreferenceManager.getDefaultSharedPreferences(context)
                         .edit()
                         .putString(PREF_LOL_STATIC_DATA_LOCALE, locale)
                         .commit();
    }

    public static String getLolStaticDataLocale(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                                .getString(PREF_LOL_STATIC_DATA_LOCALE, "<未知语言>");
    }

    public static String getLolStaticDataVersion(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                                .getString(PREF_LOL_STATIC_DATA_VERSION, null);
    }

    public static void setLolStaticDataVerion(Context context, String version) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LOL_STATIC_DATA_VERSION, version)
                .commit();
    }

    public static void setNightMode(Context context, boolean isNight) {
        PreferenceManager.getDefaultSharedPreferences(context)
                         .edit()
                         .putBoolean(PREF_NIGHT_YES, isNight)
                         .commit();
    }

    public static boolean isNightMode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                                .getBoolean(PREF_NIGHT_YES, false);
    }
}
