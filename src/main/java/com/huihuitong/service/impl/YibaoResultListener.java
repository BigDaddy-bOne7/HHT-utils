package com.huihuitong.service.impl;

import com.alibaba.fastjson15.JSONObject;
import com.ehking.sdk.executer.ResultListenerAdpater;

/**
 * @author yangz
 * @date 2017/10/24 15:03:22
 */
public class YibaoResultListener extends ResultListenerAdpater {

    @Override
    public void success(JSONObject jsonObject) {
        System.out.println("jsonObject的值是：" + jsonObject + ",当前方法=YibaoResultListener.success()");
    }

    @Override
    public void failure(JSONObject jsonObject) {
        System.out.println("jsonObject的值是：" + jsonObject + ",当前方法=YibaoResultListener.success()");
    }

}
