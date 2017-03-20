package com.nodlee.theogony.utils;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 作者：nodlee
 * 时间：2017/3/19
 * 说明：
 */

public class RealmProvider {

    private static RealmProvider sProvider;

    private RealmProvider() {
    }

    public static RealmProvider getInstance() {
        if (sProvider == null) {
            sProvider = new RealmProvider();
        }
        return sProvider;
    }

    public void init(Context context) {
        Realm.init(context);
    }

    public Realm getRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(config);
    }

    public boolean isEmpty() {
        return getRealm().isEmpty();
    }
}
