package com.nodlee.theogony.bean;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * 作者：nodlee
 * 时间：2017/3/15
 * 说明：额外参数
 */

public class Var extends RealmObject {
    private String key;
    private String link;
    @Ignore
    private float[] coeff;

    public Var() {
    }

    public Var(String key, String link, float[] coeff) {
        this.key = key;
        this.link = link;
        this.coeff = coeff;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public float[] getCoeff() {
        return coeff;
    }

    public void setCoeff(float[] coeff) {
        this.coeff = coeff;
    }
}
