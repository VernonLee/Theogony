package com.nodlee.theogony.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.DragonData;

import java.lang.reflect.Type;
import java.util.Map;

import io.realm.RealmList;


/**
 * 作者：nodlee
 * 时间：2017/3/16
 * 说明：
 */

public class ChampionDataDeserializer implements JsonDeserializer<DragonData> {

    @Override
    public DragonData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;

        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement typeElement = jsonObject.get("type");
        JsonElement formatElement = jsonObject.get("format");
        JsonElement versionElement = jsonObject.get("version");
        JsonObject dataObj = jsonObject.get("data").getAsJsonObject();

        RealmList<Champion> dataList = new RealmList();
        for (Map.Entry<String, JsonElement> entry : dataObj.entrySet()) {
            Champion champion = context.deserialize(entry.getValue(), Champion.class);
            dataList.add(champion);
        }

        DragonData dragonData = new DragonData();
        dragonData.setType(typeElement.getAsString());
        dragonData.setFormat(formatElement.getAsString());
        dragonData.setVersion(versionElement.getAsString());
        dragonData.setData(dataList);
        return dragonData;
    }
}
