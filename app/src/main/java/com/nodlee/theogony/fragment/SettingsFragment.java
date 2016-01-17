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
package com.nodlee.theogony.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nodlee.riotgames.Locale;
import com.nodlee.theogony.R;
import com.nodlee.theogony.activity.AboutAppActivity;
import com.nodlee.theogony.activity.ChampionsActivity;
import com.nodlee.theogony.task.InitLolStaticDataTask;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.Constants;
import com.nodlee.theogony.utils.UserUtils;

/**
 * Created by Vernon Lee on 15-11-25.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private static final String TAG = SettingsFragment.class.getName();
    private Preference mLocalePref, mVersionPref, mAboutPref;
    private static String sPrefLocaleKey, sPrefVersionKey, sPrefAboutKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        sPrefLocaleKey = getString(R.string.pref_locale_key);
        sPrefVersionKey = getString(R.string.pref_version_key);
        sPrefAboutKey = getString(R.string.pref_about_app_key);

        mLocalePref = findPreference(sPrefLocaleKey);
        mLocalePref.setOnPreferenceClickListener(this);
        mVersionPref = findPreference(sPrefVersionKey);
        mVersionPref.setOnPreferenceClickListener(this);
        mAboutPref = findPreference(sPrefAboutKey);
        mAboutPref.setOnPreferenceClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String staticDataVersion = UserUtils.getLolStaticDataVersion(getActivity());
        if (!TextUtils.isEmpty(staticDataVersion)) {
            mVersionPref.setSummary(staticDataVersion);
        }

        String staticDataLocaleCode = UserUtils.getLolStaticDataLocale(getActivity());
        if (!TextUtils.isEmpty(staticDataLocaleCode)) {
            mLocalePref.setSummary(Locale.getLocalName(staticDataLocaleCode));
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        final String preferenceKey = preference.getKey();

        if (preferenceKey.equals(sPrefAboutKey)) {
            startActivity(new Intent(getActivity(), AboutAppActivity.class));
        } else if (preferenceKey.equals(sPrefLocaleKey)) {
            selectLocale();
        }
        return true;
    }

    private void selectLocale() {
        final String[] selectedLocale = {Locale.getDefaultLocaleCode()};

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.select_locale_dialog_title)
                .setSingleChoiceItems(Locale.sLocaleNames, Locale.DEFAULT_LOCALE,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedLocale[0] = Locale.getLocaleCode(which);
                                Log.i(TAG, "选择语言：" + which + " " + selectedLocale[0]);
                            }
                        }
                )
                .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String localeLocale = UserUtils.getLolStaticDataLocale(getActivity());
                        if (selectedLocale[0].equals(localeLocale)) {
                            AndroidUtils.showToast(getActivity(), R.string.reselect_locale_message);
                            return;
                        }

                        switchLocale(selectedLocale[0]);
                    }
                })
                .setNegativeButton(R.string.cancel_button, null)
                .show();
    }

    private void switchLocale(String locale) {
        new FetchStaticDataTask(getActivity().getApplicationContext(), locale).execute();
    }

    private class FetchStaticDataTask extends InitLolStaticDataTask {
        private ProgressDialog mDialog;

        public FetchStaticDataTask(Context context, String locale) {
            super(context, locale);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage(getString(R.string.switch_locale_process_message));
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    AndroidUtils.showSnackBar(getView(), R.string.task_moved_to_background);
                }
            });
            mDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mDialog.dismiss();

//            if (isAdded()) {
//                sendSwitchSuccessNotification(mCtx);
//            }

            if (mLocalePref != null) {
                String locale = Locale.getLocalName(mLocale);
                mLocalePref.setSummary(locale);
            }

            mCtx.getContentResolver().notifyChange(Constants.Champions.CONTENT_URI,
                    null, false);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (mDialog != null && mDialog.isShowing()) {
                String processMessage = getString(values[0]);
                mDialog.setMessage(processMessage);
            }
        }
    }

//
//    private void sendSwitchSuccessNotification(Context context) {
//        Intent resultIntent = new Intent(context, ChampionsActivity.class);
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
//                PendingIntent.FLAG_CANCEL_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setSmallIcon(R.mipmap.app_logo);
//        builder.setContentTitle(getString(R.string.switch_success_notification_title));
//        builder.setContentText(getString(R.string.switch_success_notification_title));
//        builder.setContentIntent(resultPendingIntent);
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(0, builder.build());
//    }
}
