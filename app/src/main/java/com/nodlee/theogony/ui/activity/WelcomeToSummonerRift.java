package com.nodlee.theogony.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.nodlee.theogony.App;
import com.nodlee.theogony.R;
import com.nodlee.theogony.task.InitDragonDataTask;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.RealmProvider;
import com.nodlee.theogony.utils.ThemeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    TextView mProcessTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_to_summoner_rift);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (RealmProvider.getInstance().isEmpty()) {
            initDragonData();
        } else {
            startActivity(new Intent(this, ChampionListActivity.class));
            finish();
        }
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
        if (!TextUtils.isEmpty(appKey)) {
            Bundle args = new Bundle();
            args.putString(InitDragonDataTask.EXTRA_APP_KEY, appKey);
            mTask.execute(args);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTask != null && !mTask.isCancelled()) {
            mTask.cancel(true);
            mTask = null;
        }
    }

    private InitDragonDataTask mTask = new InitDragonDataTask() {
        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                startActivity(new Intent(WelcomeToSummonerRift.this, ChampionListActivity.class));
                finish();
            } else {
                AndroidUtils.showToast(WelcomeToSummonerRift.this, "检查网络连接重试");
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mProcessTv.setText(values[0]);
        }
    };
}
