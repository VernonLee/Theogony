package com.nodlee.theogony.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nodlee.theogony.R;
import com.nodlee.theogony.adapter.BaseCursorAdapter;
import com.nodlee.theogony.adapter.GridSpacingItemDecoration;
import com.nodlee.theogony.adapter.SkinAdapter;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.utils.SkinsLoader;

/**
 * Created by Vernon Lee on 15-11-27.
 */
public class SkinsActivity extends BaseActivity {
    public static final String EXTRA_CHAMPION = "extra_champion";
    private static final int SPAN_COUNT = 2;
    private static final int SPACING = 5;

    private Toolbar mToolbar;
    private SkinAdapter mAdapter;
    private Champion mChampion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins);

        mToolbar = initToolbar(R.drawable.ic_arrow_back_black, null);

        RecyclerView skinsView = (RecyclerView) findViewById(R.id.recy_view_skins);
        skinsView.setHasFixedSize(true);
        skinsView.addItemDecoration(new GridSpacingItemDecoration(SPAN_COUNT, SPACING, false));
        skinsView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        mAdapter = new SkinAdapter(this, null);
        mAdapter.setOnItemClickedListener(new BaseCursorAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Skin skin = mAdapter.getItem(position);
                if (skin != null) {
                    Intent intent = new Intent(SkinsActivity.this, SkinActivity.class);
                    intent.putExtra(SkinActivity.EXTRA_CHAMPION_ID, mChampion.getCid());
                    intent.putExtra(SkinActivity.EXTRA_SKIN, skin);
                    startActivity(intent);
                }
            }
        });
        skinsView.setAdapter(mAdapter);

        mChampion = getChampion();
        if (mChampion != null) {
            setToolbarTitles(mChampion.getTitle(), null);
            getSupportLoaderManager().initLoader(1, null, mLoaderCallbacks);
        }
    }

    private void setToolbarTitles(String title, String subTitle) {
        if (title != null) {
            mToolbar.setTitle(title);
        }
        if (subTitle != null) {
            mToolbar.setSubtitle(subTitle);
        }
    }

    private Champion getChampion() {
        return (Champion) getIntent().getSerializableExtra(EXTRA_CHAMPION);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private LoaderManager.LoaderCallbacks mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            return new SkinsLoader(SkinsActivity.this, mChampion.getCid());
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor cursor) {
            setToolbarTitles(mChampion.getTitle(), String.format("%d张", cursor.getCount()));
            mAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader loader) {
            mAdapter.swapCursor(null);
        }
    };
}
