package com.nodlee.theogony.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Skin;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：nodlee
 * 时间：16/8/24
 * 说明：
 */
public class SkinWithLoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int VIEW_TYPE_LOAD_MORE = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private ImageLoader mImageLoader;
    private ArrayList<Skin> mSkins;
    private ItemClickedListener mItemClickedListener;

    private boolean isLoading = false;
    private LoadMoreViewHolder mLoadMoreView;

    public SkinWithLoadMoreAdapter(ArrayList<Skin> skins) {
        mImageLoader = ImageLoader.getInstance();
        mSkins = skins;
    }

    public void setItemClickedListener(ItemClickedListener listener) {
        mItemClickedListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOAD_MORE) {
            return new LoadMoreViewHolder(LayoutInflater.from(parent.getContext())
                       .inflate(R.layout.load_more, parent, false));
        } else {
            View contentView = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.grid_item_skin_small, parent, false);
            return new SkinClickableViewHolder(contentView, mItemClickedListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreViewHolder) {
            mLoadMoreView = (LoadMoreViewHolder) holder;
        } else {
            SkinClickableViewHolder viewHolder = (SkinClickableViewHolder) holder;
            Skin skin = getItem(position);
            if (skin != null) {
                mImageLoader.displayImage(skin.getImage(), viewHolder.coverIv);
                viewHolder.nameIv.setText(skin.getName());
            }
        }
    }

    public void startLoading() {
        if (mLoadMoreView != null) {
            mLoadMoreView.show();
        }
        isLoading = true;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoaded() {
        if (mLoadMoreView != null) {
            mLoadMoreView.hide();
        }
        isLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return  isLoadMoreView(position) ? VIEW_TYPE_LOAD_MORE : VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mSkins == null ? 0 : mSkins.size() + 1;
    }

    public boolean isLoadMoreView(int position) {
        return position == getItemCount() - 1;
    }

    public Skin getItem(int position) {
        if (position >= 0 && position < mSkins.size()) {
            return mSkins.get(position);
        }
        return null;
    }

    public class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_page_desc)
        TextView mTextView;
        @BindView(R.id.progress_load_more)
        ProgressBar mProgressBar;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void hide() {
            mTextView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        }

        public void show() {
            mTextView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}
