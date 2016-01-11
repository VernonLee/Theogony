/*
 * Copyright (c) 2015 Vernon Lee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
