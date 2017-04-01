package com.nodlee.theogony.ui.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.loader.ChampionsLoader;
import com.nodlee.theogony.ui.adapter.ChampionAdapter;
import com.nodlee.theogony.ui.adapter.ItemClickedListener;
import com.nodlee.theogony.ui.view.AutoFitRecyclerView;
import com.nodlee.theogony.ui.view.MarginDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nodlee.theogony.ui.activity.ChampionActivity.EXTRA_CHAMPION_ID;

/**
 * Created by Vernon Lee on 15-12-10.
 */
public class SearchActivity extends BaseActivity implements ItemClickedListener, SearchView.OnQueryTextListener {
    private static final int LOADER_CHAMPIONS = 1;

    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.recy_view_champions)
    AutoFitRecyclerView mChampionListRecyclerView;
    @BindView(R.id.view_not_found)
    View mNotFoundView;

    private ChampionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getToolbar(R.drawable.ic_arrow_back, null);

        setupSearchView(mSearchView);

        mAdapter = new ChampionAdapter(Glide.with(this), null);
        mAdapter.setItemClickListener(this);
        mChampionListRecyclerView.setHasFixedSize(true);
        mChampionListRecyclerView.addItemDecoration(new MarginDecoration(this));
        mChampionListRecyclerView.setAdapter(mAdapter);
    }

    private void setupSearchView(final SearchView searchView) {
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false); // xml中设置不起作用
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        search(newText);
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra(SearchManager.QUERY)) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            search(query);
            mSearchView.setQuery(query, false);
        }
    }

    @Override
    public void onItemClicked(View view, int position) {
        Champion champion = mAdapter.getItem(position);
        Intent intent = new Intent(this, ChampionActivity.class);
        intent.putExtra(EXTRA_CHAMPION_ID, champion.getId());
        startActivity(intent);
    }

    private void search(String query) {
        if (query != null && query.trim().length() > 0) {
            Bundle args = new Bundle();
            args.putString("extra_query", query);
            getSupportLoaderManager().restartLoader(LOADER_CHAMPIONS, args, mLoaderCallbacks);
        }
    }

    private LoaderManager.LoaderCallbacks mLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<Champion>>() {
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            String query = args.getString("extra_query");
            return new ChampionsLoader(SearchActivity.this, ChampionsLoader.Action.QUERY, query);
        }

        @Override
        public void onLoadFinished(Loader<List<Champion>> loader, List<Champion> data) {
            if (data.size() == 0) {
                mChampionListRecyclerView.setVisibility(View.INVISIBLE);
                mNotFoundView.setVisibility(View.VISIBLE);
            } else {
                mChampionListRecyclerView.setVisibility(View.VISIBLE);
                mNotFoundView.setVisibility(View.INVISIBLE);
            }
            mAdapter.setData(data);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader loader) {
            //  Do Nothing
        }
    };
}
