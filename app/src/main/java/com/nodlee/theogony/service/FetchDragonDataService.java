package com.nodlee.theogony.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.amumu.bean.Skin;
import com.nodlee.amumu.champions.ChampionParser;
import com.nodlee.amumu.champions.ChampionsRequester;
import com.nodlee.theogony.R;
import com.nodlee.theogony.core.ChampionDataManager;
import com.nodlee.theogony.utils.LogHelper;
import com.nodlee.theogony.utils.UserUtils;

import java.util.ArrayList;

/**
 * 作者：nodlee
 * 时间：16/10/4
 * 说明：源数据更新服务
 */
public class FetchDragonDataService extends IntentService {
    private static final String TAG = "FetchDragonDataService";

    public FetchDragonDataService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String locale = intent.getStringExtra("locale");
        String oldVersion = intent.getStringExtra("version");

        if (TextUtils.isEmpty(locale)) {
            LogHelper.LOGW(TAG, "更新失败, locale为空");
            return;
        }

        ChampionParser parser = new ChampionsRequester().syncRequest(locale);
        if (parser != null) {
            String newVersion = parser.getVersion();
            if (newVersion != null && !newVersion.equals(oldVersion) && parser.getData() != null) {
                writeToDatabase(parser.getData());
                UserUtils.setLolStaticDataVerion(getApplicationContext(), newVersion);
                sendNotification(newVersion);
            }
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Skin> skins = new ArrayList<Skin>();
                ChampionDataManager.getInstance(getApplicationContext()).add(champions);
                for (Champion champion : champions) {
                    skins.addAll(champion.getSkins());
                }
                SkinManager.getInstance(getApplicationContext()).add(skins);
            }
        }).start();
    }
}
