package com.nodlee.theogony.bean;

import io.realm.RealmObject;

/**
 * 作者：nodlee
 * 时间：2017/3/19
 * 说明：收藏夹
 */

public class Favorites extends RealmObject {
    private int id;
    private Champion champion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }
}
