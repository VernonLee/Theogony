package com.nodlee.amumu.bean;

import com.nodlee.amumu.champions.Skin;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class Champion implements Serializable {
    private int id;
    private String key;
    private String name;
    private String title;
    private String dummyTags;
    private String lore;
    private String avatar;
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
