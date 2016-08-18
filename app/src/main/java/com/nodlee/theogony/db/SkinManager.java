package com.nodlee.theogony.db;

import android.content.Context;
import android.util.Log;

import com.nodlee.amumu.bean.Skin;

import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class SkinManager implements Manager<Skin> {
    private static final String TAG = "SkinManager";
    private static final boolean DEBUG = false;
    private static final int PAGE_COUNT = 10;

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
    public DatabaseOpenHelper.SkinCursor getAll() {
        return sHelper.querySkins();
    }

    public DatabaseOpenHelper.SkinCursor getAll(int cid) {
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

    /**
     * 分页获取数据
     * @param pageId
     * @return
     */
    public DatabaseOpenHelper.SkinCursor getSkins(int pageId) {
        final int totalPage = getTotalPage();
        if (totalPage > 0) {
            if (pageId <= 0 || pageId > totalPage) {
                Log.d(TAG, "页码pageId错误,pageId=" + pageId);
                return null;
            }
            final int offset = (pageId - 1) * PAGE_COUNT;
            return sHelper.querySkins(offset, PAGE_COUNT);
        }
        return null;
    }

    /**
     * 获取数据总页数
     * @return
     */
    public int getTotalPage() {
        int totalPage = -1;
        ArrayList<Skin> skins = cursorToArrayList(getAll());
        if (skins != null && skins.size() > 0) {
            final int total = skins.size();
            totalPage = (int) Math.ceil((double) total / PAGE_COUNT);
        }
        return totalPage;
    }

    public static ArrayList<Skin> cursorToArrayList(DatabaseOpenHelper.SkinCursor cursor) {
        ArrayList<Skin> skins = null;
        if (cursor != null && cursor.moveToFirst()) {
            skins = new ArrayList<>();
            do {
                skins.add(cursor.getSkin());
            } while (cursor.moveToNext());

            cursor.close();
        }
        return skins;
    }

}
