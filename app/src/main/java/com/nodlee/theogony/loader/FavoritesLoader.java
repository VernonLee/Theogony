package com.nodlee.theogony.loader;

import android.content.Context;

import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.core.FavoritesManager;

import java.util.List;

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
