package com.nodlee.amumu.champions;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.nodlee.amumu.bean.Champion;

import java.util.ArrayList;

/**
 * 异步数据请求回调接口
 * Created by nodlee on 16/6/15.
 */
public abstract class RequestCallback extends Handler {

    public RequestCallback() {
        super(Looper.getMainLooper());
    }

    /**
     * 在成功获取数据时调用
     *
     * @param champions
     */
    public void onSuccess(ArrayList<Champion> champions) {
    }

    /**
     * 在获取数据异常时调用
     *
     * @param errCode 错误码
     */
    public void onFailed(int errCode) {
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == ChampionsRequester.SUCCESS) {
            onSuccess((ArrayList<Champion>) msg.obj);
        } else {
            onFailed(msg.arg1);
        }
    }
}
