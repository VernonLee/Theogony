package com.nodlee.amumu.uri;

import android.net.Uri;

/**
 * Created by nodlee on 16/6/18.
 */
public class UriFactory {
    // 数据类型
    private static final String CHAMP_DATA = "all";
    // 数据服务器地区
    private static final String REGION = "na";
    // 数据版本
    private static final String VERSION = "v1.2";
    // 数据地址
    private static final String BASE_ENDPOINT = "https://global.api.pvp.net/api/lol/static-data/%1$s/%2$s/champion";
    // 皮肤图片请求地址
    private static final String BASE_ENDPOINT_IMAGE = "http://ddragon.leagueoflegends.com/cdn/%s/img/champion/";

    /**
     * 获取英雄全部信息据列表的URL
     *
     * @param locale 语言版本
     * @return
     */
    public static Uri createChampionstUri(String appKey, String locale) {
        return Uri.parse(String.format(BASE_ENDPOINT, REGION, VERSION)).buildUpon()
                .appendQueryParameter("locale", locale)
                .appendQueryParameter("champData", CHAMP_DATA)
                .appendQueryParameter("api_key", appKey)
                .build();
    }

    private static Uri generateRequestImageUrl(String version, String imageType, String imageName) {
        return Uri.parse(String.format(BASE_ENDPOINT_IMAGE, version)).buildUpon()
                .appendPath(imageType)
                .appendPath(imageName)
                .build();
    }

    /**
     * 获取英雄头像缩略图URL
     * @param imageName 英雄图片名称
     * @return
     */
    public static Uri createThumbnailUri(String version, String imageName) {
        return generateRequestImageUrl(version, "", imageName);
    }

    /**
     * 获取英雄插画URL
     * @param imageName 英雄图片名称
     * @return
     */
    public static Uri createSplashUri(String imageName) {
        return generateRequestImageUrl("", "splash", imageName);
    }

    /**
     * 获取英雄加载页图URL
     * @param imageName 英雄图片名称
     * @return
     */
    public static Uri createLoadingUri(String imageName) {
        return generateRequestImageUrl("", "loading", imageName);
    }
}
