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
package com.nodlee.theogony.utils;

import android.content.Context;
import android.database.Cursor;

import com.nodlee.theogony.db.ChampionManager;
import com.nodlee.theogony.db.DatabaseOpenHelper;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class ChampionsLoader extends SQLiteCursorLoader {
    private Context mCtx;
    private String mSelection;
    private String[] mSelectionArgs;

    public enum Query {
        TAGS, KEYWORDS
    }

    public ChampionsLoader(Context context, Query query, String value) {
        super(context);
        mCtx = context;
        mSelection = buildSelection(query);
        mSelectionArgs = buildSelectionArgs(query, value);
    }

    @Override
    public Cursor loadCursor() {
        if (mCtx == null)
            return null;

        return ChampionManager.getInstance(mCtx).getChampions(mSelection, mSelectionArgs);
    }

    private String buildSelection(Query query) {
        switch (query) {
            case TAGS:
                return DatabaseOpenHelper.CHAMPION_COLUMN_TAGS + " like ? ";
            case KEYWORDS:
                return DatabaseOpenHelper.CHAMPION_COLUMN_NAME + " like ? or "
                        + DatabaseOpenHelper.CHAMPION_COLUMN_TITLE + " like ? or "
                        + DatabaseOpenHelper.CHAMPION_COLUMN_KEY + " like ?";
            default:
                return null;
        }
    }

    private String[] buildSelectionArgs(Query query, String value) {
        switch (query) {
            case TAGS:
                return new String[]{"%" + value + "%"};
            case KEYWORDS:
                return new String[]{"%" + value + "%",
                        "%" + value + "%", "%" + value + "%"};
            default:
                return null;
        }
    }
}
