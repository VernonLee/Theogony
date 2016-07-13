package com.nodlee.amumu.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class Champion implements Serializable {
    // 英雄ID, 如254
    private int id;
    // 英雄关键字，如thresh
    private String key;
    // 英雄昵称，如星界游神
    private String name;
    // 英雄名字，如巴德
    private String title;
    // 英雄标签，如Fighter, Support等等
    private String dummyTags;
    // 英雄背景故事
    private String lore;
    // 英雄头像缩略图URL
    private String avatar;
    // 英雄皮肤数据
    private ArrayList<Skin> skins;

    public Champion() {
    }

    public Champion(int id, String key, String name, String title,
                    String tags, String lore, String avatar) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.title = title;
        this.dummyTags = tags;
        this.lore = lore;
        this.avatar = avatar;
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

    public String getDummyTags() {
        return dummyTags;
    }

    public void setDummyTags(String dummyTags) {
        this.dummyTags = dummyTags;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<Skin> getSkins() {
        return skins;
    }

    public void setSkins(ArrayList<Skin> skins) {
        this.skins = skins;
    }

    @Override
    public String toString() {
        return String.format("id:%d,key:%s,name:%s,title:%s,dummyTags:%s,lore:%s,avatar:%s"
                , id, key, name, title, dummyTags, lore, avatar);
    }
}
