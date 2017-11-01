package com.huihuitong.meta;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderInfo {
    private OrderHeader orderHeader;

    private List<OrderDetail> orderDetails;

    public OrderInfo() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderInfo orderInfo = (OrderInfo) o;

        return (orderHeader != null ? orderHeader.equals(orderInfo.orderHeader) : orderInfo.orderHeader == null) && (orderDetails != null ? orderDetails.equals(orderInfo.orderDetails) : orderInfo.orderDetails == null);
    }

    @Override
    public int hashCode() {
        int result = orderHeader != null ? orderHeader.hashCode() : 0;
        result = 31 * result + (orderDetails != null ? orderDetails.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderHeader=" + orderHeader +
                ", orderDetails=" + orderDetails +
                '}';
    }

    public OrderHeader getOrderHeader() {
        return orderHeader;
    }

    public void setOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
