package com.nodlee.theogony.thirdparty.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nodlee.theogony.bean.Skin;

import java.lang.reflect.Type;

import static com.nodlee.theogony.thirdparty.gson.SafetyGSONParser.*;

/**
 * 作者：nodlee
 * 时间：2017/3/17
 * 说明：
 */

public class SkinDeserializer implements JsonDeserializer<Skin> {

    @Override
    public Skin deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;

        JsonObject rootJsonObj = json.getAsJsonObject();
        int id      = getInt(rootJsonObj, "id");
        String name = getString(rootJsonObj, "name");
        int num     = getInt(rootJsonObj, "num");

        return new Skin(id, name, num);
    }
}
