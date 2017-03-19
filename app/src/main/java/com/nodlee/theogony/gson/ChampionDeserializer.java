package com.nodlee.theogony.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Image;
import com.nodlee.theogony.bean.Info;
import com.nodlee.theogony.bean.Passive;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.bean.Spell;
import com.nodlee.theogony.bean.Stats;

import java.lang.reflect.Type;

import io.realm.RealmList;

/**
 * 作者：nodlee
 * 时间：2017/3/17
 * 说明：
 */

public class ChampionDeserializer implements JsonDeserializer<Champion> {

    public static final String SEPARATOR = ",";

    @Override
    public Champion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;

        JsonObject rootJsonObj = json.getAsJsonObject();
        int id = rootJsonObj.get("id").getAsInt();
        String key = rootJsonObj.get("key").getAsString();
        String name = rootJsonObj.get("name").getAsString();
        String title = rootJsonObj.get("title").getAsString();
        String lore = rootJsonObj.get("lore").getAsString();
        String blurb = rootJsonObj.get("blurb").getAsString();
        String partype = rootJsonObj.get("partype").getAsString();

        Info infoRealm = context.deserialize(rootJsonObj.get("info"), Info.class);
        Stats statsRealm = context.deserialize(rootJsonObj.get("stats"), Stats.class);
        RealmList<Spell> spells = context.deserialize(rootJsonObj.get("spells"), new TypeToken<RealmList<Spell>>(){}.getType());
        RealmList<Skin> skins = context.deserialize(rootJsonObj.get("skins"), new TypeToken<RealmList<Skin>>(){}.getType());
        Image image = context.deserialize(rootJsonObj.get("image"), Image.class);
        Passive passiveRealm = context.deserialize(rootJsonObj.get("passive"), Passive.class);

        JsonArray allTipsArray = rootJsonObj.get("allytips").getAsJsonArray();
        JsonArray enemyTipsArray = rootJsonObj.get("enemytips").getAsJsonArray();
        JsonArray tagsArray = rootJsonObj.get("tags").getAsJsonArray();

        String enemyTips = toString(enemyTipsArray);
        String allTips = toString(allTipsArray);
        String tags = toString(tagsArray);

        Champion championRealm = new Champion();
        championRealm.setId(id);
        championRealm.setKey(key);
        championRealm.setName(name);
        championRealm.setTitle(title);
        championRealm.setLore(lore);
        championRealm.setBlurb(blurb);
        championRealm.setPartype(partype);
        championRealm.setInfo(infoRealm);
        championRealm.setStats(statsRealm);
        championRealm.setSpells(spells);
        championRealm.setSkins(skins);
        championRealm.setPassive(passiveRealm);
        championRealm.setAllytipsc(allTips);
        championRealm.setEnemytipsc(enemyTips);
        championRealm.setTagsc(tags);
        championRealm.setImage(image.getFull());
        return championRealm;
    }

    private String toString(JsonArray array) {
        if (array == null || array.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            JsonElement element = array.get(i);
            if (i == (array.size() - 1))
                builder.append(element);
            else
                builder.append(element).append(SEPARATOR);
        }
        return builder.toString();
    }
}
