package com.nodlee.theogony.utils;

import android.content.Context;

import java.io.File;

/**
 * 作者：nodlee
 * 时间：16/8/16
 * 说明：
 */
public class CacheManager {

    /**
     * 获取缓存大小
     * @return
     */
    public static String getCacheSize(Context context) {
        float size = -1;
        File cacheFile = context.getExternalCacheDir();
        if (cacheFile.exists()) {
            size = (float) Math.ceil(getFileSize(cacheFile));
        }
        return size > 0 ? String.format("%fM", size) : "";
    }

    /**
     * 获取目录尺寸，单位：M
     * @param file
     * @return
     */
    private static double getFileSize(File file) {
        if (!file.exists()) return 0;

        if (file.isDirectory()) {
            File[] children = file.listFiles();
            double size = 0;
            for (File f : children)
                size += getFileSize(f);
            return size;
        } else {
            return (double) file.length() / 1024 / 1024;
        }
    }

    /**
     * 删除目录下面所有文件
     * @param file
     */
    private static boolean delete(File file) {
        if (!file.exists()) return false;

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (File file1 : childFiles) {
                delete(file1);
            }
        } else {
           file.delete();
        }
        return true;
    }

    /**
     * 清空缓存 sdcard中cache目录下
     */
    public static boolean clean(Context context) {
        File rootFile = context.getExternalCacheDir();
        return delete(rootFile);
    }
}
