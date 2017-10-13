package com.huihuitong.service.serviceImpl;

import com.huihuitong.meta.OrderDetail;
import com.huihuitong.meta.OrderHeader;
import com.huihuitong.meta.OrderInfo;
import com.huihuitong.service.GetOrderInfoService;
import com.huihuitong.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOrderInfoServiceImpl implements GetOrderInfoService {

    @Override
    public OrderInfo getOrderInfo(String orderNo, String logisticsNo) {
        OrderInfo orderInfo = new OrderInfo();
        OrderHeader orderHeader = Utils.getMybatisDao().getOrderHeader(orderNo, logisticsNo);
        if (orderHeader != null) {
            List<OrderDetail> orderDetailList = Utils.getMybatisDao().listOrderDetails(orderHeader.getId());
            orderInfo.setOrderHeader(orderHeader);
            orderInfo.setOrderDetails(orderDetailList);
            return orderInfo;
        } else {
            return null;
        }

    }

}
