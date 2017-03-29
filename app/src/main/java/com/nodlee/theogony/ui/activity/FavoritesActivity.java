package com.nodlee.theogony.ui.activity;

import android.content.Intent;
import android.database.ContentObserver;
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
import com.nodlee.theogony.core.FavoritesManager;
import com.nodlee.theogony.loader.FavoritesLoader;
import com.nodlee.theogony.ui.adapter.ChampionAdapter;
import com.nodlee.theogony.ui.adapter.ItemClickedListener;
import com.nodlee.theogony.utils.Constants;
import com.nodlee.theogony.ui.view.AutoFitRecyclerView;
import com.nodlee.theogony.ui.view.MarginDecoration;
import com.nodlee.theogony.utils.LogHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;
import static com.nodlee.theogony.ui.activity.ChampionActivity.EXTRA_CHAMPION_ID;

/**
 * Created by Vernon Lee on 15-11-25.
 */
public class FavoritesActivity extends BaseActivity implements ItemClickedListener {
    private static final int LOADER_FAVORITE_CHAMPIONS = 4;

    @BindView(R.id.recy_view_favorite_champions)
    AutoFitRecyclerView mRecyclerView;

    private ChampionAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        getToolbar(R.drawable.ic_arrow_back, null);

        mAdapter = new ChampionAdapter(Glide.with(this), null);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MarginDecoration(this));
        mRecyclerView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(LOADER_FAVORITE_CHAMPIONS, null, mLoaderCallbacks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Loader loader = getSupportLoaderManager().getLoader(LOADER_FAVORITE_CHAMPIONS);
        if (loader != null) {
            loader.forceLoad();
        }
    }

    @Override
    public void onItemClicked(View view, int position) {
        Champion champion = mAdapter.getItem(position);
        Intent intent = new Intent(FavoritesActivity.this, ChampionActivity.class);
        intent.putExtra(EXTRA_CHAMPION_ID, champion.getId());
        startActivity(intent);
    }

    LoaderManager.LoaderCallbacks mLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<Champion>>() {

        @Override
        public Loader<List<Champion>> onCreateLoader(int id, Bundle args) {
            return new FavoritesLoader(FavoritesActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<Champion>> loader, final List<Champion> data) {
            mAdapter.setData(data);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<Champion>> loader) {
            // do nothing
        }
    };

}
