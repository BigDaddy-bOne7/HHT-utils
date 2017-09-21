package com.huihuitong.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.huihuitong.meta.OrderInfo;
import com.huihuitong.service.GetOrderInfoService;
import com.huihuitong.utils.Utils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;


import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by yangz on 2017/7/20 10:31.
 */
@Service
public class GetOrderInfoServiceImpl implements GetOrderInfoService {

    @Override
    public OrderInfo getOrderInfo(String copNo, String logisticsNo) {
        JSONObject jsonObj = new JSONObject();
        String orderNo = Utils.getMybatisDao().getOrderNo(copNo);
        jsonObj.put("orderNo", orderNo);
        jsonObj.put("logisticsNo", logisticsNo);

        System.out.println("jsonObj:" + jsonObj.toJSONString());
        OrderInfo orderInfo = new OrderInfo();
        // 读取配置信息
        XMLConfiguration config = null;
        try {
            config = new XMLConfiguration("classpath:config.xml");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        // 获取sign
        assert config != null;
        String serverIp = config.getString("serverIp");
        String customerName = config.getString("customerName");
        String interfaceVersion = config.getString("interfaceVersion");
        String customerIp = config.getString("customerIp");
        String sessionKey = config.getString("sessionKey");
        String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String appKey = config.getString("appKey");
        System.out
                .println(interfaceVersion + ":" + customerIp + ":" + sessionKey + ":" + datetime + ":" + appKey + ":");
        String sign = Utils.MD5(interfaceVersion + customerIp + sessionKey + datetime + appKey);

        // 通过httpclient发送请求
        String url = "http://" + serverIp + "/ecm/interface/rest/" + customerName + "Interface/GetOrderInfo";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // 添加参数
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("v", interfaceVersion));
        formparams.add(new BasicNameValuePair("ip", customerIp));
        formparams.add(new BasicNameValuePair("sessionKey", sessionKey));
        formparams.add(new BasicNameValuePair("datetime", datetime));
        formparams.add(new BasicNameValuePair("sign", sign));
        try {
            formparams.add(new BasicNameValuePair("JSON_OBJ",
                    Base64.getEncoder().encodeToString(jsonObj.toJSONString().getBytes("utf-8"))));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "utf-8");
            httpPost.setEntity(uefEntity);
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String respon = EntityUtils.toString(entity, "UTF-8");
                    JSONObject json = JSONObject.parseObject(respon, JSONObject.class);
                    JSONObject result = JSONObject.parseObject(json.getString("ROWSET"), JSONObject.class);
                    if ("1000".equals(result.get("resultCode").toString())) {
                        byte[] base64decodedBytes = Base64.getDecoder().decode(result.get("resultMsg").toString());
                        String resultMsg = new String(base64decodedBytes, "utf-8");
                        System.out.println(resultMsg);
                        orderInfo = JSONObject.parseObject(resultMsg, OrderInfo.class);
                        System.out.println("从ECM获取到商品为  " + orderInfo.getOrderDtls().get(0).getGname() + " 的订单信息");
                    } else {
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return orderInfo;
    }

}
