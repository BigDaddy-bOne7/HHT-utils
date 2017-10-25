package com.huihuitong.service.impl;

import com.ehking.sdk.executer.ResultListener;
import com.huihuitong.service.YibaoPaymentService;

import java.util.List;

/**
 *
 * @author yangz
 * @date 2017/10/24 16:01:00
 */
public class YibaoPaymentServiceImpl implements YibaoPaymentService {
    @Override
    public List<String> listOrders(String startDate, String endDate) {
        ResultListener resultListener = new YibaoResultListener();
        return null;
    }

    @Override
    public void sendOrders(List<String> orders) {

    }
}
