package com.nodlee.theogony.ui.activity;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.ui.adapter.ChampionAdapter;
import com.nodlee.theogony.ui.adapter.ItemClickedListener;
import com.nodlee.theogony.ui.view.AutoFitRecyclerView;
import com.nodlee.theogony.ui.view.MarginDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nodlee.theogony.ui.activity.ChampionActivity.EXTRA_CHAMPION_ID;

/**
 * Created by Vernon Lee on 15-12-10.
 */
public class SearchActivity extends BaseActivity implements ItemClickedListener {
    private static final int LOADER_CHAMPIONS = 1;
    private static final String EXTRA_QUERY = "queryByKeyWord";

    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.recy_view_champions)
    AutoFitRecyclerView mChampionListRecyclerView;

    private ChampionAdapter mAdapter;
    private String mQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getToolbar(R.drawable.ic_arrow_back, null);

        setupSearchView();

        mAdapter = new ChampionAdapter(Glide.with(this), null);
        mAdapter.setItemClickListener(this);
        mChampionListRecyclerView.setHasFixedSize(true);
        mChampionListRecyclerView.addItemDecoration(new MarginDecoration(this));
        mChampionListRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void onItemClicked(View view, int position) {
        Champion champion = mAdapter.getItem(position);
        Intent intent = new Intent(this, ChampionActivity.class);
        intent.putExtra(EXTRA_CHAMPION_ID, champion.getId());
        // 共享元素转场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View avatarIv = view.findViewById(R.id.iv_avatar);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    this, avatarIv, getString(R.string.shared_element_name_avatar));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
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
            // return new ChampionsLoader(SearchActivity.this, ChampionsLoader.Query.KEYWORDS, queryByKeyWord);
            return null;
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor cursor) {
           // mAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader loader) {
           //  mAdapter.swapCursor(null);
        }
    };
}
