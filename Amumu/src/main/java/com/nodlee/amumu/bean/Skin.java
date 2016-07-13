package com.nodlee.amumu.bean;

import java.io.Serializable;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class Skin implements Serializable {
    // 皮肤ID，如4000001
    private int id;
    // 英雄ID，如254
    private int cid;
    // 皮肤标号，如1，2，3等
    private int num;
    // 皮肤名称，如长者之森
    private String name;
    // 皮肤图片URL
    private String cover;

    public Skin() {
    }

    public Skin(int id, int cid, int num, String name, String cover) {
        this.id = id;
        this.cid = cid;
        this.num = num;
        this.name = name;
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int rid) {
        this.id = rid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return String.format("id:%d,cid:%d,num:%d,name:%s,cover:%s"
        , id, cid, num, name, cover);
    }
}
