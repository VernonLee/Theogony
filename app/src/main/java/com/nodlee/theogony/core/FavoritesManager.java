package com.nodlee.theogony.core;

import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Favorites;
import com.nodlee.theogony.utils.RealmProvider;

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
        RealmResults<Favorites> results = realm.where(Favorites.class).findAll();
        if (results == null) return null;

        List<Champion> champions = new ArrayList<>();
        for (Favorites favorites : results) {
            champions.add(favorites.getChampion());
        }
        return champions;
    }

    public boolean add(Champion champion) {
        return true;
    }

    public boolean remove(Champion champion) {
        return true;
    }

    public boolean isFavorite(Champion champion) {
        return false;
    }
}
