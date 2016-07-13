package com.nodlee.amumu.util;

/**
 * Created by nodlee on 16/7/11.
 */
public class LocaleLibrary {
    private static final Object mLock = new Object();

    private static LocaleLibrary mInstance;

    // 扩展：在此处加入新语言
    private Entry[] sEntries = {
            new Entry("中文简体", "zh_CN"),
            new Entry("中文繁体", "zh_TW"),
            new Entry("English", "en_US"),
            new Entry("조선말", "ko_KR"),
    };

    // 单例模式确保所有对象引用共用一个语言数据源
    public static LocaleLibrary getInstance() {
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocaleLibrary();
            }
            return mInstance;
        }
    }

    /**
     * 获取方言key数组
     * @return
     */
    public String[] toKeyArray() {
        String[] keyArray = new String[sEntries.length];
        for (int i = 0; i < sEntries.length; i++) {
            keyArray[i] = sEntries[i].key;
        }
        return keyArray;
    }

    /**
     * 获取方言数组中指定位置的数据
     * @param index 索引位置
     * @return
     */
    public Entry get(int index) {
        if (index < 0 || index >= sEntries.length) {
            throw new IllegalArgumentException("wrong index");
        }
        return sEntries[index];
    }

    public class Entry {
        public String key;
        public String value;

        public Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
