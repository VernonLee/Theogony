package com.nodlee.theogony.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.db.FavoriteChampionManager;
import com.nodlee.theogony.db.SkinManager;
import com.nodlee.theogony.view.AppBarOffsetChangedListener;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Vernon Lee on 15-11-26.
 */
public class ChampionActivity extends BaseActivity {
    public static final String EXTRA_CHAMPION = "extra_champion";

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mCoverIv;
    private TextView mLoreTv;
    private Toolbar mToolbar;
    private FloatingActionButton mFavoriteBtn;

    private Champion mChampion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);

        mChampion = getChampion();

        mToolbar = initToolbar(R.drawable.ic_arrow_back_white, null);

        Resources resources = getResources();
        int expandedTitleColor = resources.getColor(android.R.color.white);
        int collapsedTitleColor = resources.getColor(R.color.text_dark);
        int contentScrimColor = resources.getColor(R.color.md_primary);
        int fabBackgroundColor = resources.getColor(R.color.md_primary);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.view_collapsing_toolbar);
        mCollapsingToolbarLayout.setExpandedTitleColor(expandedTitleColor);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(collapsedTitleColor);
        mCollapsingToolbarLayout.setContentScrimColor(contentScrimColor);

        mCoverIv = (ImageView) findViewById(R.id.img_champion_cover);
        mLoreTv = (TextView) findViewById(R.id.txt_champion_lore);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.view_appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarOffsetChangedListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED || state == State.COLLAPSED) {
                    toggleToolbarMenuIcon(state);
                }
            }
        });

        mFavoriteBtn = (FloatingActionButton) findViewById(R.id.btn_favorite);
        mFavoriteBtn.setBackgroundTintList(ColorStateList.valueOf(fabBackgroundColor));
        mFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavorite(!isFavorite());
            }
        });

        if (mChampion != null) {
            setToolbarTitle(String.format("%1$s-%2$s", mChampion.getName(), mChampion.getTitle()));
            setLore(mChampion.getLore());
            mFavoriteBtn.setSelected(isFavorite());

            Skin skin = SkinManager.getInstance(this).getDefaultSkin(mChampion.getCid());
            if (skin != null) {
                ImageLoader.getInstance().displayImage(skin.getCover(), mCoverIv);
            }
        } else {
            mFavoriteBtn.hide();
        }
    }

    private Champion getChampion() {
       return (Champion) getIntent().getSerializableExtra(EXTRA_CHAMPION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_champion, menu);
        if (mChampion == null) {
            menu.findItem(R.id.menu_item_skins).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_skins:
                Intent intent = new Intent(ChampionActivity.this, SkinsActivity.class);
                intent.putExtra(SkinsActivity.EXTRA_CHAMPION, mChampion);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleToolbarMenuIcon(AppBarOffsetChangedListener.State state) {
        final MenuItem skinsMenuItem = mToolbar.getMenu().findItem(R.id.menu_item_skins);
        if (skinsMenuItem == null)
            return;

        switch (state) {
            case EXPANDED:
                mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
                skinsMenuItem.setIcon(R.drawable.ic_photo_white);
                break;
            case COLLAPSED:
                mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
                skinsMenuItem.setIcon(R.drawable.ic_photo_black);
                break;
        }
    }

    private void setToolbarTitle(String title) {
        mCollapsingToolbarLayout.setTitle(title);
    }

    private void setLore(String lore) {
        mLoreTv.setText(Html.fromHtml("&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;" + lore));
    }

    private boolean isFavorite() {
        if (mChampion == null) {
            return false;
        } else {
            return FavoriteChampionManager.getInstance(this).isFavorite(mChampion.getCid());
        }
    }

    private void setFavorite(boolean isFavorite) {
        if (mChampion == null) return;

        FavoriteChampionManager favoriteChampionManager = FavoriteChampionManager.getInstance(this);
        if (isFavorite) {
            if (favoriteChampionManager.addFavorite(mChampion.getCid())) {
                mFavoriteBtn.setSelected(true);
            }
        } else {
            if (favoriteChampionManager.deleteFavorite(mChampion.getCid())) {
                mFavoriteBtn.setSelected(false);
            }
        }
    }
}
