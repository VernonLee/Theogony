package com.nodlee.theogony.utils;

import android.net.Uri;
import android.text.TextUtils;



/**
 * 作者：nodlee
 * 时间：2017/3/20
 * 说明：拳头数据工具类
 */

public class RiotGameUtils {

    /**
     * 创建源数据请求URL
     *
     * @param languageCode
     * @param appKey
     * @return
     */
    public static String createDragonDataUrl(String languageCode, String appKey) {
        if (TextUtils.isEmpty(languageCode) || TextUtils.isEmpty(appKey)) {
            throw new IllegalArgumentException("languageCode或APPKEY为空");
        }
        final String baseDragonDataUrl = "https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion";
        Uri uri = Uri.parse(baseDragonDataUrl)
                .buildUpon()
                .appendQueryParameter("locale", languageCode)
                .appendQueryParameter("champData", "all")
                .appendQueryParameter("api_key", appKey)
                .build();
        return uri.toString();
    }

    /**
     * 创建被动技能图片URL
     *
     * @return
     */
    public static String createPassiveImageUrl(String passiveImageName) {
        if (TextUtils.isEmpty(passiveImageName)) {
            throw new IllegalArgumentException("passiveImageName为空");
        }
        final String basePassiveImageUrl = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/passive";
        Uri uri = Uri.parse(basePassiveImageUrl)
                .buildUpon()
                .appendPath(passiveImageName)
                .build();
        return uri.toString();
    }

    /**
     * 创建皮肤图片URL
     *
     * @return
     */
    public static String createSkinImageUrl(String championKey, int skinIndex) {
        if (TextUtils.isEmpty(championKey)) {
            throw new IllegalArgumentException("championId出错，" + championKey);
        }
        if (skinIndex < 0) {
            throw new IllegalArgumentException("spellIndex出错，" + skinIndex);
        }
        final String baseSkinImageUrl = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash";
        final String skinImageName = String.format("%s_%d.jpg", championKey, skinIndex);
        Uri uri = Uri.parse(baseSkinImageUrl)
                .buildUpon()
                .appendPath(skinImageName)
                .build();
        return uri.toString();
    }

    /**
     * 创建技能图片URL
     * JaxLeapStrike.png
     *
     * @return
     */
    public static String createSpellImageUrl(String spellImageName) {
        if (TextUtils.isEmpty(spellImageName)) {
            throw new IllegalArgumentException("spellImageName");
        }
        final String baseSpellImageUrl = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/spell";
        Uri uri = Uri.parse(baseSpellImageUrl)
                .buildUpon()
                .appendPath(spellImageName)
                .build();
        return uri.toString();
    }

    /**
     * 创建技能视频URL
     *
     * @param championId
     * @param spellIndex 技能顺序从零开始
     * @return
     */
    public static String createSpellVideoUrl(int championId, int spellIndex) {
        if (championId <= 0) {
            throw new IllegalArgumentException("championId出错，" + championId);
        }
        if (spellIndex < 0 || spellIndex > 4) {
            throw new IllegalArgumentException("spellIndex出错，" + spellIndex);
        }
        final String baseSpellVideoUrl = "https://lolstatic-a.akamaihd.net/champion-abilities/videos/mp4";
        final String spellVideoName = String.format("%04d_%02d.mp4", championId, spellIndex); //0024_01.mp4
        Uri uri = Uri.parse(baseSpellVideoUrl)
                .buildUpon()
                .appendPath(spellVideoName)
                .build();
        return uri.toString();
    }
}
