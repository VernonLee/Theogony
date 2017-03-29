package com.nodlee.theogony.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * 作者：nodlee
 * 时间：2017/3/29
 * 说明：
 */

public class CacheManager {

    private static File getImageCacheDir(Context context) {
        return context.getCacheDir();
    }

    public static double getCacheSize(Context context) {
        if (context == null) return 0;

        File cacheDir = getImageCacheDir(context);
        return FileManager.getDirSize(cacheDir);
    }

    public static void cleanCache(Context context) {
        File file = getImageCacheDir(context);
        if (file != null && file.exists()) {
            FileManager.cleanFile(file);
        }
    }

    private static class FileManager {

        private static double getDirSize(File directory) {
            if (directory == null) {
                return 0;
            }
            if (!directory.exists()) {
                Log.d("xxx", "文件夹不存在:" + directory.getPath());
                return 0;
            }

            if (directory.isDirectory()) {
                File[] children = directory.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {
                double size = (double) directory.length() / 1024 / 1024; // M为单位
                return size;
            }
        }

        private static void cleanFile(File directory) {
            if (directory == null || !directory.exists()) {
                return;
            }
            if (!directory.exists() || directory.isFile()) {
                Log.d("xxx", "文件夹不存在或不是文件夹:" + directory.getPath());
                return;
            }

            Log.d("xxx", "清空文件夹：" + directory.getAbsolutePath());

            File[] directoryFiles = directory.listFiles();
            for (File f : directoryFiles) {
                if (f.isFile()) {
                    Log.d("xxx", "删除：" + f.getAbsolutePath());
                    f.delete();
                }

                if (f.isDirectory()) {
                    File[] files = f.listFiles();
                    if (files.length == 0) {
                        f.delete();
                    } else {
                        cleanFile(f);
                    }
                }
            }
        }
    }
}
