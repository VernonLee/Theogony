package com.nodlee.theogony.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * 作者：nodlee
 * 时间：16/8/12
 * 说明：基础ViewPager适配器类
 */
public class SimpleViewPagerAdapter extends ViewPagerAdapter {

    public SimpleViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(Fragment fragment) {
        if (fragment == null)
            throw new IllegalArgumentException("fragment或title不能为空");

        mFragments.add(fragment);
    }
}
