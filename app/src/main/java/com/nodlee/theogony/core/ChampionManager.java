package com.nodlee.theogony.core;

import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.ChampionData;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 作者：nodlee
 * 时间：2017/3/19
 * 说明：
 */

public class ChampionManager {
    private static ChampionManager sManager;

    private ChampionManager() {
    }

    public static ChampionManager getInstance() {
        if (sManager == null) {
            sManager = new ChampionManager();
        }
        return sManager;
    }

    public List<Champion> getAll() {
        ChampionDataManager championDataManager = ChampionDataManager.getInstance();
        ChampionData data = championDataManager.get();
        if (data != null) {
            return data.getData();
        }
        return null;
    }

    public Champion get(int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Champion> results = realm.where(Champion.class)
                .equalTo("id", id)
                .findAll();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public List<Champion> get(String tag) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Champion> results = realm.where(Champion.class)
                .like("tagsc", tag)
                .findAll();
        if (results != null) {
            return realm.copyFromRealm(results);
        }
        return null;
    }

    public List<Champion> query(String query) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Champion> results = realm.where(Champion.class)
                .beginGroup()
                .like("tagsc", query)
                .or()
                .like("name", query)
                .or()
                .like("title", query)
                .findAll();
        if (results != null) {
            return realm.copyFromRealm(results);
        }
        return null;
    }
}
