package com.huihuitong.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.huihuitong.service.ServiceProcess;
import com.huihuitong.service.impl.ParkLoginServiceImpl;
import com.huihuitong.service.impl.UploadServiceImpl;
import com.huihuitong.utils.ThreadFactory;
import com.huihuitong.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangz
 */
@Controller
public class UploadController {
    private static Logger logger = Logger.getLogger(UploadController.class);
    static CountDownLatch cdl;
    private static int execTask(List<String> uploadList, Class<? extends ServiceProcess> cls) {
        ThreadPoolExecutor executor = ThreadFactory.init();
        cdl= new CountDownLatch(uploadList.size());
        for (int i = 0; i < uploadList.size(); i++) {
            ServiceTask serviceTask = new ServiceTask(i, uploadList.get(i), cls);
            logger.info("组装线程：" + i);
            executor.execute(serviceTask);
            logger.info("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" + executor.getQueue().size()
                    + "，已执行完别的任务数目：" + executor.getCompletedTaskCount());
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Utils.temporaryNum;
        // executor.shutdown();
    }

    // 上传园区版页面
    @RequestMapping(value = "/uploadList")
    public String UploadList(HttpServletRequest request, HttpServletResponse response) {
        // 获取登录用户名
        if (request.getSession().getAttribute("userName") == null) {
            return "login";
        }
        // 园区版登陆
        new ParkLoginServiceImpl().login();
        // 从前端页面获取需要上传园区版的copNo
        @SuppressWarnings("unchecked")
        List<String> uploadList = JSONObject.parseObject(request.getParameter("copNo"), List.class);
        System.out.println(uploadList);
        // 开启多线程上传到园区版
        Utils.temporaryNum = 0;
        int s = execTask(uploadList, UploadServiceImpl.class);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        JSONObject json = new JSONObject();
        json.put("message","成功暂存了"+s+"单");
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

class ServiceTask implements Runnable {
    private static Logger logger = Logger.getLogger(ServiceTask.class);
    private int taskNum;
    private String copNo;
    private Class<? extends ServiceProcess> cls;

    ServiceTask(int num, String copNo, Class<? extends ServiceProcess> cls) {
        this.taskNum = num;
        this.copNo = copNo;
        this.cls = cls;
    }

    @Override
    public void run() {
        logger.debug("ServiceTask :" + taskNum);
        try {
            cls.newInstance().execute(copNo);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.debug(e);
        }finally {
            UploadController.cdl.countDown();
        }

    }
}
