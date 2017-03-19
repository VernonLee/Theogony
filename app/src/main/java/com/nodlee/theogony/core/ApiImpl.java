package com.nodlee.theogony.core;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.ChampionData;
import com.nodlee.theogony.bean.Passive;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.bean.Spell;
import com.nodlee.theogony.gson.ChampionDataDeserializer;
import com.nodlee.theogony.gson.ChampionDeserializer;
import com.nodlee.theogony.gson.PassiveDeserializer;
import com.nodlee.theogony.gson.SkinDeserializer;
import com.nodlee.theogony.gson.SpellDeserializer;

import java.io.IOException;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：nodlee
 * 时间：2017/3/19
 * 说明：本地业务实现类
 */

public class ApiImpl implements Api {

    @Override
    public String loadDragonDataFromServer(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChampionData parseJsonWithGson(String json) {
        if (TextUtils.isEmpty(json)) return null;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChampionData.class, new ChampionDataDeserializer())
                .registerTypeAdapter(Champion.class, new ChampionDeserializer())
                .registerTypeAdapter(Passive.class, new PassiveDeserializer())
                .registerTypeAdapter(Skin.class, new SkinDeserializer())
                .registerTypeAdapter(Spell.class, new SpellDeserializer())
                .create();

        try {
            ChampionData championData = gson.fromJson(json, ChampionData.class);
            return championData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean writeToRealmDataBase(ChampionData championData) {
        if (championData == null) return false;

        Realm realm = Realm.getDefaultInstance();
        // 开始写入数据
        realm.beginTransaction();
        realm.copyToRealm(championData);
        realm.commitTransaction();
        return true;
    }

    @Override
    public boolean updateRealmDataBase(ChampionData newChampionData) {
        return false;
    }
}
