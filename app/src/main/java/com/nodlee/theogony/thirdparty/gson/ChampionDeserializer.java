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
import static com.nodlee.theogony.thirdparty.gson.SafetyGSONParser.*;

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

        JsonObject rootJsonObj   = json.getAsJsonObject();
        int id                   = getInt(rootJsonObj, "id");
        String key               = getString(rootJsonObj, "key");
        String name              = getString(rootJsonObj, "name");
        String title             = getString(rootJsonObj, "title");
        String lore              = getString(rootJsonObj, "lore");
        String blurb             = getString(rootJsonObj, "blurb");
        String parType           = getString(rootJsonObj, "partype");
        Info infoRealm           = getType(context, rootJsonObj, "info", Info.class);
        Stats statsRealm         = getType(context, rootJsonObj, "stats", Stats.class);
        Image image              = getType(context, rootJsonObj, "image", Image.class);
        Passive passiveRealm     = getType(context, rootJsonObj, "passive", Passive.class);
        JsonArray allTipsArray   = getArray(rootJsonObj, "allytips");
        JsonArray enemyTipsArray = getArray(rootJsonObj, "enemytips");
        JsonArray tagsArray      = getArray(rootJsonObj, "tags");
        RealmList<Spell> spells  = getType(context, rootJsonObj, "spells", new TypeToken<RealmList<Spell>>() {}.getType());
        RealmList<Skin> skins   = getType(context, rootJsonObj, "skins", new TypeToken<RealmList<Skin>>() {}.getType());

        String enemyTips = null;
        if (enemyTipsArray != null) {
            enemyTips = toString(enemyTipsArray);
        }
        String allTips = null;
        if (allTipsArray != null) {
            allTips = toString(allTipsArray);
        }
        String tags = null;
        if (tagsArray != null) {
            tags = toString(tagsArray);
        }
        String avatarUrl = null;
        if (image != null) {
            avatarUrl = RiotGameUtils.createAvatarUrl(image.getFull());
        }

        formatSkins(key, title, skins);

        return new Champion(id, key, name, title,
                avatarUrl, lore, blurb, enemyTips,
                allTips, tags, parType, infoRealm,
                statsRealm, passiveRealm, spells, skins);
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
            if (skin.getName().equals("default")) {
                skin.setName(title);
            }
            String imageUrl = RiotGameUtils.createSkinImageUrl(key, skin.getNum());
            skin.setImage(imageUrl);
        }
    }
}
