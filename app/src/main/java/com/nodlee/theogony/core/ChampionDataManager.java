package com.nodlee.theogony.core;


import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.ChampionData;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class ChampionDataManager {
    private static ChampionDataManager sManager;

    public static ChampionDataManager getInstance() {
        if (sManager == null) {
            sManager = new ChampionDataManager();
        }
        return sManager;
    }

    private ChampionDataManager() {
    }

    public ChampionData get() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ChampionData> result = realm.where(ChampionData.class).findAll();
        if (result != null && result.size() > 0) {
            return result.get(result.size() - 1);
        }
        return null;
    }
}
