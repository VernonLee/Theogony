package com.nodlee.amumu.bean;

import java.io.Serializable;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class Skin implements Serializable {
    private int id;
    private int cid;
    private int num;
    private String name;
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
