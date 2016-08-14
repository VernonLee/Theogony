package com.nodlee.theogony.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nodlee.theogony.R;

/**
 * Created by Vernon Lee on 15-11-24.
 */
public class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().setBackgroundDrawableResource(R.mipmap.window_bg);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    protected Toolbar getToolbar(int navResId, String title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (navResId != 0) {
            mToolbar.setNavigationIcon(navResId);
        }
        if (title != null) {
            mToolbar.setTitle(title);
        }
        setSupportActionBar(mToolbar);
        return mToolbar;
    }
}
