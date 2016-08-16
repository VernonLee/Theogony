package com.nodlee.theogony.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.nodlee.theogony.R;

/**
 * Created by nodlee on 16-4-3.
 */
public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {
    private Context mCtx;
    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
        mCtx = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
         return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed > 0) {
            hideFab(child);
        } else if (dyConsumed < 0) {
            showFab(child);
        }
    }

    private void hideFab(FloatingActionButton fab) {
        int fabMargin = (int) mCtx.getResources().getDimension(R.dimen.margin_normal);
        int bottom = fab.getHeight() + fabMargin;
        fab.animate()
           .translationY(bottom)
           .setInterpolator(new AccelerateInterpolator(2))
           .start();
    }

    private void showFab(FloatingActionButton fab) {
        fab.animate().translationY(0)
           .setInterpolator(new DecelerateInterpolator(2))
           .start();
    }
}
