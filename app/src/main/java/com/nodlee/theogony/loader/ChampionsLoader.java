package com.nodlee.theogony.loader;

import android.content.Context;
import android.database.Cursor;

import com.nodlee.theogony.db.ChampionManager;
import com.nodlee.theogony.db.DatabaseOpenHelper;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class ChampionsLoader extends SQLiteCursorLoader {
    private Context mCtx;
    private String mSelection;
    private String[] mSelectionArgs;

    public enum Query {
        TAGS, KEYWORDS
    }

    public ChampionsLoader(Context context, Query query, String value) {
        super(context);
        mCtx = context;
        mSelection = buildSelection(query);
        mSelectionArgs = buildSelectionArgs(query, value);
    }

    @Override
    public Cursor loadCursor() {
        if (mCtx == null)
            return null;

        return ChampionManager.getInstance(mCtx).getAll(mSelection, mSelectionArgs);
    }

    private String buildSelection(Query query) {
        switch (query) {
            case TAGS:
                return DatabaseOpenHelper.CHAMPION_COLUMN_TAGS + " like ? ";
            case KEYWORDS:
                return DatabaseOpenHelper.CHAMPION_COLUMN_NAME + " like ? or "
                        + DatabaseOpenHelper.CHAMPION_COLUMN_TITLE + " like ? or "
                        + DatabaseOpenHelper.CHAMPION_COLUMN_KEY + " like ?";
            default:
                return null;
        }
    }

    private String[] buildSelectionArgs(Query query, String value) {
        switch (query) {
            case TAGS:
                return new String[]{"%" + value + "%"};
            case KEYWORDS:
                return new String[]{"%" + value + "%",
                        "%" + value + "%", "%" + value + "%"};
            default:
                return null;
        }
    }
}
