package com.nodlee.theogony.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nodlee.amumu.bean.Skin;
import com.nodlee.theogony.R;
import com.nodlee.theogony.adapter.SimpleViewPagerAdapter;
import com.nodlee.theogony.db.DatabaseOpenHelper;
import com.nodlee.theogony.fragment.SkinFragment;
import com.nodlee.theogony.loader.SkinsLoader;
import com.nodlee.theogony.task.InsertGalleryTask;
import com.nodlee.theogony.task.SetWallpaperTask;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vernon Lee on 15-11-27.
 */
public class SkinActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    public static final String EXTRA_CHAMPION_ID = "extra_cid";
    public static final String EXTRA_SKIN = "extra_skin";

    @BindView(R.id.vp_skins)
    ViewPager mSkinsVp;
    @BindView(R.id.txt_skin_cover_name)
    TextView mNameTv;
    @BindView(R.id.txt_skin_num)
    TextView mSkinNumTv;
    @BindView(R.id.btn_download)
    Button mDownloadBtn;
    @BindView(R.id.btn_wallpaper)
    Button mWallPaperBtn;

    private Skin mCurrentSkin;
    private ArrayList<Skin> skinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        ButterKnife.bind(this);
        getSkins();
        mSkinsVp.addOnPageChangeListener(this);
    }

    private void getSkins() {
        Intent intent = getIntent();
        int cid = -1;
        if (intent.hasExtra(EXTRA_CHAMPION_ID)
                && (cid = intent.getIntExtra(EXTRA_CHAMPION_ID, -1)) > 0) {
            Bundle args = new Bundle();
            args.putSerializable(EXTRA_CHAMPION_ID, cid);
            getSupportLoaderManager().initLoader(1, args, mLoaderCallbacks);
        }
    }

    private void updateUi(Skin skin) {
        if (skinList != null && skinList.size() > 0 && skin != null) {
            mNameTv.setText(skin.getName());
            mSkinNumTv.setText(String.format("%d/%d", skin.getNum()+1, skinList.size()));
        }
    }

    private void setupViewPager(ViewPager viewPager, ArrayList<Skin> skins) {
        if (skins != null && skins.size() >= 0) {
            SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(getSupportFragmentManager());
            for (Skin skin : skins) {
                adapter.add(SkinFragment.newInstance(skin));
            }
            viewPager.setAdapter(adapter);

            // 设置ViewPager默认选中页面
            Skin targetSkin = null;
            if (getIntent().hasExtra(EXTRA_SKIN)
                    && (targetSkin = (Skin) getIntent().getSerializableExtra(EXTRA_SKIN)) != null) {
                viewPager.setCurrentItem(targetSkin.getNum());
                updateUi(targetSkin);
                mCurrentSkin = targetSkin;
            }
        }
    }

    @OnClick({R.id.btn_wallpaper, R.id.btn_download}) void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_wallpaper:
                setWallPaper();
                break;
            case R.id.btn_download:
                downLoad();
                break;
        }
    }

    // 设置手机壁纸  skin url -> WallPaper
    private void setWallPaper() {
        if (mCurrentSkin == null) return;

        new AlertDialog.Builder(this)
            .setMessage("要设置为壁纸吗?")
            .setNegativeButton(R.string.cancel_button, null)
            .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new SetWallpaperTask(SkinActivity.this)
                        .execute(mCurrentSkin.getCover());
                }
            }).create().show();
    }

    // 下载照片到手机相册 skin url -> gallery
    private void downLoad() {
        if (mCurrentSkin == null) return;

        new AlertDialog.Builder(this)
            .setMessage("要下载到手机吗?")
            .setNegativeButton(R.string.cancel_button, null)
            .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new InsertGalleryTask(SkinActivity.this)
                        .execute(mCurrentSkin.getCover());
                }
            }).create().show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
         // do nothing
    }

    @Override
    public void onPageSelected(int position) {
        if (skinList != null && position < skinList.size()) {
            mCurrentSkin = skinList.get(position);
            updateUi(mCurrentSkin);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滑动状态不能进行下载或设置壁纸操作
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
           enableMenu(false);
        } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
           enableMenu(true);
        }
    }

    private void enableMenu(boolean flag) {
       mDownloadBtn.setClickable(flag);
       mDownloadBtn.setEnabled(flag);
       mWallPaperBtn.setClickable(flag);
       mWallPaperBtn.setEnabled(flag);
    }

    private LoaderManager.LoaderCallbacks mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            int cid = args.getInt(EXTRA_CHAMPION_ID);
            return new SkinsLoader(SkinActivity.this, cid);
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor data) {
            ArrayList<Skin> skins = new ArrayList<>();
            if (data != null && data.moveToFirst()) {
                do {
                    skins.add(((DatabaseOpenHelper.SkinCursor)data).getSkin());
                } while (data.moveToNext());
            }

            skinList = skins;
            setupViewPager(mSkinsVp, skins);
        }

        @Override
        public void onLoaderReset(Loader loader) {
            // do nothing
        }
    };
}
