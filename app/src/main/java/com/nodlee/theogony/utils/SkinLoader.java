package com.nodlee.theogony.utils;

import android.content.Context;

import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.db.SkinManager;

/**
 * Created by Vernon Lee on 15-12-5.
 */
public class SkinLoader extends DataLoader<Skin> {
    private Context mCtx;
    private int mCid, mNum;

    public SkinLoader(Context context, int cid, int num) {
        super(context);
        mCtx = context;
        mCid = cid;
        mNum = num;
    }

    @Override
    public Skin loadInBackground() {
        return SkinManager.getInstance(mCtx).getSkin(mCid, mNum);
    }
}
