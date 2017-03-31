package com.nodlee.theogony.thirdparty.gson;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.nodlee.theogony.bean.Image;
import com.nodlee.theogony.bean.Spell;
import com.nodlee.theogony.bean.Var;
import com.nodlee.theogony.utils.LogHelper;
import com.nodlee.theogony.utils.RiotGameUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.nodlee.theogony.thirdparty.gson.SafetyGSONParser.*;

import io.realm.RealmList;

import static com.nodlee.theogony.thirdparty.gson.SafetyGSONParser.getString;

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

        String name                 = getString(rootJsonObj, "name");
        String description          = getString(rootJsonObj, "description");
        String sanitizedDescription = getString(rootJsonObj, "sanitizedDescription");
        int maxRank                 = getInt(rootJsonObj, "maxrank");
        String costBurn             = getString(rootJsonObj, "costBurn");
        String coolDownBurn         = getString(rootJsonObj, "cooldownBurn");
        RealmList<Var> vars         = getType(context, rootJsonObj, "vars", new TypeToken<RealmList<Var>>() {}.getType());
        List<String> effectBurns    = getType(context, rootJsonObj, "effectBurn", new TypeToken<List<String>>() {}.getType());
        String rangeBurn            = getString(rootJsonObj, "rangeBurn");
        String key                  = getString(rootJsonObj, "key");
        Image image                 = getType(context, rootJsonObj, "image", Image.class);
        String costType             = getString(rootJsonObj, "costType");
        String resource             = getString(rootJsonObj, "resource");
        String tooltip              = getString(rootJsonObj, "tooltip");
        String sanitizedTooltip     = getString(rootJsonObj, "sanitizedTooltip");

        String spellImage = null;
        if (image != null) {
            spellImage = RiotGameUtils.createSpellImageUrl(image.getFull());
        }

        String formattedToolTip = null;
        if (tooltip != null) {
            formattedToolTip = formatToolTip(tooltip, effectBurns.toArray(new String[]{}), vars);
        }

        return new Spell(name, description, sanitizedDescription,
                formattedToolTip, sanitizedTooltip, spellImage,
                resource, maxRank, costType, costBurn,
                coolDownBurn, vars, rangeBurn, key);
    }

    public String formatToolTip(String tooltip, String[] effectBurns, List<Var> vars) {
        tooltip = tooltip.replace("{{", "{").replace("}}", "}").replace(" ", "");

        String result = null;
        try {
            List<ToolTipKey> keys = getKeyWordFromToolTip(tooltip);
            if (keys == null || keys.size() == 0) {
                return tooltip;
            }
            String formattedToolTip = new String(tooltip);
            for (ToolTipKey key : keys) {
                if (key.index < 0)
                    continue;
                if (key.isEffectBurn()) {
                    formattedToolTip = formatEffectBurn(formattedToolTip, key, effectBurns);
                } else {
                    formattedToolTip = formatVars(formattedToolTip, key, vars);
                }
            }
            result = formattedToolTip.replace("{", "").replace("}", "");
        } catch (Exception e) {
            LogHelper.LOGD("解析出错，" + tooltip);
            result = tooltip;
            e.printStackTrace();
        }
        return result;
    }

    private String formatEffectBurn(String toolTip, ToolTipKey key, String[] effectBurns) {
        if (effectBurns == null || effectBurns.length == 0) {
            return toolTip;
        } else {
            return toolTip.replace(key.value, effectBurns[key.index]);
        }
    }

    private String formatVars(String toolTip, ToolTipKey key, List<Var> vars) {
        if (vars == null || vars.size() == 0) {
            return toolTip;
        } else {
            return toolTip.replace(key.value, "" + getCoeffByToolTipKey(key, vars));
        }
    }

    private float getCoeffByToolTipKey(ToolTipKey key, List<Var> vars) {
        float coeff = 0;
        for (Var var : vars) {
            if (var.getKey().equals(key.value)) {
                coeff = var.getCoeff()[0];
                break;
            }
        }
        return coeff;
    }

    /**
     * TODO 复杂key提取，比如{maxammo}，{effect1amount*0.25}，{charbonusphysical*0.25}等等
     * @param tooltip
     * @return
     */
    private List<ToolTipKey> getKeyWordFromToolTip(String tooltip) {
        if (TextUtils.isEmpty(tooltip)) return null;

        List<Integer> startIndex = new ArrayList<>();
        List<Integer> stopIndex = new ArrayList<>();

        char[] charArr = tooltip.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (charArr[i] == '{') {
                startIndex.add(i + 1);
                stopIndex.add(i + 3);
            }
        }

        if (startIndex.size() != stopIndex.size()) {
            LogHelper.LOGD("startIndex和stopIndex数组长度不一致");
            return null;
        }

        List<ToolTipKey> keys = new ArrayList<>();
        for (int i = 0; i < stopIndex.size(); i++) {
            String toolTipKey = tooltip.substring(startIndex.get(i), stopIndex.get(i));
            LogHelper.LOGD(toolTipKey);
            ToolTipKey kw = new ToolTipKey(toolTipKey);
            LogHelper.LOGD(kw.toString());
            keys.add(kw);
        }
        return keys;
    }


    private class ToolTipKey {
        public String value;
        public int index = -1;

        public ToolTipKey(String keyword) {
            if (keyword != null && keyword.length() > 1) {
                index = Integer.valueOf(keyword.substring(1));
            }
            value = keyword;
        }

        public boolean isEffectBurn() {
            return value.startsWith("e");
        }

        @Override
        public String toString() {
            return "ToolTipKey  value:" + value + " index:" + index;
        }
    }
}
