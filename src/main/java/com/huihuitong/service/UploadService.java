package com.huihuitong.service;

import com.huihuitong.meta.OrderDetail;
import com.huihuitong.meta.OrderHeader;
import com.huihuitong.meta.OrderInfo;

public interface UploadService {
    String upload(String copNo);

    void uploadHeader(OrderHeader orderHeader, String listNo, String cusName);

    void uploadBody(OrderDetail orderDetail, String formId, String cusCode);
}
