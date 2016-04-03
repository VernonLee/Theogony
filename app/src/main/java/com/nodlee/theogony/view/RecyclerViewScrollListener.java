package com.nodlee.theogony.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nodlee.theogony.R;

/**
 * Created by nodlee on 16-4-3.
 */
public abstract class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private Context mCtx;
    private int toolbarHeight = 0;
    /** RecyclerView滑动距离 */
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    public RecyclerViewScrollListener(Context ctx) {
        mCtx = ctx;
        toolbarHeight = getToolbarHeight();
    }

    private int getToolbarHeight() {
        final TypedArray styledAttributes = mCtx.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        //show views if first item is first visible position and views are hidden
        if (firstVisibleItem == 0) {
            if(!controlsVisible) {
                scrollingDown();
                controlsVisible = true;
            }
        } else {
            if (scrolledDistance > toolbarHeight && controlsVisible) {
                scrollingUp();
                controlsVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -toolbarHeight && !controlsVisible) {
                scrollingDown();
                controlsVisible = true;
                scrolledDistance = 0;
            }
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    /**
     * RecyclerView向上滑动ToolBar距离后触发该事件
     */
    public abstract void scrollingUp();

    /**
     * RecyclerView向下滑动ToolBar距离后触发该事件
     */
    public abstract void scrollingDown();
}
