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

import com.nodlee.theogony.bean.Champion;

import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class ChampionManager {
    private static ChampionManager sManager;
    private static DatabaseOpenHelper sHelper;

    public static ChampionManager getInstance(Context context) {
        if (sManager == null) {
            sManager = new ChampionManager(context.getApplicationContext());
        }
        return sManager;
    }

    private ChampionManager(Context appContext) {
        sHelper = new DatabaseOpenHelper(appContext);
    }

    public void insertChampions(ArrayList<Champion> champions) {
        if (champions == null || champions.size() == 0)
            return;

        sHelper.insertChampions(champions);
    }

//    public ArrayList<Champion> getChampions() {
//        ArrayList<Champion> champions = new ArrayList<>();
//
//        DatabaseOpenHelper.ChampionCursor cursor = sHelper.queryChampions();
//        while (cursor.moveToNext()) {
//            champions.add(cursor.getChampion());
//        }
//        return champions;
//    }

    public DatabaseOpenHelper.ChampionCursor getChampions(String selection, String[] selectionArgs) {
        return sHelper.queryChampions(selection, selectionArgs);
    }

    public Champion getChampion(int cid) {
        DatabaseOpenHelper.ChampionCursor cursor = sHelper.queryChampions(cid);
        if (cursor.moveToFirst()) {
            return cursor.getChampion();
        }
        return null;
    }
}
