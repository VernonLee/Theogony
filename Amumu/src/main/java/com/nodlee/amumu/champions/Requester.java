package com.nodlee.amumu.champions;

import com.nodlee.amumu.util.LocaleLibrary;

/**
 * 数据请求者
 * Created by nodlee on 16/7/11.
 */
public interface Requester<T> {

    // 请求成功标志
    static final int SUCCESS = 100;
    // 请求失败标志
    static final int FAILED = 101;

    /**
     * 异步请求数据
     * @param locale 数据语言版本
     * @param callback 回调接口
     */
    void asyncRequest (LocaleLibrary.Entry locale, RequestCallback callback);

    /**
     * 同步请求数据
     * @param locale 数据语言版本
     * @return
     */
    T syncRequest(String locale);
}
