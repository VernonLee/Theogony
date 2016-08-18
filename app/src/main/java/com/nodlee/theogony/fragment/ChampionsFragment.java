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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.theogony.R;
import com.nodlee.theogony.activity.ChampionActivity;
import com.nodlee.theogony.adapter.ChampionCursorAdapter;
import com.nodlee.theogony.adapter.OnItemClickedListener;
import com.nodlee.theogony.loader.ChampionsLoader;
import com.nodlee.theogony.utils.Constants;
import com.nodlee.theogony.view.AutoFitRecyclerView;
import com.nodlee.theogony.view.MarginDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Vernon Lee on 15-11-24.
 */
public class ChampionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClickedListener {
    private static final String EXTRA_TAG_KEY = "extra_tag";
    // LoaderID
    private static final int CHAMPIONS_LOADER_ID = 0;

    @BindView(R.id.swipe_refresh_champions)
    SwipeRefreshLayout mRefreshView;
    @BindView(R.id.recy_view_champions)
    AutoFitRecyclerView mRecyclerView;

    private ChampionCursorAdapter mAdapter;
    private Unbinder mUnbinder;

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
        mUnbinder = ButterKnife.bind(this, rootView);

        mRefreshView.setOnRefreshListener(this);
        mRefreshView.setColorSchemeResources(R.color.color_accent);
        mAdapter = new ChampionCursorAdapter(getActivity(), null);
        mAdapter.setOnItemClickedListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onItemClicked(int position) {
        Champion champion = mAdapter.getItem(position);
        if (champion != null) {
            Intent intent = new Intent(getActivity(), ChampionActivity.class);
            intent.putExtra(ChampionActivity.EXTRA_CHAMPION, champion);
            startActivity(intent);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(CHAMPIONS_LOADER_ID, getArguments(), mLoaderCallback);
        getActivity().getContentResolver().registerContentObserver(Constants.Champions.CONTENT_URI
            , true, mObserver);
    }

    @Override
    public void onRefresh() {
        Loader loader = getLoaderManager().getLoader(CHAMPIONS_LOADER_ID);
        if (loader != null) {
            loader.forceLoad();
        }
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

    private ContentObserver mObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Loader loader = getLoaderManager().getLoader(CHAMPIONS_LOADER_ID);
            if (loader != null) {
                loader.forceLoad();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        getActivity().getContentResolver().unregisterContentObserver(mObserver);
    }
}
