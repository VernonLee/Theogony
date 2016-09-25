package com.nodlee.theogony.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodlee.theogony.R;
import com.nodlee.amumu.bean.Champion;
import com.nodlee.theogony.db.DatabaseOpenHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vernon Lee on 15-11-24.
 */
public class ChampionCursorWithFooterAdapter extends ChampionCursorAdapter {
    private static final int VIEW_TYPE_FOOTER = 1;
    private static final int VIEW_TYPE_NORMAL = 2;

    public ChampionCursorWithFooterAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext())
                       .inflate(R.layout.count_panel, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item_champions, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, Cursor cursor) {
        if (viewHolder instanceof FooterViewHolder) {
            int count = getItemCount() - 1;
            ((FooterViewHolder)viewHolder).mCountTv.setText(String.format("%d位", count));
        } else {
            Champion champion = ((DatabaseOpenHelper.ChampionCursor) cursor).getChampion();
            if (champion != null) {
                ViewHolder holder = (ViewHolder) viewHolder;
                holder.nameTv.setText(champion.getName());
                mImageLoader.displayImage(champion.getAvatar(), holder.avatarIv);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (super.getItemCount() != 0) {
            return super.getItemCount() + 1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return isFooter(position) ? VIEW_TYPE_FOOTER : VIEW_TYPE_NORMAL;
    }

    public boolean isFooter(int position) {
        return position == getItemCount() - 1;
    }

    public class FooterViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_count)
        public TextView mCountTv;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) { }
    }
}