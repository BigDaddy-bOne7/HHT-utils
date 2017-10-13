package com.huihuitong.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.huihuitong.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class LoginController {
    // 登陆页面
    @RequestMapping(value = "/login")
    public String Login(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        // 若有session,已登陆,跳转到index
        if (session.getAttribute("User") != null) {
            return "index";
        }
        // 返回登陆页面
        return "login";
    }

    // 登出按钮
    @RequestMapping(value = "/logout")
    public String Logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        // 使session失效
        session.invalidate();
        return "login";
    }

    // 登陆按钮
    @RequestMapping(value = "/loginService")
    public void LoginService(HttpServletRequest request, HttpServletResponse response) {
        // 读取表单中的账号密码
        try {
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        System.out.println(userName + ":" + userPassword);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");

        int code;
        String message;
        boolean result = false;
        String password;
        JSONObject json = new JSONObject();
        // 获取session
        HttpSession session = request.getSession(true);



        if (userName.equals("")) {
            code = 400;
            message = "用户名为空！";
        } else {
            // 获取与表单中填写的用户名相对应的数据库中的密码
            password = Utils.getMybatisDao().getUser(userName).getUserPassword();
            // 比较表单中填写的密码是否与数据库中密码一致
            if (password != null && password.equals(userPassword)) {
                session.setAttribute("userName", userName);
                session.setMaxInactiveInterval(60 * 60);
                code = 200;
                message = "登陆成功！";
                result = true;
            } else {
                code = 400;
                message = "用户名或密码错误！";
            }
        }

        json.put("code", code);
        json.put("message", message);
        json.put("result", result);
        // 若登陆成功，跳转到index
        try {
            PrintWriter out = response.getWriter();
            out.println(json.toJSONString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}