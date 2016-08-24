package com.nodlee.theogony.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * 作者：nodlee
 * 时间：16/8/18
 * 说明：
 */
public class OnScrolledToBottomListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (isScrolledToBottom(recyclerView))
            onScrolledToBottom();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    private boolean isScrolledToBottom(RecyclerView view) {
        int lastCompleteVisiblePos = -1;

        RecyclerView.LayoutManager manager = view.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            lastCompleteVisiblePos = ((GridLayoutManager)manager).findLastCompletelyVisibleItemPosition();
        } else if (manager instanceof LinearLayoutManager) {
            lastCompleteVisiblePos = ((LinearLayoutManager)manager).findLastCompletelyVisibleItemPosition();
        }

        return lastCompleteVisiblePos > 0 && lastCompleteVisiblePos == manager.getItemCount() - 1 - 1; // footer除外
    }

    public void onScrolledToBottom() { }
}
