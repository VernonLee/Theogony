package com.nodlee.theogony.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodlee.theogony.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkinViewHolder extends BaseViewHolder {
    @BindView(R.id.img_skin_cover_small)
    ImageView coverIv;
    @BindView(R.id.txt_skin_name)
    TextView nameIv;

    private OnItemClickedListener mOnItemClickedListener;

    public SkinViewHolder(View view, OnItemClickedListener clickedListener) {
        super(view);
        ButterKnife.bind(this, view);
        mOnItemClickedListener = clickedListener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickedListener != null)
            mOnItemClickedListener.onItemClicked(v, getAdapterPosition());
    }
}