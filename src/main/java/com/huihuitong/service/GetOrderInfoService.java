package com.huihuitong.service;

import com.huihuitong.meta.OrderInfo;

/**
 * Created by yangz on 2017/7/20 9:42.
 */
public interface GetOrderInfoService {
    OrderInfo getOrderInfo(String copNo, String logisticsNo);
}
