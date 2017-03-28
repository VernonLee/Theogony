package com.nodlee.theogony.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Build;
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

import com.bumptech.glide.Glide;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.ui.activity.ChampionActivity;
import com.nodlee.theogony.ui.adapter.ChampionAdapter;
import com.nodlee.theogony.ui.adapter.ItemClickedListener;
import com.nodlee.theogony.loader.ChampionsLoader;
import com.nodlee.theogony.utils.Constants;
import com.nodlee.theogony.ui.view.AutoFitRecyclerView;
import com.nodlee.theogony.ui.view.MarginDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.nodlee.theogony.ui.activity.ChampionActivity.EXTRA_CHAMPION_ID;


/**
 * Created by Vernon Lee on 15-11-24.
 * 备注：SpanSize代表跨度，即item占多少单元格，三表示item占三个格子，而1表示一个格子
 */
public class ChampionListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ItemClickedListener {
    private static final String EXTRA_TAG_KEY = "extra_tag";
    // LoaderID
    private static final int CHAMPIONS_LOADER_ID = 0;

    @BindView(R.id.swipe_refresh_champions)
    SwipeRefreshLayout mRefreshView;
    @BindView(R.id.recy_view_champions)
    AutoFitRecyclerView mRecyclerView;

    private ChampionAdapter mAdapter;
    private Unbinder mUnbinder;

    public static ChampionListFragment newInstance(String championTagKey) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TAG_KEY, championTagKey);

        ChampionListFragment fragment = new ChampionListFragment();
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
        mAdapter = new ChampionAdapter(Glide.with(this));
        mAdapter.setItemClickListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onItemClicked(View itemView, int position) {
        Champion champion = mAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), ChampionActivity.class);
        intent.putExtra(EXTRA_CHAMPION_ID, champion.getId());
        // 共享元素转场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View avatarIv = itemView.findViewById(R.id.iv_avatar);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(), avatarIv, getString(R.string.shared_element_name_avatar));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(CHAMPIONS_LOADER_ID, getArguments(), mLoaderCallback);
        getActivity().getContentResolver().registerContentObserver(Constants.Champions.CONTENT_URI, true, mObserver);
    }

    @Override
    public void onRefresh() {
        Loader loader = getLoaderManager().getLoader(CHAMPIONS_LOADER_ID);
        if (loader != null) {
            loader.forceLoad();
        }
    }

    private LoaderManager.LoaderCallbacks mLoaderCallback = new LoaderManager.LoaderCallbacks<List<Champion>>() {

        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            mRefreshView.setRefreshing(true);
            String champTagKey = args.getString(EXTRA_TAG_KEY);
            return new ChampionsLoader(getActivity(), ChampionsLoader.Action.TAG, champTagKey);
        }

        @Override
        public void onLoadFinished(Loader<List<Champion>> loader, List<Champion> data) {
            mRefreshView.setRefreshing(false);
            mAdapter.setData(data);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader loader) {
            // do nothing
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
