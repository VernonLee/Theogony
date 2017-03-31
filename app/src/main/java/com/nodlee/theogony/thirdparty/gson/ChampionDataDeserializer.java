package com.nodlee.theogony.thirdparty.gson;

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

import static com.nodlee.theogony.thirdparty.gson.SafetyGSONParser.*;
/**
 * 作者：nodlee
 * 时间：2017/3/16
 * 说明：
 */

public class ChampionDataDeserializer implements JsonDeserializer<DragonData> {

    @Override
    public DragonData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;

        JsonObject rootObject = json.getAsJsonObject();
        String type                 = getString(rootObject, "type");
        String format               = getString(rootObject, "format");
        String version              = getString(rootObject, "version");
        JsonObject championsJsonObj = getJsonObject(rootObject, "data");

        RealmList<Champion> championList = new RealmList();
        if (championsJsonObj != null) {
            for (Map.Entry<String, JsonElement> entry : championsJsonObj.entrySet()) {
                Champion champion = context.deserialize(entry.getValue(), Champion.class);
                championList.add(champion);
            }
        }

        return new DragonData(type, format, version, championList);
    }
}
