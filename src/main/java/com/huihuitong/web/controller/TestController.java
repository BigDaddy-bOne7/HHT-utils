package com.huihuitong.web.controller;

import com.huihuitong.meta.QueryConfig;
import com.huihuitong.meta.YibaoPaymentOrderInfo;
import com.huihuitong.service.impl.ParkLoginServiceImpl;
import com.huihuitong.service.impl.UpdateParkStatusServiceImpl;
import com.huihuitong.service.impl.YibaoPaymentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author yangz
 * @date 2017/10/11 11:28:20
 */
@Controller
public class TestController {
    @RequestMapping(value = "/test")
    public void testPage(HttpServletRequest request) {
        QueryConfig config = new QueryConfig();
        config.setCompanyCode("YYQ");
        config.setDeliverId("YD12");
        config.setStartDate("2017-10-29 00:00:00");
        config.setEndDate("2017-10-30 23:59:59");
        System.out.println("hello的值是：" + "hello" + ",当前方法=TestController.LoginPage()");
        List<YibaoPaymentOrderInfo> list = new YibaoPaymentServiceImpl()
                .listOrders(config);
        System.out.println("list.size()的值是：" + list.size() + ",当前方法=TestController.LoginPage()");
    }
}
