package com.nodlee.theogony.core;

import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.utils.RealmProvider;

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
        realm.beginTransaction();
        Skin skin = realm.where(Skin.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return skin;
    }
}
