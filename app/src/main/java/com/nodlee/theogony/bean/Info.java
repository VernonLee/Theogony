package com.nodlee.theogony.bean;

import io.realm.RealmObject;

/**
 * 英雄能力评级，如攻击力，上手难度，魔法和防御
 */
public class Info extends RealmObject {
    private int attack;
    private int defense;
    private int magic;
    private int difficulty;

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}