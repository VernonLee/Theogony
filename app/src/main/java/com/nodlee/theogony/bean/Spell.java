package com.nodlee.theogony.bean;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * 作者：nodlee
 * 时间：2017/3/15
 * 说明：技能实体
 */

public class Spell extends RealmObject {
    private String name;
    private String description;
    private String sanitizedDescription;
    @Ignore
    private String tooltip; // 含有html标签，解析出错
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

    public String getTooltip() {
        return tooltip;
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
}
