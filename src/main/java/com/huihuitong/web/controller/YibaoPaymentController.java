package com.huihuitong.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.huihuitong.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author yangz
 * @date 2017/10/30 17:33:35
 */
@Controller
public class YibaoPaymentController {
    @RequestMapping(value = "/yibaoPayment")
    public String loginPage(HttpServletRequest request) {
        if (request.getSession().getAttribute("userName") == null) {
            return "login";
        } else {
            return "yibaoPayment";
        }
    }

    @RequestMapping(value = "/listOrder")
    @ResponseBody
    public String getNo(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("userName") == null) {
            return "login";
        }
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("presentNo", Utils.presentNo);
        json.put("totalNo", Utils.totalNo);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.println(json.toJSONString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
