package com.nodlee.theogony.loader;

import android.content.Context;

import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.core.ChampionManager;

import java.util.List;

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
                return manager.queryByTag(param);
            case QUERY:
                return manager.queryByKeyWord(param);
            default:
                return null;
        }
    }
}
