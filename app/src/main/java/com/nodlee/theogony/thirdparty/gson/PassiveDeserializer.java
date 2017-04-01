package com.nodlee.theogony.thirdparty.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nodlee.theogony.bean.Image;
import com.nodlee.theogony.bean.Passive;
import com.nodlee.theogony.utils.RiotGameUtils;

import java.lang.reflect.Type;

import static com.nodlee.theogony.thirdparty.gson.SafetyGSONParser.getString;
import static com.nodlee.theogony.thirdparty.gson.SafetyGSONParser.getType;

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
        String name                 = getString(rootJsonObj, "name");
        String description          = getString(rootJsonObj, "description");
        String sanitizedDescription = getString(rootJsonObj, "sanitizedDescription");
        Image image                 = getType(context, rootJsonObj, "image", Image.class);

        String passiveImage = null;
        if (image != null) {
           passiveImage =  RiotGameUtils.createPassiveImageUrl(image.getFull());
        }

        return new Passive(name, description, sanitizedDescription, passiveImage);
    }
}
