/*
 * Copyright (c) 2015 Vernon Lee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.nodlee.theogony.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.nodlee.theogony.utils.Constants;

/**
 * Created by Vernon Lee on 15-12-8.
 */
public class FavoriteChampionManager {
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

    public boolean addFavorite(int cid) {
        if (isFavorite(cid)) return true;

        long newId = sHelper.insertFavorite(cid);
        if (newId != -1) {
            if (DEBUG) Log.i(TAG, "添加喜爱:cid=" + cid);
            mAppCtx.getContentResolver().notifyChange(Constants.Favorite.CONTENT_URI, null, false);
            return true;
        }
        return false;
    }

    public boolean deleteFavorite(int cid) {
        if (!isFavorite(cid)) return true;

        int affectedRows = sHelper.deleteFavorite(cid);
        if (affectedRows > 0) {
            if (DEBUG) Log.i(TAG, "取消喜爱:cid=" + cid);
            mAppCtx.getContentResolver().notifyChange(Constants.Favorite.CONTENT_URI, null, false);
            return true;
        }
        return false;
    }

    public boolean isFavorite(int cid) {
        Cursor cursor = sHelper.getFavorite(cid);
        return cursor.moveToFirst();
    }

    public DatabaseOpenHelper.ChampionCursor getFavorites() {
        return sHelper.getFavorites();
    }
}
