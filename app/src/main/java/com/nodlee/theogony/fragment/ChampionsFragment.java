package com.nodlee.theogony.fragment;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nodlee.theogony.R;
import com.nodlee.theogony.activity.BaseActivity;
import com.nodlee.theogony.activity.ChampionActivity;
import com.nodlee.theogony.adapter.ChampionAdapter;
import com.nodlee.theogony.adapter.GridSpacingItemDecoration;
import com.nodlee.amumu.bean.Champion;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.loader.ChampionsLoader;
import com.nodlee.theogony.utils.Constants;
import com.nodlee.theogony.view.RecyclerViewScrollListener;


/**
 * Created by Vernon Lee on 15-11-24.
 */
public class ChampionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String EXTRA_TAG_KEY = ChampionsFragment.class.getName();
    private static final int LOADER_CHAMPIONS = 0;
    private static int SPAN_COUNT = 4;

    private SwipeRefreshLayout mRefreshView;
    private ChampionAdapter mAdapter;

    public static ChampionsFragment newInstance(String championTagKey) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TAG_KEY, championTagKey);

        ChampionsFragment fragment = new ChampionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_champions, container, false);

        mRefreshView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_champions);
        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setColorSchemeResources(R.color.md_accent);

        mAdapter = new ChampionAdapter(getActivity(), null);
        mAdapter.setOnItemClickedListener(new ChampionAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Champion champion = mAdapter.getItem(position);
                if (champion != null) {
                    Intent intent = new Intent(getActivity(), ChampionActivity.class);
                    intent.putExtra(ChampionActivity.EXTRA_CHAMPION, champion);
                    startActivity(intent);
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recy_view_champions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(SPAN_COUNT,
                (int) AndroidUtils.dpToPx(5, getActivity()), true));
        recyclerView.addOnScrollListener(new RecyclerViewScrollListener(getActivity()) {

            @Override
            public void scrollingUp() {
                hideToolbar();
            }

            @Override
            public void scrollingDown() {
                showToolbar();
            }
        });
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(LOADER_CHAMPIONS, getArguments(), mLoaderCallback);
        getActivity().getContentResolver().registerContentObserver(Constants.Champions.CONTENT_URI
            , true, mObserver);
    }

    private LoaderManager.LoaderCallbacks mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            mRefreshView.setRefreshing(true);
            String champTagKey = args.getString(EXTRA_TAG_KEY);
            return new ChampionsLoader(getActivity(), ChampionsLoader.Query.TAGS, champTagKey);
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor cursor) {
            mRefreshView.setRefreshing(false);
            mAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader loader) {
            mAdapter.swapCursor(null);
        }
    };

    @Override
    public void onRefresh() {
        Loader loader = getLoaderManager().getLoader(LOADER_CHAMPIONS);
        if (loader != null) {
            loader.forceLoad();
        }
    }

    private void hideToolbar() {
        ActionBar actionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void showToolbar() {
        ActionBar actionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    private ContentObserver mObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Loader loader = getLoaderManager().getLoader(LOADER_CHAMPIONS);
            if (loader != null) {
                loader.forceLoad();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getContentResolver().unregisterContentObserver(mObserver);
    }
}
