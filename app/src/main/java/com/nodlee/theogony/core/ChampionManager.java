package com.nodlee.theogony.core;

import android.text.TextUtils;

import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.DragonData;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.bean.Spell;
import com.nodlee.theogony.utils.RealmProvider;
import com.nodlee.theogony.utils.RiotGameUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.attr.tag;
import static io.realm.Realm.getDefaultInstance;

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
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            RealmResults<Champion> results = realm
                    .where(Champion.class).findAll();
            return realm.copyFromRealm(results);
        } finally {
            realm.close();
        }
    }

    public Champion get(int id) {
        if (id <= 0) return null;
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            return realm.where(Champion.class)
                    .equalTo("id", id).findFirst();
        } finally {
            realm.close();
        }
    }

    public List<Champion> queryByTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return getAll();
        }
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            RealmResults<Champion> results = realm
                    .where(Champion.class).contains("tagsc", tag).findAll();
            return realm.copyFromRealm(results);
        } finally {
            realm.close();
        }
    }

    public List<Champion> queryByKeyWord(String query) {
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            RealmResults<Champion> results = realm.where(Champion.class)
                    .beginGroup()
                    .contains("tagsc", query)
                    .or()
                    .contains("name", query)
                    .or()
                    .contains("title", query)
                    .findAll();
            return realm.copyFromRealm(results);
        } finally {
            realm.close();
        }
    }
}
