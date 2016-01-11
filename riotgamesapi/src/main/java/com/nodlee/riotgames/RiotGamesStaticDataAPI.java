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
