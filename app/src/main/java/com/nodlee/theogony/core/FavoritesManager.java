package com.nodlee.theogony.core;

import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Favorites;
import com.nodlee.theogony.thirdparty.realm.RealmProvider;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Vernon Lee on 15-12-8.
 */
public class FavoritesManager {
    private static FavoritesManager sFavoriteChampionManager;

    private FavoritesManager() {
    }

    public static FavoritesManager getInstance() {
        if (sFavoriteChampionManager == null) {
            sFavoriteChampionManager = new FavoritesManager();
        }
        return sFavoriteChampionManager;
    }

    public List<Champion> getAll() {
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            RealmResults<Favorites> results = realm.where(Favorites.class).findAll();
            return toChampions(realm.copyFromRealm(results));
        } finally {
            realm.close();
        }
    }

    private List<Champion> toChampions(List<Favorites> data) {
        if (data == null) return null;

        List<Champion> champions = new ArrayList<>();
        for (Favorites favorites : data) {
            champions.add(favorites.getChampion());
        }
        return champions;
    }

    public void add(final Champion champion) {
        if (champion == null) return;
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            realm.beginTransaction();

            int primaryKey = 0;
            Number number = realm.where(Favorites.class).max("id");
            if (number != null) {
                primaryKey = number.intValue() + 1;
            } else {
                primaryKey = 1;
            }
            Favorites favorites = realm.createObject(Favorites.class, primaryKey);
            favorites.setChampionId(champion.getId());
            favorites.setChampion(champion);

            realm.commitTransaction();
        } finally {
            realm.close();
        }
    }

    public void remove(final Champion champion) {
        if (champion == null) return;
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            realm.beginTransaction();
            Favorites favorites = realm.where(Favorites.class)
                    .equalTo("championId", champion.getId())
                    .findFirst();
            if (favorites != null) {
                favorites.deleteFromRealm();
            }
            realm.commitTransaction();
        } finally {
            realm.close();
        }
    }

    public boolean isFavorite(Champion champion) {
        if (champion == null) return false;
        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            RealmResults<Favorites> results = realm.where(Favorites.class)
                    .equalTo("championId", champion.getId()).findAll();
            return results != null && results.size() >= 1;
        } finally {
            realm.close();
        }
    }
}
