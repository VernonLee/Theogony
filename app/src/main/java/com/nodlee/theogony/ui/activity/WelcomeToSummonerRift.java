package com.nodlee.theogony.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.nodlee.theogony.App;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.DragonData;
import com.nodlee.theogony.core.ApiImpl;
import com.nodlee.theogony.core.DragonDataManager;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.LogHelper;
import com.nodlee.theogony.thirdparty.realm.RealmProvider;
import com.nodlee.theogony.utils.RiotGameUtils;
import com.nodlee.theogony.utils.ThemeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

/**
 * ////////////////////////////
 * //    欢迎来到召唤师峡谷     //
 * ///////////////////////////
 * <p/>
 * Created by Vernon Lee on 15-11-27.
 */
public class WelcomeToSummonerRift extends AppCompatActivity {
    @BindView(R.id.txt_progress)
    TextSwitcher mProcessTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_to_summoner_rift);
        ButterKnife.bind(this);

        mProcessTv.setInAnimation(this, R.anim.bottom_in);
        mProcessTv.setOutAnimation(this, R.anim.top_out);
        mProcessTv.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public TextView makeView() {
                TextView textView = (TextView) LayoutInflater.from(WelcomeToSummonerRift.this)
                        .inflate(R.layout.progress_text, null);
                return textView;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean emptyDataBase = RealmProvider.getInstance().isEmpty();
        boolean outDate = DragonDataManager.getInstance().isOutDate();
        if (emptyDataBase || outDate) {
            initDragonData();
        } else {
            startActivity(new Intent(this, ChampionListActivity.class));
            finish();
        }
    }

    @OnClick(R.id.txt_progress)
    void retry() {
        mProcessTv.setClickable(false);
        initDragonData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAppKey();
        int mode = ThemeUtils.isNightMode(this) ? MODE_NIGHT_YES : MODE_NIGHT_NO;
        getDelegate().setLocalNightMode(mode); // 回复日/夜间模式
    }

    public void checkAppKey() {
        String appKey = AndroidUtils.getMetaData(this, App.META_DATA_APP_KEY);
        if (TextUtils.isEmpty(appKey)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_title_warning)
                    .setMessage(R.string.message_miss_app_key)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    })
                    .create().show();
        }
    }

    private void initDragonData() {
        String appKey = AndroidUtils.getMetaData(this, App.META_DATA_APP_KEY);
        if (TextUtils.isEmpty(appKey)) return;
        Bundle args = new Bundle();
        args.putString(InitDragonDataTask.EXTRA_APP_KEY, appKey);
        new InitDragonDataTask().execute(args);
    }

    public class InitDragonDataTask extends AsyncTask<Bundle, String, Boolean> {

        public static final String EXTRA_APP_KEY = "app_key";
        public static final String EXTRA_LANGUAGE_CODE = "language_code";

        @Override
        protected void onPostExecute(Boolean success) {
            if (isFinishing()) return;

            if (!success) { // 启用重试机制
                mProcessTv.setClickable(true);
            } else {
                startActivity(new Intent(WelcomeToSummonerRift.this, ChampionListActivity.class));
                finish();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mProcessTv.setText(values[0]);
        }

        @Override
        protected Boolean doInBackground(Bundle... params) {
            Bundle args = params[0];
            if (args == null) {
                return false;
            }

            String languageCode;
            if (args.containsKey(EXTRA_LANGUAGE_CODE)) {
                languageCode = args.getString(EXTRA_LANGUAGE_CODE);
            } else {
                languageCode = AndroidUtils.getSystemLocale();
            }

            String appKey;
            if (args.containsKey(EXTRA_APP_KEY)) {
                appKey = args.getString(EXTRA_APP_KEY);
            } else {
                LogHelper.LOGE("未指定APP_KEY");
                return false;
            }

            publishProgress("开始获取应用数据...");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            publishProgress("大约耗时30S左右，请耐心等待");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final long start = System.currentTimeMillis();

            ApiImpl impl = ApiImpl.getInstance();
            String dragonDataUrl = RiotGameUtils.createDragonDataUrl(languageCode, appKey);
            LogHelper.LOGW("请求数据源：" + dragonDataUrl);

            // 第一阶段
            publishProgress("初始化...");

            String jsonResult = impl.request(dragonDataUrl);
            if (jsonResult == null) {
                publishProgress("请求失败，检查网络重试");
                LogHelper.LOGW("jsonResult为空");
                return false;
            }

            // 第二阶段
            publishProgress("解析数据...");

            DragonData dragonData = impl.parseJsonWithGson(jsonResult);
            if (dragonData == null) {
                LogHelper.LOGW("championData为空");
                return false;
            }
            dragonData.setLanguageCode(languageCode);
            dragonData.setOutDate(false);

            // 第三阶段
            publishProgress("写入数据...");

            boolean success = impl.writeToRealmDataBase(dragonData);
            LogHelper.LOGW("数据写入：" + (success ? "成功" : "失败"));

            publishProgress("初始化完毕");

            // 统计三个阶段耗时
            LogHelper.LOGD("耗时总计：" + (System.currentTimeMillis() - start) + "ms");

            return success;
        }
    }
}
