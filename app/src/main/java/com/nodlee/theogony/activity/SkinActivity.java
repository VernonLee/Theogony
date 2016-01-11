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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Adapter;

import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.db.DatabaseOpenHelper;
import com.nodlee.theogony.db.SkinManager;
import com.nodlee.theogony.fragment.SkinFragment;

/**
 * Created by Vernon Lee on 15-11-27.
 */
public class SkinActivity extends BaseActivity {
    public static final String EXTRA_CHAMPION_ID = "extra_cid";
    public static final String EXTRA_SKIN = "extra_skin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        Intent intent = getIntent();
        int cid = intent.getIntExtra(EXTRA_CHAMPION_ID, -1);
        Skin skin = (Skin) intent.getSerializableExtra(EXTRA_SKIN);

        initToolbar(R.drawable.ic_arrow_back_black, null);

        ViewPager pager = (ViewPager) findViewById(R.id.pager_skins);
        pager.setAdapter(new Adapter(getSupportFragmentManager(), cid));
        pager.setCurrentItem(skin.getNum());
    }

    private class Adapter extends FragmentStatePagerAdapter {
        private int mCid;
        private int mCount;

        public Adapter(FragmentManager fm, int cid) {
            super(fm);
            mCid = cid;
            mCount = SkinManager.getInstance(SkinActivity.this).getSkinCount(cid);
        }

        @Override
        public Fragment getItem(int position) {
            return SkinFragment.newInstance(position, mCid);
        }

        @Override
        public int getCount() {
            return mCount;
        }
    }
}
