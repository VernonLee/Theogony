package com.nodlee.theogony.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.theogony.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：nodlee
 * 时间：16/8/11
 * 说明：英雄背景故事
 */
public class LoreFragment extends Fragment {

    @BindView(R.id.txt_champion_lore)
    TextView mLoreTv;

    private Champion mChampion;
    private Unbinder mUnbinder;

    public static LoreFragment getInstance(Champion champion) {
        Bundle args = new Bundle();
        args.putSerializable("champion", champion);

        LoreFragment fragment = new LoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_lore, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChampion = (Champion) getArguments().getSerializable("champion");
        if (mChampion != null) {
            setLore(mChampion.getLore());
        }
    }

    private void setLore(String lore) {
        mLoreTv.setText(Html.fromHtml("&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;" + lore));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
