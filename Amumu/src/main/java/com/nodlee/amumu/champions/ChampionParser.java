package com.nodlee.amumu.champions;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nodlee.amumu.bean.Champion;
import com.nodlee.amumu.bean.Skin;
import com.nodlee.amumu.uri.UriFactory;

import java.util.ArrayList;
import java.util.Map;

/**
 * 英雄数据解析者
 * Created by nodlee on 16/7/11.
 */
public class ChampionParser implements Parser<ArrayList<Champion> > {

    // 未解析之前原始数据串的JsonObject
    private JsonObject jsonObject;

    public ChampionParser(String resource) {
        if (TextUtils.isEmpty(resource)) {
            throw new IllegalArgumentException("source can not be null");
        }

        jsonObject = new JsonParser().parse(resource).getAsJsonObject();
    }

    @Override
    public String getVersion() {
        return jsonObject.has("version") ?
                jsonObject.get("version").getAsString() : null;
    }

    @Override
    public ArrayList<Champion> getData() {
        String version = null;
        JsonObject championsJsonObj = null;
        try {
            if (jsonObject.has("data")
                && (championsJsonObj = jsonObject.get("data").getAsJsonObject()) != null
                && (version = getVersion()) != null) {
                return parseChampions(championsJsonObj, version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Champion> parseChampions(JsonObject jsonObject, String version) throws Exception {
        ArrayList<Champion> listOfChampions = new ArrayList<>();
        Gson gson = new Gson();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonObject champJsonObject = entry.getValue().getAsJsonObject();
            Champion champion = gson.fromJson(champJsonObject, Champion.class);
            champion.setDummyTags(buildTags(champJsonObject.get("tags").getAsJsonArray()));
            Uri thumbnailUri = UriFactory.createThumbnailUri(version, String.format("%s.png", champion.getKey()));
            champion.setAvatar(thumbnailUri.toString());

            // 解析皮肤数据
            JsonArray skinsArr = champJsonObject.get("skins").getAsJsonArray();
            if (skinsArr != null && skinsArr.size() > 0) {
                champion.setSkins(parseSkins(gson, champion, skinsArr));
            }

            listOfChampions.add(champion);
        }

        return listOfChampions;
    }

    private ArrayList<Skin> parseSkins(Gson gson, Champion champion, JsonArray skinsArr) throws Exception {
        ArrayList<Skin> skins = new ArrayList<>();
        for (JsonElement element : skinsArr) {
            Skin skin = gson.fromJson(element, Skin.class);
            Uri coverUri = UriFactory.createSplashUri(String.format("%s_%d.jpg", champion.getKey(), skin.getNum()));
            skin.setCover(coverUri.toString());
            skin.setCid(champion.getId());

            // 如果name是”default“，改成(champion_name + title)
            if (skin.getName().equals("default")) {
                skin.setName(String.format("%1$s %2$s", champion.getName(), champion.getTitle()));
            }
            skins.add(skin);
        }
        return skins;
    }

    private String buildTags(JsonArray tagsArr) {
        if (tagsArr == null || tagsArr.size() == 0)
            return null;

        StringBuilder tagsBuilder = new StringBuilder();
        for (int i = 0; i < tagsArr.size(); i++) {
            tagsBuilder.append(String.valueOf(tagsArr.get(i))).append(",");
        }

        return tagsBuilder.toString();
    }
}
