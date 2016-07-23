package com.nodlee.theogony.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.nodlee.theogony.R;
import com.nodlee.amumu.bean.Skin;
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
