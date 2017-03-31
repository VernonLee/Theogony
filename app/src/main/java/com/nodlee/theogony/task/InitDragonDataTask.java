package com.nodlee.theogony.task;

import android.os.AsyncTask;
import android.os.Bundle;

import com.nodlee.theogony.bean.DragonData;
import com.nodlee.theogony.core.ApiImpl;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.LogHelper;
import com.nodlee.theogony.utils.RiotGameUtils;

/**
 * 作者：nodlee
 * 时间：2017/3/20
 * 说明：初始化英雄联盟数据源任务
 */

public class InitDragonDataTask extends AsyncTask<Bundle, String, Boolean> {

    public static final String EXTRA_APP_KEY = "app_key";
    public static final String EXTRA_LANGUAGE_CODE = "language_code";

    @Override
    protected Boolean doInBackground(Bundle... params) {
        Bundle args = params[0];
        if (args == null) {
            return false;
        }

        String languageCode;
        if (args.containsKey(EXTRA_LANGUAGE_CODE)) {
            languageCode = args.getString(EXTRA_LANGUAGE_CODE);
        } else {
            languageCode = AndroidUtils.getSystemLocale();
        }

        String appKey;
        if (args.containsKey(EXTRA_APP_KEY)) {
            appKey = args.getString(EXTRA_APP_KEY);
        } else {
            LogHelper.LOGE("未指定APP_KEY");
            return false;
        }

        final long start = System.currentTimeMillis();

        ApiImpl impl = ApiImpl.getInstance();
        String dragonDataUrl = RiotGameUtils.createDragonDataUrl(languageCode, appKey);
        LogHelper.LOGW("请求数据源：" + dragonDataUrl);

        // 第一阶段
        publishProgress("初始化...");

        String jsonResult = impl.request(dragonDataUrl);
        if (jsonResult == null) {
            LogHelper.LOGW("jsonResult为空");
            return false;
        }

        // 第二阶段
        publishProgress("解析数据...");

        DragonData dragonData = impl.parseJsonWithGson(jsonResult);
        if (dragonData == null) {
            LogHelper.LOGW("championData为空");
            return false;
        }
        dragonData.setLanguageCode(languageCode);
        dragonData.setOutDate(false);

        // 第三阶段
        publishProgress("写入数据...");

        boolean success = impl.writeToRealmDataBase(dragonData);
        LogHelper.LOGW("数据写入：" + (success ? "成功" : "失败"));

        // 统计三个阶段耗时
        LogHelper.LOGD("耗时总计：" + (System.currentTimeMillis() - start) + "ms");

        return success;
    }
}
