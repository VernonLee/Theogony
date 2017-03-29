package com.nodlee.theogony.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.DragonData;
import com.nodlee.theogony.core.DragonDataManager;
import com.nodlee.theogony.ui.view.LicenseDialog;
import com.nodlee.theogony.utils.CacheManager;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vernon Lee on 15-11-25.
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.txt_locale)
    TextView mLocaleTv;
    @BindView(R.id.txt_dragon_data_version)
    TextView mDragonDataVersionTv;
    @BindView(R.id.txt_cache_size)
    TextView mCacheSizeTv;
    @BindView(R.id.txt_app_version)
    TextView mAppVersionTv;
    @BindView(R.id.txt_open_source_license)
    TextView mOSLTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getToolbar(R.drawable.ic_arrow_back, null);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            mAppVersionTv.setText(packageInfo.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DragonData dragonData = DragonDataManager.getInstance().getDefault();
        if (dragonData != null) {
            mLocaleTv.setText(dragonData.getLanguageCode());
            mDragonDataVersionTv.setText(dragonData.getVersion());
        }

        double cacheSize = CacheManager.getCacheSize(this);
        mCacheSizeTv.setText(String.format("%.1fM", cacheSize));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_locale:
                // TODO 手动更新数据源
                break;
            case R.id.view_cache_size:
                cleanCache();
                break;
            case R.id.view_open_source_license:
                new LicenseDialog(SettingsActivity.this).create();
                break;
            case R.id.view_app_version:
                // TODO 版本更新
                break;
            case R.id.view_about_app:
                startActivity(new Intent(SettingsActivity.this, AboutAppActivity.class));
                break;
            case R.id.view_feedback:
                feedback();
                break;
        }
    }

    private void cleanCache() {
        new AlertDialog.Builder(this)
                .setTitle("提醒")
                .setMessage("想要清理缓存？")
                .setNegativeButton(R.string.cancel_button, null)
                .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheManager.cleanCache(SettingsActivity.this);
                    }
                })
                .create().show();
    }

    private void feedback() {
        new AlertDialog.Builder(this)
                .setTitle("意见反馈")
                .setMessage(R.string.feedback_message)
                .setPositiveButton(R.string.okay_button, null)
                .create().show();
    }
}
