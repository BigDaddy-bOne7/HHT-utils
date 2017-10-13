package com.huihuitong.web.controller;

import com.huihuitong.service.serviceImpl.ParkLoginServiceImpl;
import com.huihuitong.service.serviceImpl.UpdateParkStatusServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangz on 2017/10/11 11:28:20.
 */
@Controller
public class TestController {
    @RequestMapping(value = "/test")
    public void LoginPage(HttpServletRequest request) {
        new ParkLoginServiceImpl().login();
        new UpdateParkStatusServiceImpl().updateListStatus("ZT2");

    }
}
