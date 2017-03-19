package com.nodlee.theogony.bean;

import io.realm.RealmObject;

/**
 * 作者：nodlee
 * 时间：2017/3/15
 * 说明：被动技能
 */

public class Passive extends RealmObject {
    private String name;
    private String description;
    private String sanitizedDescription;
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
