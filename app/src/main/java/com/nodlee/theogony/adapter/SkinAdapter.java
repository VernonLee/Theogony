package com.nodlee.theogony.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodlee.amumu.bean.Skin;
import com.nodlee.theogony.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * 作者：nodlee
 * 时间：16/8/18
 * 说明：
 */
public class SkinAdapter extends RecyclerView.Adapter<SkinAdapter.ViewHolder> {
    private ImageLoader mImageLoader;
    private ArrayList<Skin> mSkins;
    private OnItemClickedListener mOnItemClickedListener;

    public SkinAdapter(Context context, ArrayList<Skin> skins) {
        mImageLoader = ImageLoader.getInstance();
        mSkins = skins;
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        mOnItemClickedListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_skins, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Skin skin = getItem(position);
        if (skin != null) {
            mImageLoader.displayImage(skin.getCover(), holder.coverIv);
            holder.nameIv.setText(skin.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mSkins == null ? 0 : mSkins.size();
    }

    public Skin getItem(int position) {
        if (position >= 0 && position < mSkins.size()) {
            return mSkins.get(position);
        }
        return null;
    }

    public class ViewHolder extends BaseViewHolder {
        private ImageView coverIv;
        private TextView nameIv;

        public ViewHolder(View view) {
            super(view);
            coverIv = (ImageView) view.findViewById(R.id.img_skin_cover_small);
            nameIv = (TextView) view.findViewById(R.id.txt_skin_name);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickedListener.onItemClicked(getAdapterPosition());
        }
    }
}
