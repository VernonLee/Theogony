package com.nodlee.theogony;

import android.app.Application;

import com.nodlee.theogony.thirdparty.realm.RealmProvider;


/**
 * Created by Vernon Lee on 15-11-19.
 */
public class App extends Application {

    public static final String META_DATA_APP_KEY = "app_key";

    @Override
    public void onCreate() {
        super.onCreate();
        RealmProvider.getInstance().init(this);
    }
}
