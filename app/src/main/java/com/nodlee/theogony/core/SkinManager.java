package com.nodlee.theogony.core;

import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.utils.RealmProvider;

import java.util.List;

import io.realm.Realm;

/**
 * 作者：nodlee
 * 时间：2017/3/25
 * 说明：
 */

public class SkinManager {

    private static SkinManager sManager;

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        if (sManager == null) {
            sManager = new SkinManager();
        }
        return sManager;
    }

    public Skin get(int id) {
        if (id <= 0) {
            return null;
        }

        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            realm.beginTransaction();
            Skin skin = realm.where(Skin.class).equalTo("id", id).findFirst();
            realm.commitTransaction();
            return skin;
        } finally {
            realm.close();
        }
    }

    public Skin getDefaultSkin(Champion champion) {
        if (champion == null)
            return null;

        List<Skin> skins = champion.getSkins();
        if (skins.size() > 0) {
            return skins.get(0);
        }
        return null;
    }
}
