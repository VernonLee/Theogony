package com.nodlee.theogony.core;


import com.nodlee.theogony.bean.DragonData;
import com.nodlee.theogony.utils.LogHelper;
import com.nodlee.theogony.thirdparty.realm.RealmProvider;

import io.realm.Realm;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class DragonDataManager {
    private static DragonDataManager sManager;

    public static DragonDataManager getInstance() {
        if (sManager == null) {
            sManager = new DragonDataManager();
        }
        return sManager;
    }

    private DragonDataManager() {
    }

    public DragonData getDefault() {
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            DragonData result = realm.where(DragonData.class).findFirst();
            return result;
        } finally {
            realm.close();
        }
    }

    public void setOutDate() {
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            realm.beginTransaction();
            DragonData dragonData = getDefault();
            dragonData.setOutDate(true);
            realm.commitTransaction();
            LogHelper.LOGD("数据已过时");
        } finally {
            realm.close();
        }
    }

    public boolean isOutDate() {
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            DragonData result = realm.where(DragonData.class).findFirst();
            return result != null && result.isOutDate();
        } finally {
            realm.close();
        }
    }

    public String getVersion() {
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            DragonData result = realm.where(DragonData.class).findFirst();
            return result != null ? result.getVersion() : null;
        } finally {
            realm.close();
        }
    }
}
