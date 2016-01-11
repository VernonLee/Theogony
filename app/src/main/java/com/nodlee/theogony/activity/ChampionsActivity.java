/*
 * Copyright (c) 2015 Vernon Lee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.nodlee.theogony.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nodlee.theogony.R;
import com.nodlee.theogony.fragment.ChampionsFragment;

import java.util.ArrayList;

public class ChampionsActivity extends BaseActivity {
    private static final boolean DEBUG = true;

    private String[] championTags = null;
    private String[] championTagKeys = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champions);

        Resources res = getResources();
        championTags = res.getStringArray(R.array.championTags);
        championTagKeys = res.getStringArray(R.array.championTagKeys);

        Toolbar toolbar = initToolbar();
        toolbar.setLogo(R.mipmap.app_logo_transparent);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        ViewPager championsPager = (ViewPager) findViewById(R.id.pager_champions);
        setupPager(championsPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.view_tab_container);
        tabLayout.setupWithViewPager(championsPager);
    }

    private void setupPager(ViewPager pager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(ChampionsFragment.newInstance(""), "全部");
        for (int i = 0; i < championTags.length; i++) {
            String tagKey = championTagKeys[i];
            String tag = championTags[i];
            adapter.addFragment(ChampionsFragment.newInstance(tagKey), tag);
        }
        pager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_champions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_collections:
                startActivity(new Intent(ChampionsActivity.this, MyFavoritesActivity.class));
                break;
            case R.id.menu_item_search:
                startActivity(new Intent(ChampionsActivity.this, SearchActivity.class));
                break;
            case R.id.menu_item_more:
                startActivity(new Intent(ChampionsActivity.this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class Adapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragments = new ArrayList<>();
        private ArrayList<String> fragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}
