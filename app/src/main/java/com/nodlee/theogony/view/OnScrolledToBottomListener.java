package com.nodlee.theogony.view;

import android.support.v7.widget.RecyclerView;
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
        RecyclerView.LayoutManager manager = view.getLayoutManager();
        View lastChildView = manager.getChildAt(view.getChildCount() - 1);
        int lastChildBottom = lastChildView.getBottom();
        int recyclerViewBottom = view.getBottom() - view.getPaddingBottom();
        int lastChildPosition = manager.getPosition(lastChildView);

        return lastChildBottom == recyclerViewBottom
                     && (lastChildPosition == manager.getItemCount() - 1);
    }

    public void onScrolledToBottom() {

    }
}
