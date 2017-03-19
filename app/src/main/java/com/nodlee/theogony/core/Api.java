package com.nodlee.theogony.core;

import com.nodlee.theogony.bean.ChampionData;

/**
 * 作者：nodlee
 * 时间：2017/3/19
 * 说明：本地业务接口
 */

public interface Api {

    /**
     * 从服务器加载数据
     * @param url
     */
    String loadDragonDataFromServer(String url);

    /**
     * 解析JSON字符串
     * @param json
     * @return
     */
    ChampionData parseJsonWithGson(String json);

    /**
     * 写入数据库
     * @param championData
     */
    boolean writeToRealmDataBase(ChampionData championData);

    /**
     * 更新数据库
     * @param newChampionData
     */
    boolean updateRealmDataBase(ChampionData newChampionData);

}
