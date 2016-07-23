package com.nodlee.theogony.fragment;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nodlee.theogony.R;
import com.nodlee.amumu.bean.Skin;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.loader.SkinLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Vernon Lee on 15-11-27.
 */
public class SkinFragment extends Fragment {
    private static final String TAG = "SkinFragment";
    private static final String EXTRA_SKIN_NUM = "extra_skin_num";
    private static final String EXTRA_CHAMPION_ID = "extra_cid";

    private PhotoView mCoverIv;
    private TextView mNameTv;
    private Skin mSkin;
    private Bitmap mCoverBitmap;

    public static SkinFragment newInstance(int num, int cid) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_SKIN_NUM, num);
        args.putInt(EXTRA_CHAMPION_ID, cid);

        SkinFragment skinFragment = new SkinFragment();
        skinFragment.setArguments(args);
        return skinFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLoaderManager().initLoader(3, getArguments(), mLoaderCallBacks);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_skin, container, false);

        mCoverIv = (PhotoView) rootView.findViewById(R.id.img_skin_cover_big);
        mNameTv = (TextView) rootView.findViewById(R.id.txt_skin_cover_name);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_skin, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.menu_item_wallpaper:
                if (mCoverBitmap != null) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("要设置为壁纸吗?")
                            .setNegativeButton(R.string.cancel_button, null)
                            .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new SetWallpaperTask().execute();
                                }
                            }).create().show();
                }
                break;
            case R.id.menu_item_download:
                if (mCoverBitmap != null) {
                    new InsertGalleryTask().execute();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUi() {
        mNameTv.setText(mSkin.getName());

        ImageLoader.getInstance().loadImage(mSkin.getCover(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                mCoverBitmap = bitmap;
                if (bitmap != null) {
                    mCoverIv.setImageBitmap(bitmap);
                }
            }
        });
    }

    private LoaderManager.LoaderCallbacks mLoaderCallBacks = new LoaderManager.LoaderCallbacks<Skin>() {

        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            int cid = args.getInt(EXTRA_CHAMPION_ID);
            int num = args.getInt(EXTRA_SKIN_NUM);
            return new SkinLoader(getActivity(), cid, num);
        }

        @Override
        public void onLoadFinished(Loader loader, Skin skin) {
            mSkin = skin;
            if (mSkin != null) {
                updateUi();
            }
        }

        @Override
        public void onLoaderReset(Loader loader) {
            // do nothing
        }
    };

    private class InsertGalleryTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage("操作中...");
            mDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String title = String.valueOf(new Date().getTime());
            return insertGallery(mCoverBitmap, title, title);
        }

        @Override
        protected void onPostExecute(String newName) {
            if (getActivity() != null && !getActivity().isFinishing()) {
                mDialog.dismiss();
                AndroidUtils.showSnackBar(mNameTv, newName != null ? "已保存至相册" : "操作失败");
            }
        }
    }

    private class SetWallpaperTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage("操作中...");
            mDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getActivity());
                wallpaperManager.setBitmap(mCoverBitmap);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (getActivity() != null && !getActivity().isFinishing()) {
                mDialog.dismiss();
                AndroidUtils.showSnackBar(mNameTv, success ? "壁纸设置成功" : "壁纸设置失败");
            }
        }
    }

    /**
     * 添加图片到相册
     */
    private String insertGallery(Bitmap source, String title, String description) {
        ContentResolver cr = getActivity().getContentResolver();

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
        Cursor cursor = getActivity().getContentResolver().query(contentUri,
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCoverBitmap != null && !mCoverBitmap.isRecycled()) {
            mCoverBitmap.recycle();
        }
    }
}
