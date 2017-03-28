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
        if (realm.isEmpty()) {
            return null;
        }
        RealmResults<Champion> results = realm.where(Champion.class).findAll();
        return realm.copyFromRealm(results);
    }

    public Champion get(int id) {
        if (id <= 0) return null;
        Realm realm = RealmProvider.getInstance().getRealm();
        return realm.where(Champion.class).equalTo("id", id).findFirst();
    }

    public List<Champion> queryByTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return getAll();
        }
        Realm realm = RealmProvider.getInstance().getRealm();
        RealmResults<Champion> results = realm.where(Champion.class)
                .contains("tagsc", tag)
                .findAll();
        return realm.copyFromRealm(results);
    }

    public List<Champion> queryByKeyWord(String query) {
        Realm realm = RealmProvider.getInstance().getRealm();
        RealmResults<Champion> results = realm.where(Champion.class)
                .beginGroup()
                .contains("tagsc", query)
                .or()
                .contains("name", query)
                .or()
                .contains("title", query)
                .findAll();
        return realm.copyFromRealm(results);
    }

    public Skin getDefaultSkin(int championId) {
        Champion champion = get(championId);
        if (champion == null)
            return null;

        List<Skin> skins = champion.getSkins();
        if (skins.size() > 0) {
            return skins.get(0);
        }
        return null;
    }

}
