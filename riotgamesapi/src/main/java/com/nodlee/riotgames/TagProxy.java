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
