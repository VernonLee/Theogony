package com.nodlee.theogony.loader;

import android.content.Context;
import android.util.Log;

import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Favorites;
import com.nodlee.theogony.core.ChampionManager;
import com.nodlee.theogony.core.FavoritesManager;
import com.nodlee.theogony.utils.RealmProvider;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Vernon Lee on 15-12-8.
 */
public class FavoritesLoader extends DataLoader<List<Champion>> {

    public FavoritesLoader(Context context) {
        super(context);
    }

    @Override
    public List<Champion> loadInBackground() {
        return FavoritesManager.getInstance().getAll();
    }
}
