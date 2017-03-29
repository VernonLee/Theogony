package com.nodlee.theogony.core;


import com.nodlee.theogony.bean.DragonData;
import com.nodlee.theogony.utils.RealmProvider;

import io.realm.Realm;
import io.realm.RealmResults;

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
}
