package com.nodlee.theogony.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.theogony.R;
import com.nodlee.theogony.adapter.ViewPagerWithTitleAdapter;
import com.nodlee.theogony.fragment.LoreFragment;
import com.nodlee.theogony.fragment.SkinsFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vernon Lee on 15-11-26.
 */
public class ChampionActivity extends BaseActivity {
    public static final String EXTRA_CHAMPION = "extra_champion";

    @BindView(R.id.img_champion_avatar)
    ImageView mCoverIv;
    @BindView(R.id.txt_champion_name)
    TextView mNameTv;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_champion_desc)
    ViewPager mChampionDescVp;

    private Champion mChampion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);
        ButterKnife.bind(this);

        mChampion = getChampion();
        if (mChampion != null) {
            ImageLoader.getInstance().displayImage(mChampion.getAvatar(), mCoverIv);
            mNameTv.setText(String.format("%1$s-%2$s", mChampion.getName(), mChampion.getTitle()));
        }

        setupViewPager(mChampionDescVp);
        mTabLayout.setupWithViewPager(mChampionDescVp);
    }

    private void setupViewPager(ViewPager pager) {
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getSupportFragmentManager());
        adapter.add(LoreFragment.getInstance(mChampion), getString(R.string.viewpager_title_lore));
        adapter.add(SkinsFragment.getInstance(mChampion), getString(R.string.viewpager_title_skins));
        pager.setAdapter(adapter);
    }

    private Champion getChampion() {
       return (Champion) getIntent().getSerializableExtra(EXTRA_CHAMPION);
    }
}
