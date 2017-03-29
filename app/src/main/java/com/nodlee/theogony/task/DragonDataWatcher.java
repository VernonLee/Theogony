package com.nodlee.theogony.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.nodlee.theogony.core.ApiImpl;
import com.nodlee.theogony.core.DragonDataManager;
import com.nodlee.theogony.utils.RiotGameUtils;

/**
 * 作者：nodlee
 * 时间：2017/3/29
 * 说明：
 */

public class DragonDataWatcher extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params) {
        final String appKey = params[0];
        if (TextUtils.isEmpty(appKey)) {
            cancel(true);
            return null;
        }

        final String version = DragonDataManager.getInstance().getVersion();
        String versionCheckUrl = RiotGameUtils.createVersionCheckUrl(appKey);
        try {
            String result = ApiImpl.getInstance().request(versionCheckUrl);
            if (result == null) {
                return null;
            }
            Gson gson = new Gson();
            JsonArray array = gson.fromJson(result, JsonArray.class);
            String newVersion = array.get(getMaxNumberIndex(array)).getAsString();
            boolean needUpdate = compareVersion(version, newVersion);
            if (needUpdate) {
                DragonDataManager.getInstance().setOutDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * TODO
     * 获取JSONArray中数字最大值下标
     *
     * @param array
     * @return
     */
    private int getMaxNumberIndex(JsonArray array) {
        return 0;
    }

    /**
     * TODO
     * 判断两个版本string大小
     *
     * @param oldVersion
     * @param newVersion
     * @return true 表示 newVersion大于oldVersion
     * false表示 newVersion小于等于oldVersion
     */
    private boolean compareVersion(String oldVersion, String newVersion) {
        if (TextUtils.isEmpty(oldVersion) || TextUtils.isEmpty(newVersion)) {
            return false;
        }
        return !oldVersion.equals(newVersion);
    }
}
