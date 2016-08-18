package com.nodlee.theogony.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.nodlee.amumu.bean.Skin;
import com.nodlee.theogony.R;
import com.nodlee.theogony.adapter.OnItemClickedListener;
import com.nodlee.theogony.adapter.SkinAdapter;
import com.nodlee.theogony.db.DatabaseOpenHelper;
import com.nodlee.theogony.db.SkinManager;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.view.OnScrolledToBottomListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：nodlee
 * 时间：16/8/17
 * 说明：
 */
public class SkinListActivity extends BaseActivity {
    private static final int MESSAGE_SUCCESS = 1;
    private static final int MESSAGE_FAILED = 0;

    @BindView(R.id.recy_view_skin_list)
    RecyclerView mSkinListRecyclerView;
    @BindView(R.id.txt_load_more)
    TextView mLoadMoreTv;

    private boolean isLoading; // 加载中状态
    private int currentPage = 1; // 当前页码
    private int totalPage = 0; // 总页数
    private SkinAdapter mSkinAdapter;
    private ArrayList<Skin> mSkins = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_list);
        ButterKnife.bind(this);
        getToolbar(R.drawable.ic_arrow_back_black, null);

        mSkinAdapter = new SkinAdapter(this, mSkins);
        mSkinListRecyclerView.setHasFixedSize(true);
        mSkinListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSkinListRecyclerView.setAdapter(mSkinAdapter);
        mSkinListRecyclerView.addOnScrollListener(new OnScrolledToBottomListener() {
            @Override
            public void onScrolledToBottom() {
                 loadMore();
            }
        });
        mSkinAdapter.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Skin skin = mSkins.get(position);
                if (skin != null) {
                    Intent intent = new Intent(SkinListActivity.this, SkinActivity.class);
                    intent.putExtra(SkinActivity.EXTRA_SKIN, skin);
                    intent.putExtra(SkinActivity.EXTRA_CHAMPION_ID, skin.getCid());
                    startActivity(intent);
                }
            }
        });

        totalPage = SkinManager.getInstance(this).getTotalPage();
        requestSkins();
    }

    private void loadMore() {
        if (isLoading) return;

        boolean isLastPage = currentPage == totalPage;
        isLoading = true;
        String loadMoreDesc = isLastPage ? "全部数据加载完毕" : String.format("加载%d/%d", currentPage, totalPage);
        mLoadMoreTv.setText(loadMoreDesc);
        mLoadMoreTv.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
        mLoadMoreTv.setAnimation(animation);
        animation.start();

        if (!isLastPage && totalPage > 0) {
            currentPage += 1;
            requestSkins();
        } else {
            finishLoadMore();
        }
    }

    private void finishLoadMore() {
        if (!isLoading) return;

        isLoading = false;
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
        mLoadMoreTv.setAnimation(animation);
        mLoadMoreTv.setVisibility(View.GONE);
    }

    private void requestSkins() {
        new Thread(mRunnable).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finishLoadMore();

            switch (msg.what) {
                case MESSAGE_FAILED:
                    if (!isFinishing())
                        AndroidUtils.showToast(SkinListActivity.this, "加载失败");
                    break;
                case MESSAGE_SUCCESS:
                    ArrayList<Skin> skins = (ArrayList<Skin>) msg.obj;
                    int lastOldPosition = mSkins.size() - 1;
                    mSkins.addAll(skins);
                    mSkinAdapter.notifyItemRangeChanged(lastOldPosition, skins.size());
                    break;
            }
        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            DatabaseOpenHelper.SkinCursor cursor = SkinManager.getInstance(SkinListActivity.this)
                                                              .getSkins(currentPage);
            if (cursor != null) {
                ArrayList<Skin> skins = SkinManager.cursorToArrayList(cursor);
                Message msg = new Message();
                msg.what = MESSAGE_SUCCESS;
                msg.obj = skins;
                mHandler.sendMessage(msg);
            } else {
                mHandler.sendEmptyMessage(MESSAGE_FAILED);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadMoreTv.getAnimation() != null) {
            mLoadMoreTv.clearAnimation();
        }
    }
}
