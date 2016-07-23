package com.nodlee.theogony.db;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * 数据管理者接口，包含增加、删除和获取数据功能
 * Created by nodlee on 16/7/23.
 */
public interface Manager<T> {

    /**
     * 添加单个数据
     * @param t
     */
    public boolean add(T t);
    /**
     * 增加多个数据
     */
    public boolean add(ArrayList<T> t);

    /**
     * 删除
     * @param t
     */
    public boolean delete(T t);

    /**
     * 删除全部数据
     */
    public void deleteAll();

    /**
     * 获取所有
     * @return
     */
    public Cursor getAll();

    /**
     * 获取单个数据
     * @param id
     * @return
     */
    public T get(int id);
}
