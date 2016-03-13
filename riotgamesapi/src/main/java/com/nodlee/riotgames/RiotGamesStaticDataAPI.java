package com.nodlee.riotgames;


import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class RiotGamesStaticDataAPI {
    private static final String REGION = "na";
    private static final String VERSION = "v1.2";

    private static String sAppKey;

    public RiotGamesStaticDataAPI(String appKey) {
        sAppKey = appKey;
    }

    private String generateRequestUrl(String champData, String locale) {
        if (TextUtils.isEmpty(locale))
            return null;

        return Uri.parse(String.format(Constants.BASE_ENDPOINT, REGION, VERSION)).buildUpon()
                .appendQueryParameter("locale", locale)
                .appendQueryParameter("champData", champData)
                .appendQueryParameter("api_key", sAppKey)
                .build().toString();
    }

    /**
     * 获取英雄全部信息据列表的URL
     *
     * @param locale 语言版本
     * @return
     */
    public String getRequestChampionsUrl(String locale) {
        return generateRequestUrl("all", locale);
    }
}
