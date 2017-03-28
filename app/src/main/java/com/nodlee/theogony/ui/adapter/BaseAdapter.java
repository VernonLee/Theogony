package com.nodlee.theogony.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



/**
 * 作者：nodlee
 * 时间：2017/3/25
 * 说明：
 */

public abstract class BaseAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    protected List<T> mData;

    public BaseAdapter(List<T> data) {
        this.mData = data;
    }

    public T getItem(int position) {
        if (mData == null || position >= mData.size() || position < 0) {
            return null;
        }
        return mData.get(position);
    }

    public void setData(List<T> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        if (data != null) {
            mData.clear();
            mData.addAll(data);
        }
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
