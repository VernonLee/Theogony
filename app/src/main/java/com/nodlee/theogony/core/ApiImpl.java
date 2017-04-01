package com.nodlee.theogony.core;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.DragonData;
import com.nodlee.theogony.bean.Passive;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.bean.Spell;
import com.nodlee.theogony.thirdparty.gson.ChampionDataDeserializer;
import com.nodlee.theogony.thirdparty.gson.ChampionDeserializer;
import com.nodlee.theogony.thirdparty.gson.PassiveDeserializer;
import com.nodlee.theogony.thirdparty.gson.SkinDeserializer;
import com.nodlee.theogony.thirdparty.gson.SpellDeserializer;
import com.nodlee.theogony.utils.LogHelper;
import com.nodlee.theogony.utils.RealmProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.HttpCodec;

/**
 * 作者：nodlee
 * 时间：2017/3/19
 * 说明：本地业务实现类
 */

public class ApiImpl implements Api {

    private static ApiImpl sApi;

    private ApiImpl() {
    }

    public static ApiImpl getInstance() {
        if (sApi == null) {
            sApi = new ApiImpl();
        }
        return sApi;
    }

    @Override
    public String request(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            LogHelper.LOGD("请求响应，Code:" + response.code());
            return response.code() == 200 ? response.body().string() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DragonData parseJsonWithGson(String json) {
        if (TextUtils.isEmpty(json)) return null;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DragonData.class, new ChampionDataDeserializer())
                .registerTypeAdapter(Champion.class, new ChampionDeserializer())
                .registerTypeAdapter(Passive.class, new PassiveDeserializer())
                .registerTypeAdapter(Skin.class, new SkinDeserializer())
                .registerTypeAdapter(Spell.class, new SpellDeserializer())
                .create();

        try {
            DragonData dragonData = gson.fromJson(json, DragonData.class);
            return dragonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean writeToRealmDataBase(DragonData dragonData) {
        if (dragonData == null) return false;

        Realm realm = RealmProvider.getInstance().getRealm();
        try {
            realm.beginTransaction();
            realm.deleteAll();
            realm.copyToRealm(dragonData);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            LogHelper.LOGW("数据写入失败：" + e);
        } finally {
            realm.close();
        }
        return false;
    }

    @Override
    public boolean updateRealmDataBase(DragonData newDragonData) {
        return false;
    }
}
