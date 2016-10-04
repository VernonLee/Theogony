package com.nodlee.amumu.champions;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.nodlee.amumu.util.LocaleLibrary;
import com.nodlee.amumu.bean.Champion;
import com.nodlee.amumu.uri.UriFactory;
import com.nodlee.amumu.util.HttpFetchr;

import java.util.ArrayList;

/**
 * 英雄数据请求者
 * Created by nodlee on 16/6/15.
 */
public class ChampionsRequester implements Requester<ChampionParser> {
    private static final String TAG = ChampionsRequester.class.getName();
    private static final boolean DEBUG = true;

    @Override
    public void asyncRequest(final String locale, final RequestCallback callback) {
        if (TextUtils.isEmpty(locale))
            throw new IllegalArgumentException("locale can not be null");

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Uri requestUrl = UriFactory.createChampionstUri(Amumu.sAppKey, locale);

                if (DEBUG) Log.d(TAG, "requestUrl:" + requestUrl);

                String response = new HttpFetchr().getUrl(requestUrl);
                if (response == null) {
                    callback.obtainMessage(FAILED, -1).sendToTarget();
                    return;
                }

                if (DEBUG) Log.d(TAG, "response:" + response);

                ChampionParser parser = new ChampionParser(response);
                ArrayList<Champion> champions = parser.getData();
                if (champions != null && champions.size() > 0) {
                    Object[] result = {parser.getVersion(), champions};
                    callback.obtainMessage(SUCCESS, result).sendToTarget();
                } else {
                    callback.obtainMessage(FAILED, -1).sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public ChampionParser syncRequest(String locale) {
        if (TextUtils.isEmpty(locale))
            throw new IllegalArgumentException("locale can not be null");

        final Uri requestUrl = UriFactory.createChampionstUri(Amumu.sAppKey, locale);

        if (DEBUG) Log.d(TAG, "requestUrl:" + requestUrl);

        String response = new HttpFetchr().getUrl(requestUrl);
        if (response == null) {
            return null;
        }

        if (DEBUG) Log.d(TAG, "response:" + response);

        return new ChampionParser(response);
    }
}
