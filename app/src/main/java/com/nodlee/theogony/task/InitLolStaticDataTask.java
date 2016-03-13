package com.nodlee.theogony.task;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.nodlee.riotgames.Locale;
import com.nodlee.riotgames.RiotGamesImageAPI;
import com.nodlee.riotgames.RiotGamesStaticDataAPI;
import com.nodlee.theogony.R;
import com.nodlee.theogony.bean.Champion;
import com.nodlee.theogony.bean.Skin;
import com.nodlee.theogony.db.ChampionManager;
import com.nodlee.theogony.db.SkinManager;
import com.nodlee.theogony.utils.Constants;
import com.nodlee.theogony.utils.HttpFetchr;
import com.nodlee.theogony.utils.UserUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Vernon Lee on 15-11-28.
 */
public class InitLolStaticDataTask extends AsyncTask<Void, Integer, Boolean> {
    private static final String TAG = "FetchAndSaveTask";
    private static final boolean DEBUG = false;

    protected Context mCtx;
    protected String mLocale;
    private String mVersion;

    public InitLolStaticDataTask(Context context, String locale) {
        mLocale = locale;
        mCtx = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        RiotGamesStaticDataAPI riotApi = new RiotGamesStaticDataAPI(Constants.RIOT_APP_KEY);

        String championsUrl = riotApi.getRequestChampionsUrl(mLocale);
        if (DEBUG) Log.i(TAG, "staticDataUrl:" + championsUrl);

        if (isCancelled()) return false;

        publishProgress(R.string.download_data_progress_message);
        String response = requestStaticData(championsUrl);
        if (response == null) {
            return false;
        }

        publishProgress(R.string.parse_data_progress_message);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("data")) {
                ArrayList<Champion> champions = new ArrayList<>();
                ArrayList<Skin> skins = new ArrayList<>();

                mVersion = jsonObject.getString("version");
                parseStaticData(jsonObject.getString("data"), champions, skins);

                if (champions.size() > 0 && skins.size() > 0) {
                    publishProgress(R.string.save_data_progress_message);
                    ChampionManager.getInstance(mCtx).insertChampions(champions);
                    SkinManager.getInstance(mCtx).insertSkins(skins);

                    saveSettings();
                }

                return true;
            } else if (jsonObject.has("status")) {
                JSONObject exceptionObj = jsonObject.getJSONObject("status");
                JSONObject msgObj = exceptionObj.getJSONObject("message");
                String message = msgObj.getString("message");
                int code = msgObj.getInt("status_code");

                if (DEBUG) Log.e(TAG, String.format("message:%s_code:%d", message, code));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String requestStaticData(String url) {
        try {
            return new HttpFetchr().getUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void parseStaticData(String result, ArrayList<Champion> champions, ArrayList<Skin> skins) {
        if (TextUtils.isEmpty(result)) return;

        RiotGamesImageAPI riotImageApi = new RiotGamesImageAPI();

        try {
            JSONObject championsObj = new JSONObject(result);
            Iterator<String> iterator = championsObj.keys();
            while (iterator.hasNext()) {
                JSONObject champObj = championsObj.getJSONObject(String.valueOf(iterator.next()));
                int cid = champObj.getInt("id");
                String key = champObj.getString("key");
                String name = champObj.getString("name");
                String title = champObj.getString("title");
                String tags = buildTags(champObj.getJSONArray("tags"));
                String lore = champObj.getString("lore");
                String thumbnailName = String.format("%s.png", key);
                String thumbnailUrl = riotImageApi.buildThumbnailUrl(mVersion, thumbnailName);

                JSONArray skinsArr = champObj.getJSONArray("skins");
                if (skinsArr != null && skinsArr.length() > 0) {
                    for (int i = 0; i < skinsArr.length(); i++) {
                        JSONObject skinObj = skinsArr.getJSONObject(i);
                        int sid = skinObj.getInt("id");
                        String sname = skinObj.getString("name");
                        int num = skinObj.getInt("num");
                        String skinName = String.format("%s_%d.jpg", key, num);
                        String coverUrl = riotImageApi.buildSplashUrl(skinName);

                        // 替换name是 ”default“ 的为champion name + title
                        if (sname.equals("default")) {
                            sname = String.format("%1$s %2$s", name, title);
                        }

                        skins.add(new Skin(sid, cid, num, sname, coverUrl));
                    }
                }

                champions.add(new Champion(cid, key, name, title, tags, lore, thumbnailUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String buildTags(JSONArray tagsArr) throws JSONException {
        if (tagsArr == null || tagsArr.length() == 0)
            return null;

        StringBuilder tagsBuilder = new StringBuilder();
        for (int i = 0; i < tagsArr.length(); i++) {
            tagsBuilder.append(String.valueOf(tagsArr.get(i))).append(",");
        }

        return tagsBuilder.toString();
    }

    private void saveSettings() {
        UserUtils.setFirstBlood(mCtx, false);
        UserUtils.setLolStaticDataLocal(mCtx, mLocale);
        UserUtils.setLolStaticDataVersion(mCtx, mVersion);
    }
}
