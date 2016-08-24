package com.nodlee.theogony.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodlee.amumu.bean.Skin;
import com.nodlee.theogony.R;
import com.nodlee.theogony.adapter.SimpleViewPagerAdapter;
import com.nodlee.theogony.db.DatabaseOpenHelper;
import com.nodlee.theogony.loader.SkinsLoader;
import com.nodlee.theogony.task.InsertGalleryTask;
import com.nodlee.theogony.task.SetWallpaperTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vernon Lee on 15-11-27.
 */
public class SkinActivity extends BaseActivity {
    public static final String EXTRA_SKIN = "extra_skin";

    @BindView(R.id.txt_skin_name)
    TextView mNameTv;
    @BindView(R.id.iv_cover)
    ImageView mCoverIv;

    private Skin mSkin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        ButterKnife.bind(this);

        getToolbar(R.drawable.ic_arrow_back_black, null);

        mSkin = getSkin();
        updateUi(mSkin);
    }

    private Skin getSkin() {
        Skin skin = null;
        if (getIntent().hasExtra(EXTRA_SKIN)) {
            skin = (Skin) getIntent().getSerializableExtra(EXTRA_SKIN);
        }
        return skin;
    }

    private void updateUi(Skin skin) {
        if (skin != null) {
//            String[] names = skin.getName().split(" ");
//            String html="<big>%s</big>  <small>%s<small>";
//            String title = String.format(html, names[0], names[1]);
//            mNameTv.setText(Html.fromHtml(title));
            mNameTv.setText(skin.getName());
            ImageLoader.getInstance().displayImage(skin.getCover(), mCoverIv);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_skin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishAfterTransition();
                break;
            case R.id.menu_item_wallpaper:
                setWallPaper(mSkin);
                break;
            case R.id.menu_item_download:
                downLoad(mSkin);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置皮肤为桌面壁纸
     * @param skin
     */
    private void setWallPaper(final Skin skin) {
        if (skin == null) return;

        new AlertDialog.Builder(this)
            .setMessage("要设置为壁纸吗?")
            .setNegativeButton(R.string.cancel_button, null)
            .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                new SetWallpaperTask(SkinActivity.this)
                        .execute(skin.getCover());
            }
            }).create().show();
    }

    /**
     * 下载皮肤到本地相册
     * @param skin
     */
    private void downLoad(final Skin skin) {
        if (skin == null) return;

        new AlertDialog.Builder(this)
            .setMessage("要下载到手机吗?")
            .setNegativeButton(R.string.cancel_button, null)
            .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new InsertGalleryTask(SkinActivity.this)
                            .execute(skin.getCover());
                }
            }).create().show();
    }
}
