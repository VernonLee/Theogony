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
