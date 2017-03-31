package com.nodlee.theogony.bean;

import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;

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

    public Passive() {
    }

    public Passive(String name, String description, String sanitizedDescription, String image) {
        this.name = name;
        this.description = description;
        this.sanitizedDescription = sanitizedDescription;
        this.image = image;
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

    public Spanned getSanitizedDescription() {
        return Html.fromHtml(sanitizedDescription);
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
