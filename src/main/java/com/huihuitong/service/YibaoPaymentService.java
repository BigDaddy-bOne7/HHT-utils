package com.huihuitong.service;

import java.util.List;

/**
 *
 * @author yangz
 * @date 2017/10/24 9:39:44
 */
public interface YibaoPaymentService {
    /**
     * 列出某一时间段订单列表
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return List<orderId> 返回订单列表
     *
     */
    List<String> listOrders(String startDate,String endDate);

    /**
     * 发送订单信息到易宝接口
     * @param orders 订单列表
     */
    void sendOrders(List<String> orders);



}
