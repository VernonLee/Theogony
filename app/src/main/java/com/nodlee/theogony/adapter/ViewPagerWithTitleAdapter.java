package com.nodlee.theogony.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

/**
 * 作者：nodlee
 * 时间：16/8/12
 * 说明：
 */
public class ViewPagerWithTitleAdapter extends ViewPagerAdapter {
    protected ArrayList<String> mTitles = new ArrayList<>();

    public ViewPagerWithTitleAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(Fragment fragment, String title) {
        if (fragment == null || title == null)
            throw new IllegalArgumentException("fragment或title不能为空");

        mFragments.add(fragment);
        mTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
