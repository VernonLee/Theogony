package com.nodlee.theogony.ui.adapter;

import android.view.View;

public interface OnItemClickedListener {
    /**
     * 在单元格被点击的时候调用
     *
     * @param position
     */
    void onItemClicked(View view, int position);
}