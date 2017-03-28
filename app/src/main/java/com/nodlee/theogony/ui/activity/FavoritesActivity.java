package com.nodlee.theogony.ui.activity;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.ui.adapter.ChampionAdapter;
import com.nodlee.theogony.ui.adapter.ItemClickedListener;
import com.nodlee.theogony.utils.Constants;
import com.nodlee.theogony.ui.view.AutoFitRecyclerView;
import com.nodlee.theogony.ui.view.MarginDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nodlee.theogony.ui.activity.ChampionActivity.EXTRA_CHAMPION_ID;

/**
 * Created by Vernon Lee on 15-11-25.
 */
public class FavoritesActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_FAVORITE_CHAMPIONS = 4;

    @BindView(R.id.recy_view_favorite_champions)
    AutoFitRecyclerView mFavoriteChampionsRecyView;

    private ChampionAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        getToolbar(R.drawable.ic_arrow_back, null);

        mAdapter = new ChampionAdapter(Glide.with(this));
        mAdapter.setItemClickListener(new ItemClickedListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Champion champion = mAdapter.getItem(position);
                if (champion != null) {
                    Intent intent = new Intent(FavoritesActivity.this, ChampionActivity.class);
                    intent.putExtra(EXTRA_CHAMPION_ID, champion.getId());
                    startActivity(intent);
                }
            }
        });
        mFavoriteChampionsRecyView.setHasFixedSize(true);
        mFavoriteChampionsRecyView.addItemDecoration(new MarginDecoration(this));
        mFavoriteChampionsRecyView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(LOADER_FAVORITE_CHAMPIONS, null, this);
        getContentResolver().registerContentObserver(Constants.Favorite.CONTENT_URI, true, mObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // return new FavoritesLoader(FavoritesActivity.this);
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // mAdapter.swapCursor(null);
    }

    private ContentObserver mObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            Loader<Cursor> loader = getSupportLoaderManager().getLoader(LOADER_FAVORITE_CHAMPIONS);
            if (loader != null) {
                loader.forceLoad();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mObserver);
    }
}
