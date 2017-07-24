package com.huihuitong.service;

import com.huihuitong.meta.OrderDtl;
import com.huihuitong.meta.OrderInfo;

/**
 * Created by yangz on 2017/7/20 9:44.
 */
public interface UploadService {
    String upload(String copNo);

    void uploadHeader(OrderInfo orderInfo, String listNo, String cusName);

    void uploadBody(OrderDtl orderDtl, String formId, String cusCode);
}
