/*
 * Copyright (c) 2015 Vernon Lee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.nodlee.riotgames;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class TagProxy {
    private static String[] sTagKeys;
    private static String[] sTags;

    private Context mCtx;

    public TagProxy(Context context) {
        if (context == null)
            throw new RuntimeException("context must not be null");

        mCtx = context;

        initTags();
    }

    private void initTags() {
        Resources res = mCtx.getResources();

        sTagKeys = res.getStringArray(R.array.championTagKeys);
        sTags = res.getStringArray(R.array.championTags);

        if (sTagKeys.length == 0 || sTags.length == 0) {
            throw new RuntimeException("legendTagKeys_entries or legendTags_entries must not be empty");
        }

        if (sTagKeys.length != sTags.length) {
            throw new RuntimeException("legendTagKeys_entries and legendTags_entries must have same length");
        }
    }

    public String getTagKey(String tag) {
        int index = getTagIndex(tag);
        if (index != -1) {
            return sTagKeys[index];
        } else {
            return null;
        }
    }

    private int getTagIndex(String destTag) {
        int index = -1;

        for (int i = 0; i < sTags.length; i++) {
            if (destTag.equals(sTagKeys[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public String[] getTags() {
        return sTags;
    }
}
