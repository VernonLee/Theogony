package com.nodlee.theogony.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nodlee.theogony.bean.Image;
import com.nodlee.theogony.bean.Skin;

import java.lang.reflect.Type;

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
        int id = rootJsonObj.get("id").getAsInt();
        String name = rootJsonObj.get("name").getAsString();
        int num = rootJsonObj.get("num").getAsInt();
        // Image image = context.deserialize(rootJsonObj.get("image"), Image.class);

        Skin skin = new Skin();
        skin.setId(id);
        skin.setName(name);
        skin.setNum(num);
        // skin.setImage(image.getFull());
        return skin;
    }
}
