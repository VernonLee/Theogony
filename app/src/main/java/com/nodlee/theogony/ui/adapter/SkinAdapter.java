package com.nodlee.theogony.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Skin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vernon Lee on 15-11-27.
 */
public class SkinAdapter extends BaseAdapter<Skin, SkinAdapter.ViewHolder> {
    private RequestManager mGlide;
    private ItemClickedListener mItemClickedListener;

    public SkinAdapter(RequestManager glide, List<Skin> skinList) {
        super(skinList);
        mGlide = glide;
    }

    public void setItemClickedListener(ItemClickedListener itemClickedListener) {
        mItemClickedListener = itemClickedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_skin, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Skin skin = getItem(position);
        holder.nameIv.setText(skin.getName());
        loadImage(mGlide, holder.coverIv, skin.getImage());
    }

    private void loadImage(RequestManager glide, ImageView imageView, String url) {
        glide.load(url)
                .thumbnail(0.1f)
                .fitCenter()
                .placeholder(R.drawable.img_default_skin)
                .into(imageView);
    }

    public class ViewHolder extends BaseClickableViewHolder {
        @BindView(R.id.iv_skin_image)
        ImageView coverIv;
        @BindView(R.id.txt_skin_name)
        TextView nameIv;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickedListener != null)
                mItemClickedListener.onItemClicked(v, getAdapterPosition());
        }
    }
}
