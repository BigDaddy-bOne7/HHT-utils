package com.huihuitong.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huihuitong.service.SlideShowService;
import com.huihuitong.service.impl.SlideShowServiceImpl;
import com.huihuitong.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class ShowStatusController {

    @RequestMapping(value = "/showStatus")
    public void getStatus(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        SlideShowService slideShowService = new SlideShowServiceImpl();
        List<String> deliverIds = Utils.getMybatisDao().listDeliverId();
        System.out.println("deliverIds的值是：" + deliverIds + ",当前方法=ShowStatusController.getStatus()");
        JSONArray jsonArray = new JSONArray();
        for (String deliverId : deliverIds) {
            JSONObject json = new JSONObject();
            json.put("deliverId", deliverId);
            json.put("passNum", slideShowService.getPassNum(deliverId));
            json.put("temporaryNum", slideShowService.getTemporaryNum(deliverId));
            jsonArray.add(json);
        }
        System.out.println("jsonArray的值是：" + jsonArray.toJSONString() + ",当前方法=ShowStatusController.getStatus()");
        try {
            PrintWriter out = response.getWriter();
            out.println(jsonArray.toJSONString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
