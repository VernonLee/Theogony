package com.nodlee.theogony.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nodlee.theogony.R;

/**
 * Created by Vernon Lee on 15-11-24.
 */
public class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;

    protected Toolbar initToolbar() {
        return mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    protected Toolbar initToolbar(int navResId, int titleResId) {
        return initToolbar(navResId, getString(titleResId));
    }

    protected Toolbar initToolbar(int navResId, String title) {
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

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
