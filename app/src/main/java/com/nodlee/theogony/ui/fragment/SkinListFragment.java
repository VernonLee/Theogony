package com.nodlee.theogony.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.core.ChampionManager;
import com.nodlee.theogony.ui.activity.SkinActivity;
import com.nodlee.theogony.ui.adapter.ItemClickedListener;
import com.nodlee.theogony.ui.adapter.SkinAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.nodlee.theogony.ui.activity.SkinActivity.EXTRA_SKIN_ID;

/**
 * 作者：nodlee
 * 时间：16/8/11
 * 说明：
 */
public class SkinListFragment extends BaseFragment implements ItemClickedListener {

    @BindView(R.id.recy_view_skins)
    RecyclerView mSkinsRecyclerView;

    private Unbinder mUnbinder;
    private SkinAdapter mSkinAdapter;

    public static SkinListFragment getInstance(int championId) {
        Bundle args = new Bundle();
        args.putInt("championId", championId);

        SkinListFragment fragment = new SkinListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_skins, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        int championId = args.getInt("championId", -1);
        Champion champion = ChampionManager.getInstance().get(championId);

        mSkinAdapter = new SkinAdapter(Glide.with(this), champion.getSkins());
        mSkinAdapter.setItemClickedListener(this);
        mSkinsRecyclerView.setHasFixedSize(true);
        mSkinsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSkinsRecyclerView.setAdapter(mSkinAdapter);

        return rootView;
    }

    @Override
    public void onItemClicked(View view, int position) {
        Skin skin = mSkinAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), SkinActivity.class);
        intent.putExtra(EXTRA_SKIN_ID, skin.getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View coverIv = view.findViewById(R.id.iv_skin_image);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(), coverIv, getString(R.string.shared_element_name_skin_cover));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
