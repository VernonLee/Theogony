package com.nodlee.theogony.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 作者：nodlee
 * 时间：2017/3/19
 * 说明：收藏夹
 */

public class Favorites extends RealmObject {
    @PrimaryKey
    private int id;
    private int championId;
    private Champion champion; // Realm不支持多表查询, 将champion放到Favorites中减少FavoriteManger查询次数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }
}
