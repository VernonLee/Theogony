package com.nodlee.theogony.bean;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


/**
 * 作者：nodlee
 * 时间：2017/3/15
 * 说明：
 */

public class ChampionData extends RealmObject {
    private String type;
    private String format;
    private String version;
    private RealmList<Champion> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Champion> getData() {
        return data;
    }

    public void setData(RealmList<Champion> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "type:" + type + "\n" +
                "format:" + format + "\n" +
                "version:" + version + "\n" +
                "data size:" + (data == null ? 0 : data.size());
    }
}
