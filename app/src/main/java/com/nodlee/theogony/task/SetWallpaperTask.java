package com.nodlee.theogony.task;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import com.nodlee.amumu.util.HttpFetchr;
import com.nodlee.theogony.activity.SkinActivity;
import com.nodlee.theogony.utils.AndroidUtils;

import java.io.IOException;

public class SetWallpaperTask extends AsyncTask<String, Void, Boolean> {
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
    protected Boolean doInBackground(String... params) {
        try {
            // 图片地址urlSpec
            Uri urlSpec = Uri.parse(params[0]);
            byte[] dataBytes = new HttpFetchr().getUrlBytes(urlSpec);
            if (dataBytes == null) {
                return false;
            }

            Bitmap wallPaperBitmap = BitmapFactory.decodeByteArray(dataBytes, 0, dataBytes.length);
            if (wallPaperBitmap != null) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
                wallpaperManager.setBitmap(wallPaperBitmap);

            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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