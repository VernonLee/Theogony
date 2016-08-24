package com.nodlee.theogony.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nodlee.amumu.bean.Skin;
import com.nodlee.theogony.R;
import com.nodlee.theogony.adapter.OnItemClickedListener;
import com.nodlee.theogony.adapter.SkinWithLoadMoreAdapter;
import com.nodlee.theogony.db.DatabaseOpenHelper;
import com.nodlee.theogony.db.SkinManager;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.view.AutoFitRecyclerView;
import com.nodlee.theogony.view.MarginDecoration;
import com.nodlee.theogony.view.OnScrolledToBottomListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：nodlee
 * 时间：16/8/17
 * 说明：皮肤库
 */
public class SkinListActivity extends BaseActivity implements OnItemClickedListener  {
    private static final int MESSAGE_SUCCESS = 1;
    private static final int MESSAGE_FAILED = 0;

    @BindView(R.id.recy_view_skin_list)
    AutoFitRecyclerView mSkinListRecyclerView;

    private SkinWithLoadMoreAdapter mSkinAdapter;
    private ArrayList<Skin> mSkins = new ArrayList<>();

    private int currentPage = 1; // 当前页码
    private int totalPage = 0; // 总页数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_list);
        ButterKnife.bind(this);
        getToolbar(R.drawable.ic_arrow_back_black, null);

        mSkinAdapter = new SkinWithLoadMoreAdapter(mSkins);
        mSkinListRecyclerView.setHasFixedSize(true);
        mSkinListRecyclerView.addItemDecoration(new MarginDecoration(this));
        mSkinListRecyclerView.setAdapter(mSkinAdapter);
        mSkinAdapter.setOnItemClickedListener(this);
        mSkinListRecyclerView.addOnScrollListener(new OnScrolledToBottomListener() {
            @Override
            public void onScrolledToBottom() {
                loadMore();
            }
        });
        GridLayoutManager manager = mSkinListRecyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mSkinAdapter.isLoadMoreView(position) ? 2 : 1;
            }
        });

        totalPage = SkinManager.getInstance(this).getTotalPage();
        mHandler.post(mRunnable);
    }

    private void loadMore() {
        if (totalPage > 0 && currentPage < totalPage && !mSkinAdapter.isLoading()) {
            mSkinAdapter.startLoading();
            // 计算下一页页码
            currentPage += 1;
            mHandler.postDelayed(mRunnable, 1500);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Skin skin = mSkinAdapter.getItem(position);
        Intent intent = new Intent(SkinListActivity.this, SkinActivity.class);
        intent.putExtra(SkinActivity.EXTRA_SKIN, skin);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View coverIv = view.findViewById(R.id.img_skin_cover_small);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    SkinListActivity.this, coverIv, getString(R.string.shared_element_name_skin_cover));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSkinAdapter.setLoaded();

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
            DatabaseOpenHelper.SkinCursor cursor = SkinManager.getInstance(SkinListActivity.this).getSkins(currentPage);
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
}
