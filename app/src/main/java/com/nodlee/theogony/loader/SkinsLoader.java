package com.nodlee.theogony.loader;

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

        return SkinManager.getInstance(mCtx).getAll(mCid);
    }
}
