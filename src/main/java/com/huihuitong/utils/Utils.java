package com.huihuitong.utils;

import com.huihuitong.dao.MybatisDao;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import java.security.MessageDigest;

/**
 * Created by yangz on 2017/7/20 9:46.
 */
public class Utils {
    private static final ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
    private static final MybatisDao dao = ContextLoader.getCurrentWebApplicationContext().getBean("mybatisDao",
            MybatisDao.class);
    public static int page = 1;
    public static int lastPage = 2;
    public static int totalNo = 0;
    public static int presentNo = 0;
    public static String uniteFlag = "";
    public static String uniteAccount = "";

    public static ServletContext getContext() {
        return context;
    }

    public static MybatisDao getMybatisDao() {
        return dao;
    }

    public static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
