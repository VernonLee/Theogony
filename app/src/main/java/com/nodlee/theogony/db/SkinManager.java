package com.nodlee.theogony.db;

import android.content.Context;
import android.util.Log;

import com.nodlee.theogony.bean.Skin;

import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class SkinManager {
    private static final String TAG = "SkinManager";
    private static final boolean DEBUG = false;

    private static SkinManager sManager;
    private static DatabaseOpenHelper sHelper;

    public static SkinManager getInstance(Context context) {
        if (sManager == null) {
            sManager = new SkinManager(context.getApplicationContext());
        }
        return sManager;
    }

    private SkinManager(Context appContext) {
        sHelper = new DatabaseOpenHelper(appContext);
    }

    public void insertSkins(ArrayList<Skin> skins) {
        if (skins == null || skins.size() == 0)
            return;

        sHelper.insertSkins(skins);
    }

//    private static ArrayList<Skin> getSkins(int lid) {
//        ArrayList<Skin> legendRoles = new ArrayList<>();
//
//        DatabaseOpenHelper.SkinCursor cursor = sHelper.querySkins(lid);
//        while (cursor.moveToNext()) {
//            legendRoles.add(cursor.getSkin());
//        }
//        return legendRoles;
//    }

    public DatabaseOpenHelper.SkinCursor getSkins(int cid) {
        return sHelper.querySkins(cid);
    }

    public Skin getSkin(int sid) {
        DatabaseOpenHelper.SkinCursor cursor = sHelper.querySkin(sid);
        if (cursor.moveToFirst()) {
            return cursor.getSkin();
        }
        return null;
    }

    public Skin getSkin(int cid, int num) {
        DatabaseOpenHelper.SkinCursor cursor = sHelper.querySkin(cid, num);
        if (cursor.moveToFirst()) {
            if (DEBUG) Log.i(TAG, "查询单个皮肤：cid=" + cid + "num:" + num);
            return cursor.getSkin();
        }
        return null;
    }

    public Skin getDefaultSkin(int cid) {
        return getSkin(cid, 0);
    }

    public int getSkinCount(int cid) {
        DatabaseOpenHelper.SkinCursor cursor = getSkins(cid);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }
}
