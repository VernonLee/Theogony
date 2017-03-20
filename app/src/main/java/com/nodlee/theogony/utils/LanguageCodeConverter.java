package com.nodlee.theogony.utils;

import android.text.TextUtils;

/**
 * 作者：nodlee
 * 时间：2017/3/20
 * 说明：语言code转换器（iso639 -> script language code）
 */

public class LanguageCodeConverter {

    public static String convert(String iso639Code) {
        if (TextUtils.isEmpty(iso639Code)) {
            throw new IllegalArgumentException("参数不能为空");
        }

        switch (iso639Code) {
            case "zh": // 中文
                return "zh_CN";
            case "en": // 英文
                return "en_US";
            case "ko": // 韩语
                return "ko_KR";
            default:
                return "en_US";
        }
    }
}
