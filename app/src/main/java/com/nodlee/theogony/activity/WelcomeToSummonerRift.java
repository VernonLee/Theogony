package com.nodlee.theogony.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.TextView;

import com.nodlee.amumu.util.LocaleLibrary;
import com.nodlee.amumu.bean.Champion;
import com.nodlee.amumu.bean.Skin;
import com.nodlee.amumu.champions.ChampionsRequester;
import com.nodlee.amumu.champions.RequestCallback;
import com.nodlee.theogony.R;
import com.nodlee.theogony.db.ChampionManager;
import com.nodlee.theogony.db.SkinManager;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.UserUtils;

import java.util.ArrayList;

/**
 * ////////////////////////////
 * //    欢迎来到召唤师峡谷     //
 * ///////////////////////////
 * <p/>
 * Created by Vernon Lee on 15-11-27.
 */
public class WelcomeToSummonerRift extends AppCompatActivity {
    private static final String TAG = WelcomeToSummonerRift.class.getName();
    private static final int TOTAL_MILLIS = 2500;
    private static final int INTERVAL = 1000;

    private TextView mProcessMsgTv;

    // 重试次数
    private int retryTimes = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_to_summoner_rift);

        mProcessMsgTv = (TextView) findViewById(R.id.txt_progress_msg);

        // 恢复夜间/日间模式
        if (UserUtils.isNightMode(this)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // 检查是否第一次打开应用
        if (UserUtils.isFirstBlood(this)) {
            selectLocale();
        } else {
            startCountDown();
        }
    }

    private void selectLocale() {
        final LocaleLibrary locale = LocaleLibrary.getInstance();
        // 选中的语言
        final int defaultLocaleIndex = 0;
        final LocaleLibrary.Entry[] selectedLocale = {locale.get(defaultLocaleIndex)};

        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.select_locale_dialog_title)
                .setSingleChoiceItems(locale.toKeyArray(), defaultLocaleIndex,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedLocale[0] = locale.get(which);
                                Log.i(TAG, "选择语言：" + which + " " + selectedLocale[0]);
                            }
                        })
                .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startTask(selectedLocale[0]);
            }
        }).show();
    }

    private void startTask(final LocaleLibrary.Entry locale) {
        setProcessMsg("请求数据中...");
        new ChampionsRequester().asyncRequest(locale, new RequestCallback() {
            @Override
            public void onSuccess(ArrayList<Champion> champions) {
                writeToDatabase(champions);
                UserUtils.setLolStaticDataLocal(WelcomeToSummonerRift.this, locale.value);
            }

            @Override
            public void onFailed(int errCode) {
                retry(locale);
            }
        });
    }

    private void retry(LocaleLibrary.Entry locale) {
        if (retryTimes > 0) {
            Log.d("xxx", "请求失败，第" + retryTimes +"次重试");
            startTask(locale);
            retryTimes--;
        } else {
            setProcessMsg("数据请求失败...");
            welcomeToSummerRift();
        }
    }

    private void writeToDatabase(final ArrayList<Champion> champions) {
        setProcessMsg("数据写入中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Skin> skins = new ArrayList<Skin>();
                ChampionManager.getInstance(WelcomeToSummonerRift.this).add(champions);
                for (Champion champion : champions) {
                    skins.addAll(champion.getSkins());
                }
                SkinManager.getInstance(WelcomeToSummonerRift.this).add(skins);
                UserUtils.setFirstBlood(WelcomeToSummonerRift.this, false);
                welcomeToSummerRift();
            }
        }).start();
    }

    private void startCountDown() {
        CountDownTimer timer = new CountDownTimer(TOTAL_MILLIS, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Do nothing...
            }

            @Override
            public void onFinish() {
                welcomeToSummerRift();
            }
        };
        timer.start();
    }

    private void welcomeToSummerRift() {
        startActivity(new Intent(this, ChampionListActivity.class));
        finish();
    }

    private void setProcessMsg(String resId) {
        mProcessMsgTv.setText(resId);
    }
}
