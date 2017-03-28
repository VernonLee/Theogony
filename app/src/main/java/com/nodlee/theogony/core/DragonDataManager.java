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
        RealmResults<DragonData> result = realm.where(DragonData.class).findAll();
        if (result != null && result.size() > 0) {
            return result.get(result.size() - 1);
        }
        return null;
    }
}
