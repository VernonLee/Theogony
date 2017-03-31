package com.nodlee.theogony.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Spell;
import com.nodlee.theogony.databinding.ListItemSpellsBinding;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：nodlee
 * 时间：2017/3/25
 * 说明：
 */

public class SpellAdapter extends BaseAdapter<Spell, SpellAdapter.ViewHolder> {

    private RequestManager mGlide;

    public SpellAdapter(RequestManager glide, List<Spell> spells) {
        super(spells);
        this.mGlide = glide;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_spells, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Spell spell = getItem(position);
        holder.binder.setSpell(spell);
        loadImage(mGlide, holder.mSpellImageIv, spell.getImage());
    }

    private void loadImage(RequestManager glide, ImageView imageView, String url) {
        glide.load(url)
                .crossFade()
                .fitCenter()
                .placeholder(R.drawable.img_default_spell)
                .into(imageView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_spell_image)
        ImageView mSpellImageIv;

        public ListItemSpellsBinding binder;

        public ViewHolder(View itemView) {
            super(itemView);
            binder = DataBindingUtil.bind(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
