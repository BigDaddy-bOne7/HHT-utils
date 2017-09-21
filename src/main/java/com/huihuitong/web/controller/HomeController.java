package com.huihuitong.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huihuitong.meta.ListNoStatus;
import com.huihuitong.meta.User;
import com.huihuitong.service.serviceImpl.DownloadServiceImpl;
import com.huihuitong.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by yangz on 2017/7/20 9:46.
 */
@Controller
public class HomeController {

    private static boolean isRunning = false;


    // 首页
    @RequestMapping(value = "/")
    public String LoginPage(HttpServletRequest request) {
        if (request.getSession().getAttribute("userName") == null) {
            return "login";
        } else {
            return "index";
        }

    }

    @RequestMapping(value = "/index")
    public String HomePage(HttpServletRequest request) {
        if (request.getSession().getAttribute("userName") == null) {
            return "login";
        } else {
            return "index";
        }

    }

    @RequestMapping(value = "/download")
    @ResponseBody
    public String Download(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("userName") == null) {
            return "login";
        }

        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        JSONObject json = new JSONObject();
        int code;
        String message;
        boolean result = false;
        try {
            PrintWriter out = response.getWriter();
            if (!isRunning) {
                isRunning = true;
                new DownloadServiceImpl().downLoad("yangzhao", startDate, endDate);
                isRunning = false;
                code = 200;
                message = "获取完毕！";
                result = true;
            } else {
                code = 400;
                message = "系统繁忙，请稍等几分钟再试";
            }
            json.put("code", code);
            json.put("message", message);
            json.put("result", result);
            out.println(json.toJSONString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取爬取状态
    @RequestMapping(value = "/getNo")
    @ResponseBody
    public String getNo(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("userName") == null) {
            return "login";
        }
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("isRunning", isRunning);
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

    // 获取已爬取到的清单状态
    @RequestMapping(value = "/getDownloadList")
    @ResponseBody
    public String getDownloadList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("getDownloadList running");
        String userName;
        if (request.getSession().getAttribute("userName") == null) {
            return "login";
        } else {
            userName = request.getSession().getAttribute("userName").toString();
        }
        User user = Utils.getMybatisDao().getUser(userName);
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        JSONArray jsonArr = new JSONArray();
        System.out.println("startDate:" + startDate + ",endDate:" + endDate + ",orgCode:" + user.getOrgCode());
        List<ListNoStatus> list = Utils.getMybatisDao().getListNoStatus(1, startDate, endDate, user.getOrgCode());
        System.out.println("list.size=" + list.size());
        for (ListNoStatus lns : list) {
            JSONObject json = new JSONObject();
            json.put("copNo", lns.getCopNo());
            json.put("listNo", lns.getListNo());
            json.put("orderNo", lns.getOrderNo());
            json.put("date", lns.getDate().toString());
            jsonArr.add(json);
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.println(jsonArr.toJSONString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}