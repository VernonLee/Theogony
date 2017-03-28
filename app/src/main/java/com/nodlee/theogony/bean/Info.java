package com.nodlee.theogony.bean;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * 英雄能力评级，如攻击力，上手难度，魔法和防御
 */
public class Info extends RealmObject {
    private int attack;
    private int defense;
    private int magic;
    private int difficulty;

    public Info() {
    }

    public Info(int attack, int defense, int magic, int difficulty) {
        this.attack = attack;
        this.defense = defense;
        this.magic = magic;
        this.difficulty = difficulty;
    }

    public String getAttack() {
        return String.valueOf(attack);
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return String.valueOf(defense);
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getMagic() {
        return String.valueOf(magic);
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public String getDifficulty() {
        return String.valueOf(difficulty);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}