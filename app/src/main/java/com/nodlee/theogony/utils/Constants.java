package com.nodlee.theogony.utils;

import android.net.Uri;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class Constants {
    /**
     * 拳头公司APP_KEY，开发者需要到拳头开发者平台申请
     */
    public static final String RIOT_APP_KEY = "";

    public static class Favorite {
        public static final Uri CONTENT_URI = Uri.parse("content://com.nodlee.thogony").buildUpon()
                .appendPath("favorite").build();
    }

    public static class Champions {
        public static final Uri CONTENT_URI = Uri.parse("content://com.nodlee.thogony").buildUpon()
                .appendPath("champion").build();
    }
}
