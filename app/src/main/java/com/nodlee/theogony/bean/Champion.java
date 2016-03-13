package com.nodlee.theogony.bean;

import java.io.Serializable;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class Champion implements Serializable {
    private int cid;
    private String key;
    private String name;
    private String title;
    private String tags;
    private String lore;
    private String avatar;

    public Champion() {
    }

    public Champion(int cid, String key, String name, String title,
                    String tags, String lore, String avatar) {
        this.cid = cid;
        this.key = key;
        this.name = name;
        this.title = title;
        this.tags = tags;
        this.lore = lore;
        this.avatar = avatar;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    @Override
    public String toString() {
        return String.format("cid:%d,key:%s,name:%s,title:%s,tags:%s,lore:%s,avatar:%s"
                , cid, key, name, title, tags, lore, avatar);
    }
}
