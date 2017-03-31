package com.nodlee.theogony.thirdparty.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * 作者：nodlee
 * 时间：2017/3/31
 * 说明：
 */

public class SafetyGSONParser {

    public static String getString(JsonObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key)) {
            return jsonObject.get(key).getAsString();
        }
        return null;
    }

    public static int getInt(JsonObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key)) {
            return jsonObject.get(key).getAsInt();
        }
        return 0;
    }

    public static <T> T getType(JsonDeserializationContext context, JsonObject jsonObject, String key, Type typeOfT) {
        if (jsonObject != null && jsonObject.has(key)) {
            return context.deserialize(jsonObject.get(key), typeOfT);
        }
        return null;
    }

    public static JsonObject getJsonObject(JsonObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key)) {
            return jsonObject.get(key).getAsJsonObject();
        }
        return null;
    }

    public static JsonArray getArray(JsonObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key)) {
            return jsonObject.get(key).getAsJsonArray();
        }
        return null;
    }
}
