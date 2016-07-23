package com.nodlee.theogony.activity;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.nodlee.theogony.R;
import com.nodlee.theogony.adapter.ChampionAdapter;
import com.nodlee.theogony.adapter.GridSpacingItemDecoration;
import com.nodlee.amumu.bean.Champion;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.Constants;
import com.nodlee.theogony.loader.FavoriteChampionsLoader;

/**
 * Created by Vernon Lee on 15-11-25.
 */
public class MyFavoritesActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int SPAN_COUNT = 4;
    private static final int SPACING = 5;
    private static final int LOADER_FAVORITE_CHAMPIONS = 4;

    private ChampionAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);

        initView();

        getSupportLoaderManager().initLoader(LOADER_FAVORITE_CHAMPIONS, null, this);

        getContentResolver().registerContentObserver(
                Constants.Favorite.CONTENT_URI, true, mObserver);
    }

    private void initView() {
        initToolbar(R.drawable.ic_arrow_back_black, null);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_view_favorite_champions);
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(SPAN_COUNT,
                (int) AndroidUtils.dpToPx(SPACING, this), true));
        mAdapter = new ChampionAdapter(this, null);
        mAdapter.setOnItemClickedListener(new ChampionAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Champion champion = mAdapter.getItem(position);
                if (champion != null) {
                    Intent intent = new Intent(MyFavoritesActivity.this, ChampionActivity.class);
                    intent.putExtra(ChampionActivity.EXTRA_CHAMPION, champion);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
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
        return new FavoriteChampionsLoader(MyFavoritesActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
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
