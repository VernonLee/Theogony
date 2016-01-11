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

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nodlee.riotgames.Locale;
import com.nodlee.theogony.R;
import com.nodlee.theogony.activity.AboutAppActivity;
import com.nodlee.theogony.utils.UserUtils;

/**
 * Created by Vernon Lee on 15-11-25.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
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
        }
        return true;
    }
}
