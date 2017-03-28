package com.nodlee.theogony.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.nodlee.theogony.bean.Image;
import com.nodlee.theogony.bean.Spell;
import com.nodlee.theogony.bean.Var;
import com.nodlee.theogony.utils.RiotGameUtils;

import java.lang.reflect.Type;

import io.realm.RealmList;

/**
 * 作者：nodlee
 * 时间：2017/3/17
 * 说明：
 */

public class SpellDeserializer implements JsonDeserializer<Spell> {

    @Override
    public Spell deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;

        JsonObject rootJsonObj = json.getAsJsonObject();
        String name = rootJsonObj.get("name").getAsString();
        String description = rootJsonObj.get("description").getAsString();
        String sanitizedDescription = rootJsonObj.get("sanitizedDescription").getAsString();
        int maxrank = rootJsonObj.get("maxrank").getAsInt();
        String costBurn = rootJsonObj.get("costBurn").getAsString();
        String cooldownBurn = rootJsonObj.get("cooldownBurn").getAsString();
        RealmList<Var> vars = context.deserialize(rootJsonObj.get("vars"), new TypeToken<RealmList<Var>>(){}.getType());
        String rangeBurn = rootJsonObj.get("rangeBurn").getAsString();
        String key = rootJsonObj.get("key").getAsString();
        Image image = context.deserialize(rootJsonObj.get("image"), Image.class);
        String spellImage = RiotGameUtils.createSpellImageUrl(image.getFull());

        String costType = null; // 亚索没有costtype
        if (rootJsonObj.has("costType")) {
            costType = rootJsonObj.get("costType").getAsString();
        }
        String resource = null; // 没有costtype也就没有resource
        if (rootJsonObj.has("resource")) {
            resource = rootJsonObj.get("resource").getAsString();
        }
        String tooltip = null; // 德莱文e技能没有伤害计算，仅刷新技能CD
        if (rootJsonObj.has("tooltip")) {
            tooltip = rootJsonObj.get("tooltip").getAsString();
        }
        String sanitizedTooltip = null; // 没有tooltip也就没有sanitizedTooltip
        if (rootJsonObj.has("sanitizedTooltip")) {
            sanitizedTooltip = rootJsonObj.get("sanitizedTooltip").getAsString();
        }

        Spell spell = new Spell();
        spell.setName(name);
        spell.setDescription(description);
        spell.setSanitizedDescription(sanitizedDescription);
        spell.setTooltip(tooltip);
        spell.setSanitizedTooltip(sanitizedTooltip);
        spell.setImage(spellImage);
        spell.setResource(resource);
        spell.setMaxrank(maxrank);
        spell.setCostType(costType);
        spell.setCostBurn(costBurn);
        spell.setCooldownBurn(cooldownBurn);
        spell.setVars(vars);
        spell.setRangeBurn(rangeBurn);
        spell.setKey(key);
        return spell;
    }
}
