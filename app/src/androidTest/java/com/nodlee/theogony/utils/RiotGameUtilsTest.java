package com.nodlee.theogony.utils;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;


import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

/**
 * 作者：nodlee
 * 时间：2017/3/20
 * 说明：
 */
@RunWith(AndroidJUnit4.class)
public class RiotGameUtilsTest {

    @Test
    public void createDragonDataUrl() throws Exception {
        final String testAppKey = "e0af5cdf-caab-44c2-a692-dea1d712f8ab";
        final String testLanguageCode = "zh_CN";
        String dragonDataUrl = RiotGameUtils.createDragonDataUrl(testLanguageCode, testAppKey);
        Log.d("xxx", "dragonDataUrl:" + dragonDataUrl);
        assertTrue(dragonDataUrl != null);
    }

    @Test
    public void createPassiveImageUrl() throws Exception {
        final String testPassiveImageName = "Armsmaster_MasterOfArms.png";
        String passiveImageUrl = RiotGameUtils.createPassiveImageUrl(testPassiveImageName);
        Log.d("xxx", "passiveImageUrl:" + passiveImageUrl);
        assertTrue(passiveImageUrl != null);
    }

    @Test
    public void createSkinImageUrl() throws Exception {
        final String testChampionKey = "Jax";
        final int testSkinIndex = 0;
        String skinImageUrl = RiotGameUtils.createSkinImageUrl(testChampionKey, testSkinIndex);
        Log.d("xxx", "skinImageUrl:" + skinImageUrl);
        assertTrue(skinImageUrl != null);
    }

    @Test
    public void createSpellImageUrl() throws Exception {
        final String spellImageName = "JaxLeapStrike.png";
        String spellImageUrl = RiotGameUtils.createSpellImageUrl(spellImageName);
        Log.d("xxx", "spellImageUrl:" + spellImageUrl);
        assertTrue(spellImageUrl != null);
    }

    // not passed
    @Test
    public void createSpellVideoUrl() throws Exception {
        final int testChampionId = 24;
        final int testSpellIndex = 2;
        String spellVideoUrl = RiotGameUtils.createSpellVideoUrl(testChampionId, testSpellIndex);
        Log.d("xxx", "spellVideoUrl:" + spellVideoUrl);
        assertTrue(spellVideoUrl != null);
    }

}