package com.nodlee.theogony.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.core.SkinManager;
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
    public static final String EXTRA_SKIN_ID = "extra_skin_id";

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
        init();
    }

    private void init() {
        if (!getIntent().hasExtra(EXTRA_SKIN_ID))
            return;

        int skinID = getIntent().getIntExtra(EXTRA_SKIN_ID, -1);
        Skin skin = SkinManager.getInstance().get(skinID);
        if (skin != null) {
            mNameTv.setText(skin.getName());
            Glide.with(this).load(skin.getImage()).thumbnail(0.1f).into(mCoverIv);
            mSkin = skin;
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
     *
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
                                .execute(skin.getImage());
                    }
                }).create().show();
    }

    /**
     * 下载皮肤到本地相册
     *
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
                                .execute(skin.getImage());
                    }
                }).create().show();
    }
}
