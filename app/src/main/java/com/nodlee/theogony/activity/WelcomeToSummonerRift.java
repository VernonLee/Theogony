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
package com.nodlee.theogony.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.nodlee.riotgames.Locale;
import com.nodlee.theogony.R;
import com.nodlee.theogony.task.InitLolStaticDataTask;
import com.nodlee.theogony.utils.Constants;
import com.nodlee.theogony.utils.UserUtils;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_to_summoner_rift);

        mProcessMsgTv = (TextView) findViewById(R.id.txt_progress_msg);

        // 检查APP_KEY是否完整
        if (TextUtils.isEmpty(Constants.RIOT_APP_KEY)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.warning_dialog_title)
                    .setMessage(R.string.miss_app_key_message)
                    .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which) {
                            welcomeToSummerRift();
                        }
                    })
                    .create().show();
            // 检查是否第一次打开应用
        } else if (UserUtils.isFirstBlood(this)) {
            selectLocale();
        } else {
            startCountDown();
        }
    }

    private void selectLocale() {
        final String[] selectedLocale = {Locale.getDefaultLocaleCode()};

        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.select_locale_dialog_title)
                .setSingleChoiceItems(Locale.sLocaleNames, Locale.DEFAULT_LOCALE,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedLocale[0] = Locale.getLocaleCode(which);
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

    private void startTask(String locale) {
        new FirstBloodTask(this.getApplicationContext(), locale).execute();
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
        startActivity(new Intent(this, ChampionsActivity.class));
        finish();
    }

    private void setProcessMsg(int resId) {
        mProcessMsgTv.setText(resId);
    }

    private class FirstBloodTask extends InitLolStaticDataTask {

        public FirstBloodTask(Context context, String locale) {
            super(context, locale);
        }

        @Override
        protected void onPreExecute() {
            setProcessMsg(R.string.request_data_progress_message);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                setProcessMsg(R.string.init_successful_progress_message);
            } else {
                setProcessMsg(R.string.init_failed_progress_message);
            }
            welcomeToSummerRift();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            setProcessMsg(values[0]);
        }
    }
}
