package com.nodlee.theogony.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vernon Lee on 15-11-27.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public abstract void onClick(View v);
}
