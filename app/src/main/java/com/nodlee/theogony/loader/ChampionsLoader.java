package com.nodlee.theogony.loader;

import android.content.Context;
import android.database.Cursor;

import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.core.ChampionDataManager;
import com.nodlee.theogony.core.ChampionManager;

import java.util.List;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.tag;
import static okhttp3.Protocol.get;

/**
 * Created by Vernon Lee on 15-11-23.
 */
public class ChampionsLoader extends DataLoader<List<Champion>> {
    private String param;
    private Action mAction;

    public enum Action {
        TAG, QUERY
    }

    public ChampionsLoader(Context context, Action action, String param) {
        super(context);
        this.mAction = action;
        this.param = param;
    }

    @Override
    public List<Champion> loadInBackground() {
        ChampionManager manager = ChampionManager.getInstance();
        switch (mAction) {
            case TAG:
                return manager.get(param);
            case QUERY:
                return manager.query(param);
            default:
                return null;
        }
    }
}
