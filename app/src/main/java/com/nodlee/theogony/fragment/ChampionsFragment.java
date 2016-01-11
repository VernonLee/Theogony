/*
 * Copyright (c) 2015 Vernon Lee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.nodlee.theogony.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nodlee.theogony.R;
import com.nodlee.theogony.activity.ChampionActivity;
import com.nodlee.theogony.adapter.ChampionAdapter;
import com.nodlee.theogony.adapter.GridSpacingItemDecoration;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.ChampionsLoader;

import java.io.Serializable;


/**
 * Created by Vernon Lee on 15-11-24.
 */
public class ChampionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String EXTRA_TAG_KEY = "championTagKey";
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

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recy_view_champions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(SPAN_COUNT,
                (int) AndroidUtils.dpToPx(5, getActivity()), true));
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
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(0, getArguments(), mLoaderCallback);
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
        getLoaderManager().restartLoader(0, getArguments(), mLoaderCallback);
    }
}
