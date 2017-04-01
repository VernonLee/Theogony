package com.nodlee.theogony.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.core.ChampionManager;
import com.nodlee.theogony.ui.adapter.ViewPagerWithTitleAdapter;
import com.nodlee.theogony.core.FavoritesManager;
import com.nodlee.theogony.ui.fragment.BasicInformationFragment;
import com.nodlee.theogony.ui.fragment.SkinListFragment;
import com.nodlee.theogony.ui.fragment.SpellListFragment;
import com.nodlee.theogony.ui.view.CircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vernon Lee on 15-11-26.
 */
public class ChampionActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
    public static final String EXTRA_CHAMPION_ID = "extra_champion_id";

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_champion_desc)
    ViewPager mChampionDescVp;
    @BindView(R.id.btn_favorite)
    FloatingActionButton mFavoriteBtn;
    @BindView(R.id.view_appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    private Champion mChampion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);
        ButterKnife.bind(this);
        getToolbar(R.drawable.ic_arrow_back, null);

        init();

        mAppBarLayout.addOnOffsetChangedListener(this);
        setupViewPager(mChampionDescVp, mChampion.getId());
        mTabLayout.setupWithViewPager(mChampionDescVp);
    }

    private void init() {
        int championId = getIntent().getIntExtra(EXTRA_CHAMPION_ID, -1);
        if (championId > 0) {
            Champion champion = ChampionManager.getInstance().get(championId);
            boolean isFavorite = FavoritesManager.getInstance().isFavorite(champion);
            mFavoriteBtn.setSelected(isFavorite);
            mCollapsingToolbar.setTitle(champion.getName());

            mChampion = champion;
        }
    }

    private void setupViewPager(ViewPager pager, int championId) {
        BasicInformationFragment basicInformationFragment = BasicInformationFragment.getInstance(championId);
        SpellListFragment spellListFragment = SpellListFragment.getInstance(championId);
        SkinListFragment skinsFragment = SkinListFragment.getInstance(championId);

        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getSupportFragmentManager());
        adapter.add(basicInformationFragment, getString(R.string.vp_title_basic_info));
        adapter.add(spellListFragment, getString(R.string.vp_title_spells));
        adapter.add(skinsFragment, getString(R.string.vp_title_skins));
        pager.setAdapter(adapter);
    }

    @OnClick(R.id.btn_favorite)
    void setOrCancelFavorite() {
        FavoritesManager manager = FavoritesManager.getInstance();
        boolean isFavorite = manager.isFavorite(mChampion);
        if (isFavorite) {
            manager.remove(mChampion);
            mFavoriteBtn.setSelected(false);
        } else {
            manager.add(mChampion);
            mFavoriteBtn.setSelected(true);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        mFavoriteBtn.setTranslationY(Math.abs(verticalOffset));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
