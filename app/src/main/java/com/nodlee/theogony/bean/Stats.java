package com.nodlee.theogony.bean;

import io.realm.RealmObject;

/**
 * 作者：nodlee
 * 时间：2017/3/15
 * 说明：基础属性
 */

public class Stats extends RealmObject {
    private float armor;
    private float armorperlevel;
    private float attackdamage;
    private float attackdamageperlevel;
    private float attackrange;
    private float attackspeedoffset;
    private float attackspeedperlevel;
    private float crit;
    private float critperlevel;
    private float hp;
    private float hpperlevel;
    private float hpregen;
    private float hpregenperlevel;
    private float movespeed;
    private float mp;
    private float mpperlevel;
    private float mpregen;
    private float mpregenperlevel;
    private float spellblock;
    private float spellblockperlevel;

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getArmorperlevel() {
        return armorperlevel;
    }

    public void setArmorperlevel(float armorperlevel) {
        this.armorperlevel = armorperlevel;
    }

    public float getAttackdamage() {
        return attackdamage;
    }

    public void setAttackdamage(float attackdamage) {
        this.attackdamage = attackdamage;
    }

    public float getAttackdamageperlevel() {
        return attackdamageperlevel;
    }

    public void setAttackdamageperlevel(float attackdamageperlevel) {
        this.attackdamageperlevel = attackdamageperlevel;
    }

    public float getAttackrange() {
        return attackrange;
    }

    public void setAttackrange(float attackrange) {
        this.attackrange = attackrange;
    }

    public float getAttackspeedoffset() {
        return attackspeedoffset;
    }

    public void setAttackspeedoffset(float attackspeedoffset) {
        this.attackspeedoffset = attackspeedoffset;
    }

    public float getAttackspeedperlevel() {
        return attackspeedperlevel;
    }

    public void setAttackspeedperlevel(float attackspeedperlevel) {
        this.attackspeedperlevel = attackspeedperlevel;
    }

    public float getCrit() {
        return crit;
    }

    public void setCrit(float crit) {
        this.crit = crit;
    }

    public float getCritperlevel() {
        return critperlevel;
    }

    public void setCritperlevel(float critperlevel) {
        this.critperlevel = critperlevel;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getHpperlevel() {
        return hpperlevel;
    }

    public void setHpperlevel(float hpperlevel) {
        this.hpperlevel = hpperlevel;
    }

    public float getHpregen() {
        return hpregen;
    }

    public void setHpregen(float hpregen) {
        this.hpregen = hpregen;
    }

    public float getHpregenperlevel() {
        return hpregenperlevel;
    }

    public void setHpregenperlevel(float hpregenperlevel) {
        this.hpregenperlevel = hpregenperlevel;
    }

    public float getMovespeed() {
        return movespeed;
    }

    public void setMovespeed(float movespeed) {
        this.movespeed = movespeed;
    }

    public float getMp() {
        return mp;
    }

    public void setMp(float mp) {
        this.mp = mp;
    }

    public float getMpperlevel() {
        return mpperlevel;
    }

    public void setMpperlevel(float mpperlevel) {
        this.mpperlevel = mpperlevel;
    }

    public float getMpregen() {
        return mpregen;
    }

    public void setMpregen(float mpregen) {
        this.mpregen = mpregen;
    }

    public float getMpregenperlevel() {
        return mpregenperlevel;
    }

    public void setMpregenperlevel(float mpregenperlevel) {
        this.mpregenperlevel = mpregenperlevel;
    }

    public float getSpellblock() {
        return spellblock;
    }

    public void setSpellblock(float spellblock) {
        this.spellblock = spellblock;
    }

    public float getSpellblockperlevel() {
        return spellblockperlevel;
    }

    public void setSpellblockperlevel(float spellblockperlevel) {
        this.spellblockperlevel = spellblockperlevel;
    }
}
