package com.nodlee.theogony.task;

import android.os.Bundle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 作者：nodlee
 * 时间：2017/3/29
 * 说明：
 */
public class DragonDataWatcherTest {

    @Test
    public void doInBackground() throws Exception {
        String appKey = "e0af5cdf-caab-44c2-a692-dea1d712f8ab";
        Bundle args = new Bundle();
        args.putString("app_key", appKey);
        args.putString("version", "");
        new DragonDataWatcher().execute(args);
    }

}