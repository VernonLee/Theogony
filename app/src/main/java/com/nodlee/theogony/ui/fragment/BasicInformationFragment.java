package com.nodlee.theogony.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.core.ChampionManager;
import com.nodlee.theogony.databinding.FragBasicInformationBinding;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：nodlee
 * 时间：2017/3/24
 * 说明：
 */

public class BasicInformationFragment extends BaseFragment {

    private Unbinder mUnbinder;

    public static BasicInformationFragment getInstance(int championId) {
        Bundle args = new Bundle();
        args.putInt("champion_id", championId);

        BasicInformationFragment fragment = new BasicInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragBasicInformationBinding binder = DataBindingUtil.inflate(inflater, R.layout.frag_basic_information, container, false);
        View rootView = binder.getRoot();
        mUnbinder = ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        int championId = args.getInt("champion_id");
        Champion champion = ChampionManager.getInstance().get(championId);
        binder.setChampion(champion);
        binder.setInfo(champion.getInfo());

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
