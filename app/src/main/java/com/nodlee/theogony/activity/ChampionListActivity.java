package com.nodlee.theogony.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nodlee.theogony.R;
import com.nodlee.theogony.adapter.ViewPagerWithTitleAdapter;
import com.nodlee.theogony.fragment.ChampionListFragment;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：nodlee
 * 时间：16/8/1
 * 说明：英雄列表，单元格以缩略图+文字显示
 */
public class ChampionListActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion_list);
        ButterKnife.bind(this);

        mToolbar = getToolbar(0, null);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDrawerLayoutOpen()) {
                    closeDrawerLayout();
                } else {
                    openDrawerLayout();
                }
            }
        });

        mNavigationView.setNavigationItemSelectedListener(this);
        // 二次创建的时候,重置Menu菜单

        ViewPager championsPager = (ViewPager) findViewById(R.id.vp_champions);
        setupPager(championsPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.view_tab_container);
        tabLayout.setupWithViewPager(championsPager);
    }

    private void setupPager(ViewPager pager) {
        Resources res = getResources();
        String[] championTags = res.getStringArray(R.array.championTags);
        String[]championTagKeys = res.getStringArray(R.array.championTagKeys);

        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getSupportFragmentManager());
        for (int i = 0; i < championTags.length; i++) {
            String tagKey = championTagKeys[i];
            String tag = championTags[i];
            adapter.add(ChampionListFragment.newInstance(tagKey), tag);
        }
        pager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.menu_item_search:
                startActivity(new Intent(ChampionListActivity.this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        goToNavigationViewItem(item.getItemId());
        closeDrawerLayout();
        return false;
    }

    private void goToNavigationViewItem(int itemID) {
        switch (itemID) {
            case R.id.menu_item_champion_list:
                // do nothing
                break;
            case R.id.menu_item_collection_list:
                startActivity(new Intent(ChampionListActivity.this, FavoritesActivity.class));
                break;
            case R.id.menu_item_wallpaper:
                startActivity(new Intent(ChampionListActivity.this, SkinListActivity.class));
                break;
            case R.id.menu_item_settings:
                startActivity(new Intent(ChampionListActivity.this, SettingsActivity.class));
                break;
            case R.id.menu_item_dayNight:
                toggleDayNightMode();
                break;
        }
    }

    private void openDrawerLayout() {
        if (!isDrawerLayoutOpen())
            mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private boolean isDrawerLayoutOpen() {
        return mDrawerLayout != null
                && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    private void closeDrawerLayout() {
        if (isDrawerLayoutOpen())
            mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void toggleDayNightMode() {
        int currentDayNightMode = AndroidUtils.getDayNightMode(this);
        switch (currentDayNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                UserUtils.setNightMode(this, false);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                UserUtils.setNightMode(this, true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isDrawerLayoutOpen()) {
            closeDrawerLayout();
        } else {
            super.onBackPressed();
        }
    }
}
