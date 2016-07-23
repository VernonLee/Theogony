package com.nodlee.theogony.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.nodlee.amumu.bean.Champion;

import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class ChampionManager implements Manager<Champion> {
    private static final String TAG = ChampionManager.class.getName();
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

    public DatabaseOpenHelper.ChampionCursor getAll(String selection, String[] selectionArgs) {
        return sHelper.queryChampions(selection, selectionArgs);
    }

    @Override
    public boolean add(Champion champion) {
        return sHelper.insertChampion(champion) > 0;
    }

    @Override
    public boolean add(ArrayList<Champion> champions) {
        if (champions == null || champions.size() == 0)
            return false;

        sHelper.insertChampions(champions);
        return true;
    }

    @Override
    public boolean delete(Champion champion) {
        if (get(champion.getId()) == null) {
            Log.d(TAG, "数据 champion id=" + champion.getId() + "不存在");
            return false;
        }
        return sHelper.deleteChampion(champion.getId()) > 0;
    }

    @Override
    public void deleteAll() {
        sHelper.deleteChampions();
    }

    @Override
    public Cursor getAll() {
        return sHelper.queryChampions(null, null);
    }

    @Override
    public Champion get(int cid) {
        DatabaseOpenHelper.ChampionCursor cursor = sHelper.queryChampion(cid);
        if (cursor.moveToFirst()) {
            return cursor.getChampion();
        }
        return null;
    }
}
