package com.nodlee.amumu.champions;

/**
 * 数据解析者
 * Created by nodlee on 16/7/11.
 */
public interface Parser<T> {

    /**
     * 获取数据版本，如5.24.1
     * @return
     */
    public String getVersion();

    /**
     * 获取解析后的数据
     * @return
     */
    public T getData();

}
