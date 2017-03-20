package com.nodlee.theogony.core;

import android.support.test.runner.AndroidJUnit4;

import com.nodlee.theogony.bean.ChampionData;
import com.nodlee.theogony.utils.RealmProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;

import static org.junit.Assert.*;

/**
 * 作者：nodlee
 * 时间：2017/3/19
 * 说明：
 */
@RunWith(AndroidJUnit4.class)
public class ApiImplTest {

    private Realm mRealm;
    private ApiImpl mApi;

    @Before
    public void setUp() throws Exception {
        mRealm = RealmProvider.getInstance().getRealm();
        mApi = ApiImpl.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        if (mRealm != null) {
            mRealm.close();
        }
    }

    @Test
    public void loadDragonDataFromServer() throws Exception {
        String baiduUrl = "http://baidu.com/";
        String baiduString = mApi.loadDragonDataFromServer(baiduUrl);
        assertTrue(baiduString != null);
    }

    @Test
    public void parseJsonWithGson() throws Exception {
        String gradonDataUrl = "https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion?locale=zh_TW&champData=all&api_key=e0af5cdf-caab-44c2-a692-dea1d712f8ab";
        String gradonDataString = mApi.loadDragonDataFromServer(gradonDataUrl);
        assertTrue(gradonDataString != null && gradonDataString.length() > 100);

        ChampionData championData = mApi.parseJsonWithGson(gradonDataString);
        assertTrue(championData != null);
        assertTrue(championData.getVersion() != null);
        assertTrue(championData.getData().size() > 10);
    }

    @Test
    public void writeToRealmDataBase() throws Exception {
        String gradonDataUrl = "https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion?locale=zh_TW&champData=all&api_key=e0af5cdf-caab-44c2-a692-dea1d712f8ab";
        String gradonDataString = mApi.loadDragonDataFromServer(gradonDataUrl);
        ChampionData championData = mApi.parseJsonWithGson(gradonDataString);
        boolean success = mApi.writeToRealmDataBase(championData);
        assertTrue(success);
    }

    @Test
    public void updateRealmDataBase() throws Exception {

    }

}