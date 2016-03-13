package com.nodlee.riotgames;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class Locale {
    /**
     * 中文简体
     */
    public static final int CN = 0;
    /**
     * 中文繁体
     */
    public static final int TW = 1;
    /**
     * English
     */
    public static final int US = 2;
    /**
     * 조선말
     */
    public static final int KR = 3;
    /**
     * 默认语言为中文
     */
    public static final int DEFAULT_LOCALE = CN;

    public static String[] sLocaleCodes = {
            "zh_CN",
            "zh_TW",
            "en_US",
            "ko_KR"
    };

    public static String[] sLocaleNames = {
            "中文简体",
            "中文繁体",
            "English",
            "조선말"
    };

    /**
     * 默认为中文简体
     *
     * @return
     */
    public static String getDefaultLocaleCode() {
        return getLocaleCode(DEFAULT_LOCALE);
    }

    public static String getLocaleCode(int localeTypeId) {
        if (localeTypeId >= 0 && localeTypeId < sLocaleCodes.length) {
            return sLocaleCodes[localeTypeId];
        } else {
            return null;
        }
    }

    public static String getLocalName(int localeTypeId) {
        if (localeTypeId >= 0 && localeTypeId < sLocaleNames.length) {
            return sLocaleNames[localeTypeId];
        } else {
            return null;
        }
    }

    public static String getLocaleCode(String localeName) {
        return sLocaleCodes[getArrayItemIndex(sLocaleNames, localeName)];
    }

    public static String getLocalName(String localeCode) {
        return sLocaleNames[getArrayItemIndex(sLocaleCodes, localeCode)];
    }


    public static String[] getLocales() {
        return sLocaleCodes;
    }

    public static String[] getLocalesNames() {
        return sLocaleNames;
    }

    /**
     * 获取数组中某个值的下标
     *
     * @param array
     * @param destStr
     * @return
     */
    private static int getArrayItemIndex(String[] array, String destStr) {
        int index = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(destStr)) {
                index = i;
                break;
            }
        }

        return index;
    }
}
