package com.nodlee.theogony.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.MenuItem;

import com.nodlee.theogony.R;
import com.nodlee.theogony.adapter.ChampionAdapter;
import com.nodlee.theogony.adapter.GridSpacingItemDecoration;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.ChampionsLoader;

/**
 * Created by Vernon Lee on 15-12-10.
 */
public class SearchActivity extends BaseActivity {
    private static final int SPAN_COUNT = 4;
    private static final int SPACING = 5;
    private static final int LOADER_CHAMPIONS = 1;
    private static final String EXTRA_QUERY = "query";

    private ChampionAdapter mAdapter;
    private SearchView mSearchView;
    private String mQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolbar(R.drawable.ic_arrow_back_black, null);

        mSearchView = (SearchView) findViewById(R.id.search_view);
        setupSearchView();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_view_champions);
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
                    Intent intent = new Intent(SearchActivity.this, ChampionActivity.class);
                    intent.putExtra(ChampionActivity.EXTRA_CHAMPION, champion);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra(SearchManager.QUERY)) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (!TextUtils.isEmpty(query)) {
                search(query);
                mSearchView.setQuery(query, false);
            }
        }
    }

    private void setupSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconified(false); // xml中设置不起作用
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return true;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(String query) {
        if (TextUtils.isEmpty(query)) {
            return;
        }

        Bundle args = new Bundle();
        args.putString(EXTRA_QUERY, query);
        if (TextUtils.equals(mQuery, query)) {
            getSupportLoaderManager().initLoader(LOADER_CHAMPIONS, args, mLoaderCallbacks);
        } else {
            getSupportLoaderManager().restartLoader(LOADER_CHAMPIONS, args, mLoaderCallbacks);
        }

        mQuery = query;
    }

    private LoaderManager.LoaderCallbacks mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            String query = args.getString(EXTRA_QUERY);
            return new ChampionsLoader(SearchActivity.this, ChampionsLoader.Query.KEYWORDS, query);
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor cursor) {
            mAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader loader) {
            mAdapter.swapCursor(null);
        }
    };
}
