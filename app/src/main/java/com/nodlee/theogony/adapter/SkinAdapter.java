/*
 * Copyright (c) 2015 Vernon Lee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.nodlee.theogony.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.db.DatabaseOpenHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Vernon Lee on 15-11-27.
 */
public class SkinAdapter extends BaseCursorAdapter<SkinAdapter.ViewHolder> {
    private static ImageLoader mImageLoader;

    public SkinAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public SkinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_skins, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        Skin skin = ((DatabaseOpenHelper.SkinCursor) cursor).getSkin();
        if (skin != null) {
            viewHolder.nameIv.setText(skin.getName());
            mImageLoader.displayImage(skin.getCover(), viewHolder.coverIv);
        }
    }

    public Skin getItem(int position) {
        DatabaseOpenHelper.SkinCursor cursor = (DatabaseOpenHelper.SkinCursor) getCursor();
        if (cursor != null && cursor.moveToPosition(position)) {
            return cursor.getSkin();
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
            onItemClickedListener.onItemClicked(getAdapterPosition());
        }
    }
}
