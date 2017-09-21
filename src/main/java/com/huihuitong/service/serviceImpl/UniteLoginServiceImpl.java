package com.huihuitong.service.serviceImpl;

import com.huihuitong.meta.User;
import com.huihuitong.service.UniteLoginService;
import com.huihuitong.utils.Utils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangz on 2017/7/20 10:38.
 */
@Service
public class UniteLoginServiceImpl implements UniteLoginService {
    @Override
    public void login(String userName) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String verificationCode = getVerificationCode(userName);
        System.out.println(verificationCode);
        User user = Utils.getMybatisDao().getUser(userName);
        HttpPost httpPost = new HttpPost("http://www.szceb.cn/login.do?method=login");
        httpPost.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpPost.addHeader("Accept-Encoding","gzip, deflate");
        httpPost.addHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
        httpPost.addHeader("Cache-Control","max-age=0");
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.addHeader("DNT","1");
        httpPost.addHeader("Cookie","JSESSIONID="+user.getUniteCookie());
//        httpPost.addHeader("Content-Length","52");
        httpPost.addHeader("Host","www.szceb.cn");
        httpPost.addHeader("Origin","http://www.szceb.cn");
        httpPost.addHeader("Proxy-Connection","keep-alive");
        httpPost.addHeader("Referer","http://www.szceb.cn/login.jsp");
        httpPost.addHeader("Upgrade-Insecure-Requests","1");
        httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("username", user.getUserName()));
        formparams.add(new BasicNameValuePair("password", user.getUserPassword()));
        formparams.add(new BasicNameValuePair("yanzheng", verificationCode));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "GBK");
            httpPost.setEntity(uefEntity);
            Header[] list = httpPost.getAllHeaders();
            System.out.println("list.size()的值是：" + list.length + ",当前方法=UniteLoginServiceImpl.login()");
            for (Header header : list) {
                System.out.println(header.getName()+":"+header.getValue());
            }

//            CloseableHttpResponse response =
                    httpClient.execute(httpPost);
//            HttpEntity entity = response.getEntity();
//            System.out.println("Response content: " + EntityUtils.toString(entity, "GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 获取验证码
    private String getVerificationCode(String userName) {
        User user = Utils.getMybatisDao().getUser(userName);
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        String result = "";
        // 下载验证码
        BufferedImage image = null;
        HttpPost httpPost = new HttpPost("http://www.szceb.cn/jsp/common/g_image.jsp");
        try {
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                image = ImageIO.read(entity.getContent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Utils.getMybatisDao().updateUniteCookie(user.getId(), cookieStore.getCookies().get(0).getValue());
        // 二值化图片
        try {
            image = removeBackgroud(image);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // 识别
        try {
            result = getAllOcr(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 图片二值化
    private BufferedImage removeBackgroud(BufferedImage img) throws Exception {
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        return img;
    }

    // 二值化处理方法
    private int isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 400) return 1;
        return 0;
    }

    // 分割验证码图片
    private List<BufferedImage> splitImage(BufferedImage img) throws Exception {
        List<BufferedImage> subImgs = new ArrayList<>();
        subImgs.add(img.getSubimage(7, 0, 10, 20));
        subImgs.add(img.getSubimage(20, 0, 10, 20));
        subImgs.add(img.getSubimage(33, 0, 10, 20));
        subImgs.add(img.getSubimage(46, 0, 10, 20));
        return subImgs;
    }

    // 识别所有数字
    private String getAllOcr(BufferedImage img) throws Exception {
        List<BufferedImage> listImg = splitImage(img);
        Map<BufferedImage, String> map = loadTrainData();
        StringBuilder result = new StringBuilder();
        for (BufferedImage bi : listImg) {
            result.append(getSingleCharOcr(bi, map));
        }
        return result.toString();
    }

    // 获取标准0-9图像Map
    private Map<BufferedImage, String> loadTrainData() throws Exception {
        Map<BufferedImage, String> map = new HashMap<>();
        File dir = new File(Utils.getContext().getRealPath("/WEB-INF/") + "/image");
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            map.put(ImageIO.read(file), file.getName().charAt(0) + "");
        }
        return map;
    }

    // 判断单个数字
    private String getSingleCharOcr(BufferedImage img, Map<BufferedImage, String> map) {
        String result = "";
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height;
        for (BufferedImage bi : map.keySet()) {
            int count = 0;
            Label1:
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {
                        count++;
                        if (count >= min)
                            break Label1;
                    }
                }
            }
            if (count < min) {
                min = count;
                result = map.get(bi);
            }
        }
        return result;
    }

}
