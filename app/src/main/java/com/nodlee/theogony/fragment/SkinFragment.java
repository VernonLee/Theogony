package com.nodlee.theogony.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nodlee.amumu.bean.Skin;
import com.nodlee.theogony.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Vernon Lee on 15-11-27.
 */
public class SkinFragment extends Fragment {

    @BindView(R.id.img_skin_cover_big)
    PhotoView mCoverIv;

    private Bitmap mCoverBitmap;

    public static SkinFragment newInstance(Skin skin) {
        Bundle args = new Bundle();
        args.putSerializable("skin", skin);

        SkinFragment fragment = new SkinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_skin, container, false);
        ButterKnife.bind(this, rootView);

        Skin skin = (Skin) getArguments().getSerializable("skin");
        ImageLoader.getInstance().loadImage(skin.getCover(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                mCoverBitmap = bitmap;
                if (bitmap != null) {
                    mCoverIv.setImageBitmap(bitmap);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCoverBitmap != null && !mCoverBitmap.isRecycled()) {
            mCoverBitmap.recycle();
        }
    }
}
