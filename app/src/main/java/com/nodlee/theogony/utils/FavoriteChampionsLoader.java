package com.nodlee.theogony.utils;

import android.content.Context;

import com.nodlee.theogony.db.DatabaseOpenHelper;
import com.nodlee.theogony.db.FavoriteChampionManager;

/**
 * Created by Vernon Lee on 15-12-8.
 */
public class FavoriteChampionsLoader extends SQLiteCursorLoader {
    private Context mCtx = null;

    public FavoriteChampionsLoader(Context context) {
        super(context);
        mCtx = context;
    }

    @Override
    public DatabaseOpenHelper.ChampionCursor loadCursor() {
        return FavoriteChampionManager.getInstance(mCtx).getFavorites();
    }
}
