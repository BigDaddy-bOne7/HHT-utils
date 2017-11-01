package com.huihuitong.service;

import com.huihuitong.meta.QueryConfig;
import com.huihuitong.meta.YibaoPaymentOrderInfo;

import java.util.List;

/**
 *
 * @author yangz
 * @date 2017/10/24 9:39:44
 */
public interface YibaoPaymentService {
    /**
     * 获取需要发送支付报文的订单号
     * @param config 查询设置
     * @return 订单号列表
     */
    List<YibaoPaymentOrderInfo> listOrders(QueryConfig config);

    /**
     * 发送订单信息到易宝接口
     * @param orders 订单列表
     */
    void sendOrders(List<String> orders);



}
