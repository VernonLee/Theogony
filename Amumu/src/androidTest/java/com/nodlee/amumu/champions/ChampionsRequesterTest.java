package com.nodlee.amumu.champions;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.nodlee.amumu.bean.Champion;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Created by nodlee on 16/6/16.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ChampionsRequesterTest extends TestCase {

    private ChampionsRequester requester;

    @Before
    public void createRequester() {
        requester = new ChampionsRequester();
    }

    @Test
    public void testSyncRequest() throws Exception {
        requester.asyncRequest("zh_CN", new RequestCallback() {
            @Override
            public void onSuccess(ArrayList<Champion> champions) {
                Log.d("xxx", champions.toString());
                assertNotNull(champions);
            }

            @Override
            public void onFailed(int errCode) {
                Log.d("xxx", errCode + "");
            }
        });
    }
}