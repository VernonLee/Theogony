package com.nodlee.theogony.bean;

import java.io.Serializable;

/**
 * Created by Vernon Lee on 15-11-22.
 */
public class Skin implements Serializable {
    private int sid;
    private int cid;
    private int num;
    private String name;
    private String cover;

    public Skin() {
    }

    public Skin(int sid, int cid, int num, String name, String cover) {
        this.sid = sid;
        this.cid = cid;
        this.num = num;
        this.name = name;
        this.cover = cover;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int rid) {
        this.sid = rid;
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
        return String.format("sid:%d,cid:%d,num:%d,name:%s,cover:%s"
        , sid, cid, num, name, cover);
    }
}
