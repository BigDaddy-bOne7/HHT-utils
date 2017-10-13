package com.huihuitong.service;

import com.huihuitong.meta.OrderInfo;

public interface GetOrderInfoService {
    OrderInfo getOrderInfo(String copNo, String logisticsNo);
}
