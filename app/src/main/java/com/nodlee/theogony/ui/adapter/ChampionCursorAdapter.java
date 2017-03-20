package com.nodlee.theogony.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.theogony.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：nodlee
 * 时间：16/8/26
 * 说明：
 */
public class ChampionCursorAdapter extends BaseCursorAdapter<BaseViewHolder> {
    protected static ImageLoader mImageLoader;

    public ChampionCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_champions, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, Cursor cursor) {
//        Champion champion = ((DatabaseOpenHelper.ChampionCursor) cursor).getChampion();
//        if (champion != null) {
//            ViewHolder holder = (ViewHolder) viewHolder;
//            holder.nameTv.setText(champion.getName());
//            mImageLoader.displayImage(champion.getAvatar(), holder.avatarIv);
//        }
    }

    public Champion getItem(int position) {
//        DatabaseOpenHelper.ChampionCursor cursor = (DatabaseOpenHelper.ChampionCursor) getCursor();
//        if (cursor != null && cursor.moveToPosition(position)) {
//            return cursor.getChampion();
//        }
        return null;
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_avatar)
        public ImageView avatarIv;
        @BindView(R.id.txt_champion_name)
        public TextView nameTv;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
            onItemClickedListener.onItemClicked(v, getAdapterPosition());
        }
    }
}
