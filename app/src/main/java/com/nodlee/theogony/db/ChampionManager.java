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
