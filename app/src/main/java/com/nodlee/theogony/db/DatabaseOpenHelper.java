package com.nodlee.theogony.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.nodlee.amumu.bean.Champion;
import com.nodlee.amumu.bean.Skin;

import java.util.ArrayList;

/**
 * Created by Vernon Lee on 15-11-20.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "theogony.db";
    private static final int DB_VERSION_CODE = 1;

    /**
     * 英雄表 champion
     */
    private static final String TABLE_CHAMPION = "champion";
    private static final String CHAMPION_COLUMN_CID = "cid";
    public static final String CHAMPION_COLUMN_KEY = "key";
    public static final String CHAMPION_COLUMN_NAME = "name";
    public static final String CHAMPION_COLUMN_TITLE = "title";
    public static final String CHAMPION_COLUMN_TAGS = "tags";
    private static final String CHAMPION_COLUMN_LORE = "lore";
    private static final String CHAMPION_COLUMN_AVATAR = "avatar";
    private static final String CHAMPION_DEFAULT_ORDER = CHAMPION_COLUMN_CID + " desc";

    /**
     * 皮肤表 skin
     */
    private static final String TABLE_SKIN = "skin";
    private static final String SKIN_COLUMN_SID = "sid";
    private static final String SKIN_COLUMN_CID = "cid";
    private static final String SKIN_COLUMN_NUM = "num";
    private static final String SKIN_COLUMN_NAME = "name";
    private static final String SKIN_COLUMN_COVER = "cover";
    private static final String SKIN_DEFAULT_ORDER = SKIN_COLUMN_NUM + " asc";

    /**
     * 喜欢的英雄
     */
    private static final String TABLE_FAVORITES = "favorite";
    private static final String FAVORITES_COLUMN_CID = "cid";
    private static final String FAVORITES_COLUMN_ADD_TIME = "add_time";
    private static final String FAVORITES_DEFAULT_ORDER = FAVORITES_COLUMN_ADD_TIME + " desc";

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION_CODE);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建英雄表
        db.execSQL(
                "create table " + TABLE_CHAMPION + " ("
                        + "_id integer, "
                        + CHAMPION_COLUMN_CID + " integer primary key, "
                        + CHAMPION_COLUMN_KEY + " varchar(50), "
                        + CHAMPION_COLUMN_NAME + " varchar(50), "
                        + CHAMPION_COLUMN_TITLE + " varchar(50), "
                        + CHAMPION_COLUMN_TAGS + " varchar(50), "
                        + CHAMPION_COLUMN_LORE + " text, "
                        + CHAMPION_COLUMN_AVATAR + " varchar(200)"
                        + ");"
        );
        // 创建英雄皮肤表
        db.execSQL(
                "create table " + TABLE_SKIN + " ("
                        + "_id integer, "
                        + SKIN_COLUMN_SID + " integer primary key, "
                        + SKIN_COLUMN_CID + " integer references "
                        + TABLE_CHAMPION + "(" + CHAMPION_COLUMN_CID + "), "
                        + SKIN_COLUMN_NUM + " integer, "
                        + SKIN_COLUMN_NAME + " varchar(50), "
                        + SKIN_COLUMN_COVER + " varchar(200)"
                        + ");"
        );
        // 创建喜欢的英雄表
        db.execSQL(
                "create table " + TABLE_FAVORITES + " ("
                        + "_id integer, "
                        + FAVORITES_COLUMN_CID + " integer references "
                        + TABLE_CHAMPION + "(" + CHAMPION_COLUMN_CID + "), "
                        + FAVORITES_COLUMN_ADD_TIME + " integer"
                        + ");"
        );
    }

    public void insertChampions(ArrayList<Champion> champions) {
        SQLiteStatement insertStmt = null;
        SQLiteStatement deleteStmt = null;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            deleteStmt = db.compileStatement("delete from " + TABLE_CHAMPION);
            insertStmt = db.compileStatement("insert into " + TABLE_CHAMPION
                    + " values(?, ?, ?, ?, ?, ?, ?, ?)");
            // 清空原有英雄数据
            deleteStmt.execute();
            // 添加新的英雄数据
            for (Champion champion : champions) {
                insertStmt.bindNull(1);
                insertStmt.bindLong(2, champion.getId());
                insertStmt.bindString(3, champion.getKey());
                insertStmt.bindString(4, champion.getName());
                insertStmt.bindString(5, champion.getTitle());
                insertStmt.bindString(6, champion.getDummyTags());
                insertStmt.bindString(7, champion.getLore());
                insertStmt.bindString(8, champion.getAvatar());
                insertStmt.execute();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }

    public long insertChampion(Champion champion) {
        ContentValues values = new ContentValues();
        values.put(CHAMPION_COLUMN_CID, champion.getId());
        values.put(CHAMPION_COLUMN_KEY, champion.getKey());
        values.put(CHAMPION_COLUMN_NAME, champion.getName());
        values.put(CHAMPION_COLUMN_TITLE, champion.getTitle());
        values.put(CHAMPION_COLUMN_TAGS, champion.getDummyTags());
        values.put(CHAMPION_COLUMN_LORE, champion.getLore());
        values.put(CHAMPION_COLUMN_AVATAR, champion.getAvatar());

        return getWritableDatabase().insert(TABLE_CHAMPION, null, values);
    }

    public ChampionCursor queryChampion(int cid) {
        Cursor cursor = getReadableDatabase().query(TABLE_CHAMPION, new String[]{"*"},
                CHAMPION_COLUMN_CID + "=?", new String[]{String.valueOf(cid)}, null, null, null);
        return new ChampionCursor(cursor);
    }

    public ChampionCursor queryChampions(String selection, String[] selectionArgs) {
        Cursor cursor = getReadableDatabase().query(TABLE_CHAMPION, new String[]{"*"},
                selection, selectionArgs, null, null, CHAMPION_DEFAULT_ORDER);
        return new ChampionCursor(cursor);
    }

    public int deleteChampion(int cid) {
        return getWritableDatabase().delete(TABLE_CHAMPION, CHAMPION_COLUMN_CID + "=?",
                new String[]{String.valueOf(cid)});
    }

    public void deleteChampions() {
        SQLiteStatement delStmt = null;
        try {
            delStmt = getWritableDatabase().compileStatement("delete from " + TABLE_CHAMPION);
            delStmt.execute();
        } finally {
            if (delStmt != null) {
                delStmt.close();
            }
        }
    }

    public void insertSkins(ArrayList<Skin> skins) {
        SQLiteStatement insertStmt = null;
        SQLiteStatement deleteStmt = null;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            deleteStmt = db.compileStatement("delete from " + TABLE_SKIN);
            insertStmt = db.compileStatement("insert into " + TABLE_SKIN +
                    " values(?, ?, ?, ?, ?, ?)");
            // 清空原有的皮肤数据
            deleteStmt.execute();
            // 添加新的皮肤数据
            for (Skin role : skins) {
                insertStmt.bindNull(1);
                insertStmt.bindLong(2, role.getId());
                insertStmt.bindLong(3, role.getCid());
                insertStmt.bindLong(4, role.getNum());
                insertStmt.bindString(5, role.getName());
                insertStmt.bindString(6, role.getCover());
                insertStmt.execute();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }

    public long insertSkin(Skin skin) {
        ContentValues values = new ContentValues();
        values.put(SKIN_COLUMN_SID, skin.getId());
        values.put(SKIN_COLUMN_CID, skin.getCid());
        values.put(SKIN_COLUMN_NUM, skin.getNum());
        values.put(SKIN_COLUMN_NAME, skin.getNum());
        values.put(SKIN_COLUMN_COVER, skin.getCover());

        return getWritableDatabase().insert(TABLE_SKIN, null, values);
    }

    public SkinCursor querySkins(int cid) {
        Cursor cursor = getReadableDatabase().query(TABLE_SKIN, new String[]{"*"},
                SKIN_COLUMN_CID + "=?", new String[]{String.valueOf(cid)}, null, null, SKIN_DEFAULT_ORDER);
        return new SkinCursor(cursor);
    }

    public SkinCursor querySkin(int sid) {
        Cursor cursor = getReadableDatabase().query(TABLE_SKIN, new String[]{"*"},
                SKIN_COLUMN_SID + "=?", new String[]{String.valueOf(sid)}, null, null, null);
        return new SkinCursor(cursor);
    }

    public SkinCursor querySkin(int cid, int num) {
        Cursor cursor = getReadableDatabase().query(TABLE_SKIN, new String[]{"*"},
                SKIN_COLUMN_CID + "=? and " + SKIN_COLUMN_NUM + "=?",
                new String[]{String.valueOf(cid), String.valueOf(num)}, null, null, null);
        return new SkinCursor(cursor);
    }

    public SkinCursor querySkins() {
        Cursor cursor = getReadableDatabase().query(TABLE_SKIN, new String[]{"*"},
                null, null, null, null, null);
        return new SkinCursor(cursor);
    }

    public int deleteSkin(int sid) {
        return getWritableDatabase().delete(TABLE_SKIN, SKIN_COLUMN_SID + "=?",
                new String[]{String.valueOf(sid)});
    }

    public void deleteSkins() {
        SQLiteStatement delStmt = null;
        try {
            delStmt = getWritableDatabase().compileStatement("delete from " + TABLE_SKIN);
            delStmt.execute();
        } finally {
            if (delStmt != null) {
                delStmt.close();
            }
        }
    }

    public long insertFavorite(int cid) {
        ContentValues values = new ContentValues();
        values.put(FAVORITES_COLUMN_CID, cid);
        values.put(FAVORITES_COLUMN_ADD_TIME, System.currentTimeMillis());

        return getWritableDatabase().insert(TABLE_FAVORITES, null, values);
    }

    public int deleteFavorite(int cid) {
        return getWritableDatabase().delete(TABLE_FAVORITES, FAVORITES_COLUMN_CID + "=?",
                new String[]{String.valueOf(cid)});
    }

    public void deleteFavorites() {
        SQLiteStatement delStmt = null;
        try {
            delStmt = getWritableDatabase().compileStatement("delete from " + TABLE_FAVORITES);
            delStmt.execute();
        } finally {
            if (delStmt != null) {
                delStmt.close();
            }
        }
    }

    public Cursor getFavorite(int cid) {
        return getReadableDatabase().query(TABLE_FAVORITES, new String[]{"*"},
                FAVORITES_COLUMN_CID + "=?", new String[]{String.valueOf(cid)}, null, null, null);
    }

    public ChampionCursor getFavorites() {
        String selection = String.format("%s.%s=%s.%s", TABLE_CHAMPION, CHAMPION_COLUMN_CID,
                TABLE_FAVORITES, FAVORITES_COLUMN_CID);
        Cursor cursor = getReadableDatabase().query(TABLE_CHAMPION + "," + TABLE_FAVORITES,
                new String[]{"*"}, selection, null, null, null, FAVORITES_DEFAULT_ORDER);
        return new ChampionCursor(cursor);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 升级数据库
    }

    public class ChampionCursor extends CursorWrapper {

        /**
         * Creates a cursor wrapper.
         *
         * @param cursor The underlying cursor to wrap.
         */
        public ChampionCursor(Cursor cursor) {
            super(cursor);
        }

        public Champion getChampion() {
            if (isBeforeFirst() || isAfterLast())
                return null;

            Champion champion = new Champion();
            champion.setId(getInt(getColumnIndex(CHAMPION_COLUMN_CID)));
            champion.setKey(getString(getColumnIndex(CHAMPION_COLUMN_KEY)));
            champion.setName(getString(getColumnIndex(CHAMPION_COLUMN_NAME)));
            champion.setTitle(getString(getColumnIndex(CHAMPION_COLUMN_TITLE)));
            champion.setLore(getString(getColumnIndex(CHAMPION_COLUMN_LORE)));
            champion.setAvatar(getString(getColumnIndex(CHAMPION_COLUMN_AVATAR)));
            return champion;
        }
    }

    public class SkinCursor extends CursorWrapper {

        /**
         * Creates a cursor wrapper.
         *
         * @param cursor The underlying cursor to wrap.
         */
        public SkinCursor(Cursor cursor) {
            super(cursor);
        }

        public Skin getSkin() {
            if (isBeforeFirst() || isAfterLast())
                return null;

            Skin skin = new Skin();
            skin.setId(getInt(getColumnIndex(SKIN_COLUMN_SID)));
            skin.setCid(getInt(getColumnIndex(SKIN_COLUMN_CID)));
            skin.setNum(getInt(getColumnIndex(SKIN_COLUMN_NUM)));
            skin.setName(getString(getColumnIndex(SKIN_COLUMN_NAME)));
            skin.setCover(getString(getColumnIndex(SKIN_COLUMN_COVER)));
            return skin;
        }
    }
}
