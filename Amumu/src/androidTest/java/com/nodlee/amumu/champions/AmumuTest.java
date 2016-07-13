package com.nodlee.amumu.champions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nodlee on 16/6/16.
 */
public class AmumuTest {

    @Test
    public void testInit() throws Exception {
        assertTrue(Amumu.sAppKey != null);
    }
}