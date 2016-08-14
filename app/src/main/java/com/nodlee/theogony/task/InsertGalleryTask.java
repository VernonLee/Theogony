package com.nodlee.theogony.task;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.nodlee.amumu.util.HttpFetchr;
import com.nodlee.theogony.activity.SkinActivity;
import com.nodlee.theogony.utils.AndroidUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class InsertGalleryTask extends AsyncTask<String, Void, Boolean> {
    private static String TAG = InsertGalleryTask.class.getName();
    private ProgressDialog mDialog;
    private Context mContext;

    public InsertGalleryTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("操作中...");
        mDialog.show();
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

            Bitmap mCoverBitmap = BitmapFactory.decodeByteArray(dataBytes, 0, dataBytes.length);
            String title = String.valueOf(new Date().getTime());
            insertGallery(mCoverBitmap, title, title);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        mDialog.dismiss();
        if (!((SkinActivity)mContext).isFinishing()) {
            AndroidUtils.showToast(mContext, success ? "已保存至相册" : "操作失败");
        }
    }

    /**
     * 添加图片到相册
     */
    private String insertGallery(Bitmap source, String title, String description) {
        ContentResolver cr = mContext.getContentResolver();

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        Uri url = null;
        String stringUrl = null;
        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (source != null) {
                OutputStream imageOut = new FileOutputStream(new File(getRealPathFromUri(url)));
                BufferedOutputStream out = new BufferedOutputStream(imageOut);
                try {
                    source.compress(Bitmap.CompressFormat.JPEG, 50, out);
                } finally {
                    out.flush();
                    out.close();
                }
                long id = ContentUris.parseId(url);
                // Wait until MINI_KIND thumbnail is generated.
                Bitmap miniThumb = MediaStore.Images.Thumbnails.getThumbnail(cr, id,
                        MediaStore.Images.Thumbnails.MINI_KIND, null);
                // This is for backward compatibility.
                Bitmap microThumb = storeThumbnail(cr, miniThumb, id, 50F, 50F,
                        MediaStore.Images.Thumbnails.MICRO_KIND);
            } else {
                Log.e(TAG, "Failed to create thumbnail, removing original");
                cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to insert image", e);
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }

        return stringUrl;
    }


    private String getRealPathFromUri(Uri contentUri) {
        String result;
        Cursor cursor = mContext.getContentResolver().query(contentUri,
                null, null, null, null, null);
        if (cursor == null) {
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /**
     * 存储相册缩略图
     */
    private Bitmap storeThumbnail(ContentResolver cr,
                                  Bitmap source, long id, float width, float height, int kind) {
        // create the matrix to scale it
        Matrix matrix = new Matrix();
        float scaleX = width / source.getWidth();
        float scaleY = height / source.getHeight();
        matrix.setScale(scaleX, scaleY);

        Bitmap thumb = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);

        ContentValues values = new ContentValues(4);
        values.put(MediaStore.Images.Thumbnails.KIND, kind);
        values.put(MediaStore.Images.Thumbnails.IMAGE_ID, (int) id);
        values.put(MediaStore.Images.Thumbnails.HEIGHT, thumb.getHeight());
        values.put(MediaStore.Images.Thumbnails.WIDTH, thumb.getWidth());

        Uri url = cr.insert(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, values);
        try {
            OutputStream thumbOut = cr.openOutputStream(url);
            thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
            thumbOut.close();
            return thumb;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

}