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

import com.nodlee.theogony.db.SkinManager;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class SkinsLoader extends SQLiteCursorLoader {
    private Context mCtx;
    private int mCid = 0;

    public SkinsLoader(Context context, int cid) {
        super(context);
        mCtx = context;
        mCid = cid;
    }

    @Override
    public Cursor loadCursor() {
        if (mCtx == null || mCid == 0)
            return null;

        return SkinManager.getInstance(mCtx).getSkins(mCid);
    }
}
