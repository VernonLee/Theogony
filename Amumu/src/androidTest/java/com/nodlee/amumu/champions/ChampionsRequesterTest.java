package com.nodlee.amumu.champions;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    }
}