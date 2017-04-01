package com.nodlee.theogony.bean;

import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * 作者：nodlee
 * 时间：2017/3/15
 * 说明：技能实体
 * 备注：
 *     亚索没有costType
 *     没有costtype也就没有resource
 *     德莱文e技能没有伤害计算，仅刷新技能CD
 *     没有tooltip也就没有sanitizedTooltip
 */

public class Spell extends RealmObject {
    private String name;
    private String description;
    private String sanitizedDescription;
    private String tooltip;
    private String sanitizedTooltip;
    // private LevelTipRealm leveltip;
    private String image;
    private String resource;
    private int maxrank;
    @Ignore
    private int[] cost;
    private String costType;
    private String costBurn;
    @Ignore
    private float[] cooldown;
    private String cooldownBurn;
    @Ignore
    private List<float[]> effect;
    // private RealmList<RealmString> effectBurn;
    private RealmList<Var> vars;
    @Ignore
    private int[] range;
    private String rangeBurn;
    private String key;

    public Spell() {
    }

    public Spell(String name, String description, String sanitizedDescription,
                 String tooltip, String sanitizedTooltip, String image, String resource,
                 int maxrank, String costType, String costBurn, String cooldownBurn,
                 RealmList<Var> vars, String rangeBurn, String key) {
        this.name = name;
        this.description = description;
        this.sanitizedDescription = sanitizedDescription;
        this.tooltip = tooltip;
        this.sanitizedTooltip = sanitizedTooltip;
        this.image = image;
        this.resource = resource;
        this.maxrank = maxrank;
        this.costType = costType;
        this.costBurn = costBurn;
        this.cooldownBurn = cooldownBurn;
        this.vars = vars;
        this.rangeBurn = rangeBurn;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSanitizedDescription() {
        return sanitizedDescription;
    }

    public void setSanitizedDescription(String sanitizedDescription) {
        this.sanitizedDescription = sanitizedDescription;
    }

    public Spanned getTooltip() {
        return Html.fromHtml(tooltip);
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getSanitizedTooltip() {
        return sanitizedTooltip;
    }

    public void setSanitizedTooltip(String sanitizedTooltip) {
        this.sanitizedTooltip = sanitizedTooltip;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getMaxrank() {
        return maxrank;
    }

    public void setMaxrank(int maxrank) {
        this.maxrank = maxrank;
    }

    public int[] getCost() {
        return cost;
    }

    public void setCost(int[] cost) {
        this.cost = cost;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getCostBurn() {
        return costBurn;
    }

    public void setCostBurn(String costBurn) {
        this.costBurn = costBurn;
    }

    public float[] getCooldown() {
        return cooldown;
    }

    public void setCooldown(float[] cooldown) {
        this.cooldown = cooldown;
    }

    public String getCooldownBurn() {
        return cooldownBurn;
    }

    public void setCooldownBurn(String cooldownBurn) {
        this.cooldownBurn = cooldownBurn;
    }

    public List<float[]> getEffect() {
        return effect;
    }

    public void setEffect(List<float[]> effect) {
        this.effect = effect;
    }

    public void setVars(RealmList<Var> vars) {
        this.vars = vars;
    }

    public List<Var> getVars() {
        return vars;
    }


    public int[] getRange() {
        return range;
    }

    public void setRange(int[] range) {
        this.range = range;
    }

    public String getRangeBurn() {
        return rangeBurn;
    }

    public void setRangeBurn(String rangeBurn) {
        this.rangeBurn = rangeBurn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCostInfo() {
        return costType != null && costType.equals("无消耗") ? costType : costBurn + costType;
    }

    public String getCoolDownInfo() {
        return cooldownBurn + "秒";
    }
}
