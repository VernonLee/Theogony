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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.amumu.bean.Skin;
import com.nodlee.amumu.champions.ChampionsRequester;
import com.nodlee.amumu.champions.RequestCallback;
import com.nodlee.amumu.util.LocaleLibrary;
import com.nodlee.theogony.R;
import com.nodlee.theogony.activity.AboutAppActivity;
import com.nodlee.theogony.activity.ChampionListActivity;
import com.nodlee.theogony.db.ChampionManager;
import com.nodlee.theogony.db.SkinManager;
import com.nodlee.theogony.utils.AndroidUtils;
import com.nodlee.theogony.utils.CacheManager;
import com.nodlee.theogony.utils.UserUtils;
import com.nodlee.theogony.view.LicenseDialog;

import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-11-25.
 * ButterKnife截止2016/8/14不支持Preference绑定
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private static final String TAG = SettingsFragment.class.getName();

    private Preference mLocalePref, mCacheSizePref;
    private Preference mAboutPref, mOpenSourcePref, mAppVersionPref;
    private Preference mFeedbackPref;

    private String sLocalPrefKey, sCacheSizePrefKey, sAboutAppPrefKey,
                   mOSLPrefKey, mAppVersionPrefKey, mFeedbackPrefKey;

    private ProgressDialog mDialog;

    // 请求联盟数据重试次数
    private int retryTimes = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        sLocalPrefKey = getString(R.string.pref_key_locale);
        sCacheSizePrefKey = getString(R.string.pref_key_cache_size);
        sAboutAppPrefKey = getString(R.string.pref_key_about_app);
        mOSLPrefKey = getString(R.string.pref_key_open_source_license);
        mAppVersionPrefKey = getString(R.string.pref_key_app_version);
        mFeedbackPrefKey = getString(R.string.pref_key_feedback);

        mLocalePref = findPreference(sLocalPrefKey);
        mLocalePref.setOnPreferenceClickListener(this);
        mCacheSizePref = findPreference(sCacheSizePrefKey);
        mCacheSizePref.setOnPreferenceClickListener(this);

        mAboutPref = findPreference(sAboutAppPrefKey);
        mAboutPref.setOnPreferenceClickListener(this);
        mOpenSourcePref = findPreference(mOSLPrefKey);
        mOpenSourcePref.setOnPreferenceClickListener(this);
        mAppVersionPref = findPreference(mAppVersionPrefKey);
        mAppVersionPref.setOnPreferenceClickListener(this);

        mFeedbackPref = findPreference(mFeedbackPrefKey);
        mFeedbackPref.setOnPreferenceClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String localeName = UserUtils.getLolStaticDataLocale(getActivity());
        mLocalePref.setSummary(localeName);

        String appVersion = AndroidUtils.getAppVersion(getActivity());
        mAppVersionPref.setSummary(appVersion);

        String cacheSize = CacheManager.getCacheSize(getActivity());
        mCacheSizePref.setSummary(cacheSize);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        final String preferenceKey = preference.getKey();

        if (preferenceKey.equals(sLocalPrefKey)) { // 切换语言

             selectLocale();

        } else if (preferenceKey.equals(sCacheSizePrefKey)) { // 清除缓存

            cleanCache();

        } else if (preferenceKey.equals(sAboutAppPrefKey)) { // 关于应用

            startActivity(new Intent(getActivity(), AboutAppActivity.class));

        } else if (preferenceKey.equals(mOSLPrefKey)) { // 阅读开源许可证

            new LicenseDialog(getActivity()).create();

        } else if (preferenceKey.equals(mFeedbackPrefKey)) { // 意见反馈

            new AlertDialog.Builder(getActivity())
                           .setTitle("意见反馈")
                           .setMessage(R.string.feedback_message)
                           .setPositiveButton("确定", null)
                           .create().show();

        }

        return true;
    }

    private void cleanCache() {
        boolean success = CacheManager.clean(getActivity());
        String cacheSize = CacheManager.getCacheSize(getActivity());
        mCacheSizePref.setSummary(cacheSize);
        AndroidUtils.showToast(getActivity(), success ? "清除成功" : "清除失败");
    }

    private void selectLocale() {
        final LocaleLibrary locale = LocaleLibrary.getInstance();
        // 选中的语言
        final int defaultLocaleIndex = 0;
        final LocaleLibrary.Entry[] selectedLocaleArr = {locale.get(defaultLocaleIndex)};

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.select_locale_dialog_title)
                .setSingleChoiceItems(locale.toKeyArray(), defaultLocaleIndex,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedLocaleArr[0] = locale.get(which);
                                Log.i(TAG, "选择语言：" + which + " " + selectedLocaleArr[0]);
                            }
                        })
                .setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedLocale = selectedLocaleArr[0].value;
                        String localLocale = UserUtils.getLolStaticDataLocale(getActivity());
                        if (!selectedLocale.equals(localLocale)) {
                            mDialog = new ProgressDialog(getActivity());
                            mDialog.setMessage("请求数据中...");
                            mDialog.setCancelable(false);
                            mDialog.show();
                            switchLocale(selectedLocale);
                        }
                    }
                }).show();
    }

    private void switchLocale(final String locale) {
        new ChampionsRequester().asyncRequest(locale, new RequestCallback() {
            @Override
            public void onSuccess(Object[] result) {
                mDialog.dismiss();
                String version = (String) result[0];
                ArrayList<Champion> champions = (ArrayList<Champion>) result[1];
                if (champions != null) {
                    writeToDatabase(champions);
                    UserUtils.setLolStaticDataLocal(getActivity(), locale);
                    UserUtils.setLolStaticDataVerion(getActivity(), version);
                    AndroidUtils.showToast(getActivity(), "语言切换成功");
                }
            }

            @Override
            public void onFailed(int errCode) {
                retry(locale);
            }
        });
    }

    private void retry(String locale) {
        if (retryTimes > 0) {
            Log.d("xxx", "请求失败，第" + retryTimes +"次重试");
            switchLocale(locale);
            retryTimes--;
        } else {
            mDialog.dismiss();
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
        Intent resultIntent = new Intent(context, ChampionListActivity.class);
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
