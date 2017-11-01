package com.huihuitong.service.impl;

import com.ehking.sdk.entity.CustomsInfo;
import com.ehking.sdk.entity.Payer;
import com.ehking.sdk.entity.ProductDetail;
import com.ehking.sdk.hg.builder.OrderBuilder;
import com.ehking.sdk.hg.executer.HgOrderExecuter;
import com.huihuitong.meta.QueryConfig;
import com.huihuitong.meta.YibaoPaymentOrderInfo;
import com.huihuitong.service.YibaoPaymentService;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.List;

import static com.huihuitong.utils.Utils.getYibaoDao;

/**
 * @author yangz
 * @date 2017/10/24 16:01:00
 */
public class YibaoPaymentServiceImpl implements YibaoPaymentService {
    @Override
    public List<YibaoPaymentOrderInfo> listOrders(QueryConfig config) {
        return getYibaoDao().listOrders(config);
    }

    @Override
    public void sendOrders(List<String> orders) {
        for (String order : orders) {
            sendOrder(order);
        }
    }

    private void sendOrder(String orderCode){
        Configuration config = null;
        try {
            config = new PropertiesConfiguration("ehking-config.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        assert config != null;
        String merchantId = config.getString("merchantId");
        OrderBuilder orderBuilder = new OrderBuilder(merchantId);
        System.out.println("orderCode的值是：" + orderCode + ",当前方法=YibaoPaymentServiceImpl.sendOrder()");
        Long orderAmount = getYibaoDao().getOrderAmount(orderCode);
        System.out.println("orderAmount的值是：" + orderAmount + ",当前方法=YibaoPaymentServiceImpl.sendOrder()");
        List<ProductDetail> productDetails = getYibaoDao().getProductDetails(orderCode);
        Payer payer = getYibaoDao().getPayer(orderCode);
        CustomsInfo customsInfo = getYibaoDao().getCustomsInfo(orderCode);
        customsInfo.setGoodsAmount(428L);
        customsInfo.setTax(73L);
        orderAmount = 510L;
        customsInfo.setDxpid("DXPENT0000012642");
        System.out.println("customsInfo的值是：" + customsInfo.getStoreHouse() + ",当前方法=YibaoPaymentServiceImpl.sendOrder()");
        orderBuilder.setRequestId(orderCode).setOrderAmount(orderAmount.toString()).setOrderCurrency("CNY").setRemark("备注")
                .setPayer(payer).addCustomsInfo(customsInfo);
        for (ProductDetail productDetail : productDetails) {
            productDetail.setAmount(510L);
            orderBuilder.addProductDetail(productDetail);
        }
        new HgOrderExecuter().order(orderBuilder.build(),new YibaoResultListener());
        System.out.println("over的值是：" + "over" + ",当前方法=YibaoPaymentServiceImpl.sendOrder()");
    }
}
