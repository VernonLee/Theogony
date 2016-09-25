package com.nodlee.theogony.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.theogony.R;
import com.nodlee.theogony.adapter.ViewPagerWithTitleAdapter;
import com.nodlee.theogony.db.FavoriteChampionManager;
import com.nodlee.theogony.fragment.LoreFragment;
import com.nodlee.theogony.fragment.SkinListFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vernon Lee on 15-11-26.
 */
public class ChampionActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
    public static final String EXTRA_CHAMPION = "extra_champion";

    @BindView(R.id.iv_avatar)
    ImageView mAvatarIv;
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
    private boolean isCollected; // 是否收藏

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);
        ButterKnife.bind(this);
        getToolbar(R.drawable.ic_arrow_back_black, null);

        mChampion = getChampion();
        if (mChampion != null) {
            ImageLoader.getInstance().displayImage(mChampion.getAvatar(), mAvatarIv);
            mCollapsingToolbar.setTitle(String.format("%1$s-%2$s", mChampion.getName(), mChampion.getTitle()));
            mFavoriteBtn.setSelected(isCollected = isFavorite());
        }

        mFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavorite(!isCollected);
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(this);
        setupViewPager(mChampionDescVp);
        mTabLayout.setupWithViewPager(mChampionDescVp);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        final int scrollRange = appBarLayout.getTotalScrollRange();
        // 头像渐变动画
        final float alphaFactor = (float)(scrollRange - Math.abs(verticalOffset)) / scrollRange;
        mAvatarIv.setAlpha(alphaFactor);
        // 浮动按钮位移动画
        mFavoriteBtn.setTranslationY(Math.abs(verticalOffset));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                return super.onOptionsItemSelected(item);
            }
        }
        return true;
    }

    private void setupViewPager(ViewPager pager) {
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getSupportFragmentManager());
        adapter.add(LoreFragment.getInstance(mChampion), getString(R.string.viewpager_title_lore));
        adapter.add(SkinListFragment.getInstance(mChampion), getString(R.string.viewpager_title_skins));
        pager.setAdapter(adapter);
    }

    private Champion getChampion() {
       return (Champion) getIntent().getSerializableExtra(EXTRA_CHAMPION);
    }

    private boolean isFavorite() {
        if (mChampion != null) {
            return FavoriteChampionManager.getInstance(this).isFavorite(mChampion.getId());
        }
        return false;
    }

    private void setFavorite(boolean isFavorite) {
        if (mChampion == null) return;

        FavoriteChampionManager favoriteChampionManager = FavoriteChampionManager.getInstance(this);
        if (isFavorite) {
            favoriteChampionManager.add(mChampion);
            mFavoriteBtn.setSelected(true);
        } else {
            favoriteChampionManager.delete(mChampion);
            mFavoriteBtn.setSelected(false);
        }

        isCollected = isFavorite;
    }
}
