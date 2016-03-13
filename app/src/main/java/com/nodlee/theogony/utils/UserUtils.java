package com.nodlee.theogony.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class UserUtils {
    /** 第一次使用App标识 */
    private static final String PREF_FIRST_BLOOD = "pref_first_blood";
    /** riot开发者API中源数据版本 */
    private static final String PREF_LOL_STATIC_DATA_VERSION = "pref_lol_static_data_version";
    /** 当前源数据的语言 */
    private static final String PREF_LOL_STATIC_DATA_LOCALE = "pref_lol_static_data_locale";

    public static void setFirstBlood(Context context, boolean isFirstBlood) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(PREF_FIRST_BLOOD, isFirstBlood).commit();
    }

    public static boolean isFirstBlood(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_FIRST_BLOOD, true);
    }

    public static void setLolStaticDataVersion(Context context, String version) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(PREF_LOL_STATIC_DATA_VERSION, version).commit();
    }

    public static String getLolStaticDataVersion(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LOL_STATIC_DATA_VERSION, null);
    }

    public static void setLolStaticDataLocal(Context context, String locale) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(PREF_LOL_STATIC_DATA_LOCALE, locale).commit();
    }

    public static String getLolStaticDataLocale(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LOL_STATIC_DATA_LOCALE, null);
    }
}
