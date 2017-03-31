package com.nodlee.theogony.bean;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


/**
 * 作者：nodlee
 * 时间：2017/3/15
 * 说明：
 */

public class DragonData extends RealmObject {
    private String type;
    private String format;
    private String version;
    private String languageCode;
    private RealmList<Champion> data;
    private boolean isOutDate; // 数据过时，即有新版本数据可更新

    public DragonData() {
    }

    public DragonData(String type, String format, String version, RealmList<Champion> data) {
        this.type = type;
        this.format = format;
        this.version = version;
        this.data = data;
    }

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

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public boolean isOutDate() {
        return isOutDate;
    }

    public void setOutDate(boolean outDate) {
        isOutDate = outDate;
    }

    @Override
    public String toString() {
        return "type:" + type + "\n" +
                "format:" + format + "\n" +
                "version:" + version + "\n" +
                "language code:" + languageCode +
                "data size:" + (data == null ? 0 : data.size());
    }
}
