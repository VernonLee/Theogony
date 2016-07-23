package com.nodlee.theogony.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.nodlee.amumu.bean.Skin;

import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class SkinManager implements Manager<Skin> {
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
        DatabaseOpenHelper.SkinCursor cursor = (DatabaseOpenHelper.SkinCursor) getAll(cid);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    @Override
    public boolean add(Skin skin) {
        return sHelper.insertSkin(skin) > 0;
    }

    @Override
    public boolean add(ArrayList<Skin> skins) {
        if (skins == null || skins.size() == 0)
            return false;

        sHelper.insertSkins(skins);
        return true;
    }

    @Override
    public boolean delete(Skin skin) {
        if (get(skin.getId()) != null) {
            return sHelper.deleteSkin(skin.getId()) > 0;
        }
       return false;
    }

    @Override
    public void deleteAll() {
        sHelper.deleteSkins();
    }

    @Override
    public Cursor getAll() {
        return sHelper.querySkins();
    }

    public Cursor getAll(int cid) {
        return sHelper.querySkins(cid);
    }

    @Override
    public Skin get(int sid) {
        DatabaseOpenHelper.SkinCursor cursor = sHelper.querySkin(sid);
        if (cursor.moveToFirst()) {
            return cursor.getSkin();
        }
        return null;
    }
}
