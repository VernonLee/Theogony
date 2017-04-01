package com.nodlee.theogony;

import android.app.Application;
import android.content.Context;
import android.os.Debug;

import com.nodlee.theogony.utils.RealmProvider;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import io.realm.Realm;


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
