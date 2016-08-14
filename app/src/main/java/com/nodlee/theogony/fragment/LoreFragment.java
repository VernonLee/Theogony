package com.nodlee.theogony.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.theogony.R;
import com.nodlee.theogony.db.FavoriteChampionManager;

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
    @BindView(R.id.btn_favorite)
    FloatingActionButton mFavoriteBtn;

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

        int fabBackgroundColor = getResources().getColor(R.color.md_primary);
        mFavoriteBtn.setBackgroundTintList(ColorStateList.valueOf(fabBackgroundColor));
        mFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavorite(!isFavorite());
            }
        });

        if (mChampion != null) {
            setLore(mChampion.getLore());
            mFavoriteBtn.setSelected(isFavorite());
        } else {
            mFavoriteBtn.hide();
        }
    }

    private void setLore(String lore) {
        mLoreTv.setText(Html.fromHtml("&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;" + lore));
    }

    private boolean isFavorite() {
        if (mChampion == null) {
            return false;
        } else {
            return FavoriteChampionManager.getInstance(getActivity()).isFavorite(mChampion.getId());
        }
    }

    private void setFavorite(boolean isFavorite) {
        if (mChampion == null) return;

        FavoriteChampionManager favoriteChampionManager = FavoriteChampionManager.getInstance(getActivity());
        if (isFavorite) {
            if (favoriteChampionManager.add(mChampion)) {
                mFavoriteBtn.setSelected(true);
            }
        } else {
            if (favoriteChampionManager.delete(mChampion)) {
                mFavoriteBtn.setSelected(false);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
