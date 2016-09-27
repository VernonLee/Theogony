package com.nodlee.theogony.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nodlee.amumu.bean.Skin;
import com.nodlee.theogony.R;
import com.nodlee.theogony.task.InsertGalleryTask;
import com.nodlee.theogony.task.SetWallpaperTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Vernon Lee on 15-11-27.
 */
public class SkinActivity extends BaseActivity {
    public static final String EXTRA_SKIN = "extra_skin";

    @BindView(R.id.txt_skin_name)
    TextView mNameTv;
    @BindView(R.id.iv_cover)
    PhotoView mCoverIv;

    private Skin mSkin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        ButterKnife.bind(this);
        getToolbar(R.drawable.ic_arrow_back, null);

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    return super.onOptionsItemSelected(item);
                }
                break;
            case R.id.menu_item_wallpaper:
                setWallPaper(mSkin);
                break;
            case R.id.menu_item_download:
                downLoad(mSkin);
                break;
        }
        return true;
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
