package com.nodlee.theogony.bean;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 作者：nodlee
 * 时间：2017/3/15
 * 说明：英雄实体
 */

public class Champion extends RealmObject {
    @PrimaryKey
    private int id;
    private String key;
    private String name;
    private String title;
    private String image;
    private String lore;
    private String blurb;
    private String enemytipsc;
    private String allytipsc;
    private String tagsc;
    private String partype;
    private Info info;
    private Stats stats;
    private RealmList<Spell> spells;
    private Passive passive;
    private RealmList<Skin> skins;

    public Passive getPassive() {
        return passive;
    }

    public void setPassive(Passive passive) {
        this.passive = passive;
    }

    public String getEnemytipsc() {
        return enemytipsc;
    }

    public void setEnemytipsc(String enemytipsc) {
        this.enemytipsc = enemytipsc;
    }

    public String getAllytipsc() {
        return allytipsc;
    }

    public void setAllytipsc(String allytipsc) {
        this.allytipsc = allytipsc;
    }

    public String getTagsc() {
        return tagsc;
    }

    public void setTagsc(String tagsc) {
        this.tagsc = tagsc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<Skin> getSkins() {
        return skins;
    }

    public void setSkins(RealmList<Skin> skins) {
        this.skins = skins;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getPartype() {
        return partype;
    }

    public void setPartype(String partype) {
        this.partype = partype;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public RealmList<Spell> getSpells() {
        return spells;
    }

    public void setSpells(RealmList<Spell> spells) {
        this.spells = spells;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
