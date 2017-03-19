package com.nodlee.theogony.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.amumu.bean.Skin;
import com.nodlee.theogony.R;
import com.nodlee.theogony.ui.activity.SkinActivity;
import com.nodlee.theogony.adapter.OnItemClickedListener;
import com.nodlee.theogony.adapter.SkinCursorAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：nodlee
 * 时间：16/8/11
 * 说明：
 */
public class SkinListFragment extends Fragment implements OnItemClickedListener {

    @BindView(R.id.recy_view_skins)
    RecyclerView mSkinsRecyclerView;

    private Unbinder mUnbinder;
    private SkinCursorAdapter mSkinCursorAdapter;
    private Champion mChampion;

    public static SkinListFragment getInstance(Champion champion) {
        Bundle args = new Bundle();
        args.putSerializable("champion", champion);

        SkinListFragment fragment = new SkinListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_skins, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        mSkinCursorAdapter = new SkinCursorAdapter(getActivity(), null);
        mSkinCursorAdapter.setOnItemClickedListener(this);
        mSkinsRecyclerView.setHasFixedSize(true);
        mSkinsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSkinsRecyclerView.setAdapter(mSkinCursorAdapter);

        return rootView;
    }

    @Override
    public void onItemClicked(View view, int position) {
        Skin skin = mSkinCursorAdapter.getItem(position);
        if (skin != null) {
            Intent intent = new Intent(getActivity(), SkinActivity.class);
            intent.putExtra(SkinActivity.EXTRA_SKIN, skin);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View coverIv = view.findViewById(R.id.img_skin_cover_big);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        getActivity(), coverIv, getString(R.string.shared_element_name_skin_cover));
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChampion = (Champion) getArguments().getSerializable("champion");
        if (mChampion != null) {
            getActivity().getSupportLoaderManager().initLoader(1, null, mLoaderCallbacks);
        }
    }

    private LoaderManager.LoaderCallbacks mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            return new SkinsLoader(getActivity(), mChampion.getId());
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor cursor) {
            mSkinCursorAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader loader) {
            mSkinCursorAdapter.swapCursor(null);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
