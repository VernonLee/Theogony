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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nodlee.amumu.util.LocaleLibrary;
import com.nodlee.amumu.bean.Champion;
import com.nodlee.amumu.champions.ChampionsRequester;
import com.nodlee.amumu.champions.RequestCallback;
import com.nodlee.amumu.bean.Skin;
import com.nodlee.theogony.R;
import com.nodlee.theogony.activity.AboutAppActivity;
import com.nodlee.theogony.activity.ChampionsActivity;
import com.nodlee.theogony.db.ChampionManager;
import com.nodlee.theogony.db.SkinManager;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.UserUtils;

import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-11-25.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private static final String TAG = SettingsFragment.class.getName();
    private Preference mLocalePref, mVersionPref, mAboutPref;
    private static String sPrefLocaleKey, sPrefVersionKey, sPrefAboutKey;

    // 重试次数
    private int retryTimes = 3;

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
//        String staticDataVersion = UserUtils.getLolStaticDataVersion(getActivity());
//        if (!TextUtils.isEmpty(staticDataVersion)) {
//            mVersionPref.setSummary(staticDataVersion);
//        }

        String staticDataLocaleCode = UserUtils.getLolStaticDataLocale(getActivity());
        if (!TextUtils.isEmpty(staticDataLocaleCode)) {
            // mLocalePref.setSummary(LocaleLibrary.getLocalName(staticDataLocaleCode));
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
        final LocaleLibrary locale = LocaleLibrary.getInstance();
        // 选中的语言
        final int defaultLocaleIndex = 0;
        final LocaleLibrary.Entry[] selectedLocale = {locale.get(defaultLocaleIndex)};

        new AlertDialog.Builder(getActivity())
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
                        switchLocale(selectedLocale[0]);
                    }
                }).show();
    }

    private void switchLocale(final LocaleLibrary.Entry locale) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("请求数据中...");
        dialog.setCancelable(false);
        dialog.show();

        new ChampionsRequester().asyncRequest(locale, new RequestCallback() {
            @Override
            public void onSuccess(ArrayList<Champion> champions) {
                dialog.dismiss();
                writeToDatabase(champions);
                UserUtils.setLolStaticDataLocal(getActivity(), locale.value);
            }

            @Override
            public void onFailed(int errCode) {
                retry(dialog, locale);
            }
        });
    }

    private void retry(ProgressDialog dialog, LocaleLibrary.Entry locale) {
        if (retryTimes > 0) {
            Log.d("xxx", "请求失败，第" + retryTimes +"次重试");
            switchLocale(locale);
            retryTimes--;
        } else {
            dialog.dismiss();
            AndroidUtils.showToast(getActivity(), "请求失败");
        }
    }

    private void writeToDatabase(final ArrayList<Champion> champions) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Skin> skins = new ArrayList<Skin>();
                ChampionManager.getInstance(getActivity()).add(champions);
                for (Champion champion : champions) {
                    skins.addAll(champion.getSkins());
                }
                SkinManager.getInstance(getActivity()).add(skins);
                sendSwitchSuccessNotification(getActivity());
            }
        }).start();
    }


    private void sendSwitchSuccessNotification(Context context) {
        Intent resultIntent = new Intent(context, ChampionsActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.app_logo);
        builder.setContentTitle(getString(R.string.switch_success_notification_title));
        builder.setContentText(getString(R.string.switch_success_notification_title));
        builder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());
    }
}
