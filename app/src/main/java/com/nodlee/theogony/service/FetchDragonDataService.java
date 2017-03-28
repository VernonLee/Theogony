package com.nodlee.theogony.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.utils.LogHelper;

import java.util.ArrayList;


/**
 * 作者：nodlee
 * 时间：16/10/4
 * 说明：源数据更新服务
 */
public class FetchDragonDataService extends IntentService {
    public FetchDragonDataService() {
        super("FetchDragonDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String locale = intent.getStringExtra("locale");
        String oldVersion = intent.getStringExtra("version");

        if (TextUtils.isEmpty(locale)) {
            LogHelper.LOGW("更新失败, locale为空");
            return;
        }


    }

    private void sendNotification(String version) {
        Notification.Builder mBuilder = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.app_logo)
                .setContentTitle("数据源更新")
                .setContentText("数据源已更新至最新版本" + version)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }

    private void writeToDatabase(final ArrayList<Champion> champions) {

    }
}
