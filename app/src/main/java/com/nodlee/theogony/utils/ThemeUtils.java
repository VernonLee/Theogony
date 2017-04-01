package com.nodlee.theogony.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class ThemeUtils {
    /** 是否是夜间模式 */
    private static final String PREF_NIGHT_YES = "pref_night_yes";

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
