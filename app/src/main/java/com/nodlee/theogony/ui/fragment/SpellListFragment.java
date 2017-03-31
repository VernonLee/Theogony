package com.nodlee.theogony.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Passive;
import com.nodlee.theogony.core.ChampionManager;
import com.nodlee.theogony.databinding.FragSpellsBinding;
import com.nodlee.theogony.ui.adapter.SpellAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：nodlee
 * 时间：2017/3/24
 * 说明：
 */

public class SpellListFragment extends BaseFragment {

    @BindView(R.id.iv_passive_image)
    ImageView mPassiveImageIv;
    @BindView(R.id.recy_view_spells)
    RecyclerView mRecyclerView;

    public static SpellListFragment getInstance(int championId) {
        Bundle args = new Bundle();
        args.putInt("champion_id", championId);

        SpellListFragment fragment = new SpellListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragSpellsBinding binder = DataBindingUtil.inflate(inflater, R.layout.frag_spells, container, false);
        View rootView = binder.getRoot();
        ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        int championId = args.getInt("champion_id");
        Champion champion = ChampionManager.getInstance().get(championId);
        Passive passive = champion.getPassive();
        binder.setPassive(passive);
        Glide.with(this).load(passive.getImage())
                .placeholder(R.drawable.img_default_spell)
                .into(mPassiveImageIv);

        SpellAdapter adapter = new SpellAdapter(Glide.with(this), champion.getSpells());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }
}
