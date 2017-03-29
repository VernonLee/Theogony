package com.nodlee.theogony.thirdparty.gson;

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
import com.nodlee.theogony.utils.RiotGameUtils;

import java.lang.reflect.Type;
import java.util.List;

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
        Image image = context.deserialize(rootJsonObj.get("image"), Image.class);
        Passive passiveRealm = context.deserialize(rootJsonObj.get("passive"), Passive.class);
        JsonArray allTipsArray = rootJsonObj.get("allytips").getAsJsonArray();
        JsonArray enemyTipsArray = rootJsonObj.get("enemytips").getAsJsonArray();
        JsonArray tagsArray = rootJsonObj.get("tags").getAsJsonArray();

        String enemyTips = toString(enemyTipsArray);
        String allTips = toString(allTipsArray);
        String tags = toString(tagsArray);
        String avatarUrl = RiotGameUtils.createAvatarUrl(image.getFull());

        RealmList<Spell> spells = context.deserialize(rootJsonObj.get("spells"),
                new TypeToken<RealmList<Spell>>() {}.getType());
        RealmList<Skin> skins = context.deserialize(rootJsonObj.get("skins"),
                new TypeToken<RealmList<Skin>>() {}.getType());
        formatSkins(key, title, skins);

        Champion champion = new Champion();
        champion.setId(id);
        champion.setKey(key);
        champion.setName(name);
        champion.setTitle(title);
        champion.setLore(lore);
        champion.setBlurb(blurb);
        champion.setPartype(partype);
        champion.setInfo(infoRealm);
        champion.setStats(statsRealm);
        champion.setSpells(spells);
        champion.setSkins(skins);
        champion.setPassive(passiveRealm);
        champion.setAllytipsc(allTips);
        champion.setEnemytipsc(enemyTips);
        champion.setTagsc(tags);
        champion.setImage(avatarUrl);
        return champion;
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

    public void formatSkins(String key, String title, List<Skin> skins) {
        if (skins == null)
            return;

        for (Skin skin : skins) {
            String imageUrl = RiotGameUtils.createSkinImageUrl(key, skin.getNum());
            skin.setImage(imageUrl);

            if (skin.getName().equals("default")) {
                skin.setName(title);
            }
        }
    }
}
