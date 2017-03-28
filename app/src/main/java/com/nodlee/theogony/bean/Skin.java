package com.nodlee.theogony.bean;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * 作者：nodlee
 * 时间：2017/3/15
 * 说明：皮肤实体
 */

public class Skin extends RealmObject implements Serializable {
    private int id;
    private String name;
    private int num;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
