package com.nodlee.riotgames;

import android.net.Uri;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class RiotGamesImageAPI {

    private String generateRequestImageUrl(String version, String imageType, String imageName) {
        return Uri.parse(String.format(Constants.BASE_ENDPOINT_IMAGE, version)).buildUpon()
                .appendPath(imageType)
                .appendPath(imageName)
                .build().toString();
    }

    /**
     * 获取英雄头像缩略图URL
     * @param imageName 英雄图片名称
     * @return
     */
    public String buildThumbnailUrl(String version, String imageName) {
        return generateRequestImageUrl(version, "", imageName);
    }

    /**
     * 获取英雄插画URL
     * @param imageName 英雄图片名称
     * @return
     */
    public String buildSplashUrl(String imageName) {
        return generateRequestImageUrl("", "splash", imageName);
    }

    /**
     * 获取英雄加载页图URL
     * @param imageName 英雄图片名称
     * @return
     */
    public String buildLoadingUrl(String imageName) {
        return generateRequestImageUrl("", "loading", imageName);
    }
}
