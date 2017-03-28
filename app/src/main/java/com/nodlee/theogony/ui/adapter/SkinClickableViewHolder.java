package com.nodlee.theogony.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodlee.theogony.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkinClickableViewHolder extends BaseClickableViewHolder {
    @BindView(R.id.img_skin_cover_small)
    ImageView coverIv;
    @BindView(R.id.txt_skin_name)
    TextView nameIv;

    private ItemClickedListener mItemClickedListener;

    public SkinClickableViewHolder(View view, ItemClickedListener clickedListener) {
        super(view);
        ButterKnife.bind(this, view);
        mItemClickedListener = clickedListener;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickedListener != null)
            mItemClickedListener.onItemClicked(v, getAdapterPosition());
    }
}