package com.nodlee.theogony.task;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import com.nodlee.theogony.ui.activity.SkinActivity;
import com.nodlee.theogony.utils.AndroidUtils;

import java.io.IOException;

import static android.R.attr.bitmap;

public class SetWallpaperTask extends AsyncTask<Bitmap, Void, Boolean> {
    private Context mContext;
    private ProgressDialog mProgressDialog;

    public SetWallpaperTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("操作中...");
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Bitmap... params) {
        Bitmap bitmap = params[0];
        try {
            if (bitmap != null) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
                wallpaperManager.setBitmap(bitmap);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressDialog.dismiss();
        if (!((SkinActivity)mContext).isFinishing()) {
            AndroidUtils.showToast(mContext, aBoolean ? "操作成功" : "操作失败");
        }
    }
}