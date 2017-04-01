package com.nodlee.theogony.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：nodlee
 * 时间：16/8/26
 * 说明：
 */
public class ChampionAdapter extends BaseAdapter<Champion, ChampionAdapter.ClickableViewHolder> {
    private RequestManager glide;
    private ItemClickedListener mItemClickedListener;

    public ChampionAdapter(RequestManager glide, List<Champion> data) {
        super(data);
        this.glide = glide;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClickableViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_champions, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        Champion champion = getItem(position);
        holder.nameTv.setText(champion.getName());
        loadImage(glide, holder.avatarIv, champion.getImage());
    }

    /**
     * 加载图片，单独传RequestManager便于Glide在Activity生命周期
     * 内管理图片加载请求。
     *
     * @param glide
     * @param imageView
     * @param url
     */
    private void loadImage(RequestManager glide, ImageView imageView, String url) {
        glide.load(url)
                .crossFade()
                .fitCenter()
                .placeholder(R.drawable.img_default_head)
                .into(imageView);
    }

    public void setItemClickListener(ItemClickedListener listener) {
        this.mItemClickedListener = listener;
    }


    public class ClickableViewHolder extends BaseClickableViewHolder {
        @BindView(R.id.iv_avatar)
        public ImageView avatarIv;
        @BindView(R.id.txt_champion_name)
        public TextView nameTv;

        public ClickableViewHolder(View view) {
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
