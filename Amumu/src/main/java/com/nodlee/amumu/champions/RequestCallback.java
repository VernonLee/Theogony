package com.nodlee.amumu.champions;

import java.util.ArrayList;

/**
 * Created by nodlee on 16/6/15.
 */
public interface RequestCallback {

    /**
     * 在成功获取所有英雄数据调用
     * @param champions
     */
    public void onSuccess (ArrayList<Champion> champions);

    /**
     * 在获取数据异常的时候调用
     * @param errCode 错误码
     * @param errorMsg 错误消息
     */
    public void onFailed(int errCode, String errorMsg);

}
