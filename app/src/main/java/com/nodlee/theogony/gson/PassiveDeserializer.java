package com.nodlee.theogony.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nodlee.theogony.bean.Image;
import com.nodlee.theogony.bean.Passive;

import java.lang.reflect.Type;

/**
 * 作者：nodlee
 * 时间：2017/3/17
 * 说明：重新格式化图片地址
 */

public class PassiveDeserializer implements JsonDeserializer<Passive> {

    @Override
    public Passive deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;

        JsonObject rootJsonObj = json.getAsJsonObject();
        String name = rootJsonObj.get("name").getAsString();
        String description = rootJsonObj.get("description").getAsString();
        String sanitizedDescription = rootJsonObj.get("sanitizedDescription").getAsString();
        Image image = context.deserialize(rootJsonObj.get("image"), Image.class);

        Passive passiveRealm = new Passive();
        passiveRealm.setName(name);
        passiveRealm.setDescription(description);
        passiveRealm.setSanitizedDescription(sanitizedDescription);
        passiveRealm.setImage(image.getFull());
        return passiveRealm;
    }
}
