package com.nodlee.theogony.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.theogony.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-12-8.
 */
public class FavoriteChampionManager implements Manager<Champion> {
    private static final String TAG = "FavoriteChampionManager";
    private static final boolean DEBUG = true;

    private Context mAppCtx;
    private static FavoriteChampionManager sFavoriteChampionManager;
    private static DatabaseOpenHelper sHelper;

    private FavoriteChampionManager(Context appContext) {
        mAppCtx = appContext;
        sHelper = new DatabaseOpenHelper(appContext);
    }

    public static FavoriteChampionManager getInstance(Context context) {
        if (sFavoriteChampionManager == null) {
            sFavoriteChampionManager = new FavoriteChampionManager(context.getApplicationContext());
        }

        return sFavoriteChampionManager;
    }

    public boolean isFavorite(int cid) {
        Cursor cursor = sHelper.getFavorite(cid);
        return cursor.moveToFirst();
    }

    @Override
    public boolean add(Champion champion) {
        if (isFavorite(champion.getId())) return true;

        long newId = sHelper.insertFavorite(champion.getId());
        if (newId != -1) {
            if (DEBUG) Log.i(TAG, "添加喜爱:cid=" + champion.getId());
            mAppCtx.getContentResolver().notifyChange(Constants.Favorite.CONTENT_URI, null, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(ArrayList<Champion> t) {
        /** 暂不支持多个添加 */
        return false;
    }

    @Override
    public boolean delete(Champion champion) {
        if (!isFavorite(champion.getId())) return true;

        int affectedRows = sHelper.deleteFavorite(champion.getId());
        if (affectedRows > 0) {
            if (DEBUG) Log.i(TAG, "取消喜爱:cid=" + champion.getId());
            mAppCtx.getContentResolver().notifyChange(Constants.Favorite.CONTENT_URI, null, false);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        sHelper.deleteFavorites();
    }

    @Override
    public Cursor getAll() {
        return sHelper.getFavorites();
    }

    @Override
    public Champion get(int cid) {
        if (isFavorite(cid)) {
            return sHelper.queryChampion(cid).getChampion();
        }
        return null;
    }
}
