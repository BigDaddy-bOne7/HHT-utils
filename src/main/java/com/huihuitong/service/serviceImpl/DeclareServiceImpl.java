package com.huihuitong.service.serviceImpl;

import com.huihuitong.meta.ListStatus;
import com.huihuitong.service.DeclareService;
import com.huihuitong.utils.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeclareServiceImpl implements DeclareService {
    @Override
    public void declare(String listNo) {
        ListStatus listStatus = Utils.getMybatisDao().getListStatusByListNo(listNo);
        String formId = listStatus.getFormId();
        String token = getToken(listNo, formId);
        String parkCookie = Utils.getMybatisDao().getUserById(1).getParkCookie();
        System.out.println("listNo:" + listNo + "parkCookie:" + parkCookie);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://pub.szcport.cn/bbmi/whbDeclare.action");
        httpPost.setHeader("Cookie", "JSESSIONID=" + parkCookie);
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("struts.token.name", "token"));
        formparams.add(new BasicNameValuePair("token", token));
        formparams.add(new BasicNameValuePair("formId", formId));
        formparams.add(new BasicNameValuePair("pageIndex", "1"));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "GBK");
            httpPost.setEntity(uefEntity);
            System.out.println("executing request " + httpPost.getURI());
            httpclient.execute(httpPost);
            Utils.getMybatisDao().updateListStatusByFormId(formId,3);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getToken(String listNo, String formId) {
        String token = null;
        String parkCookie = Utils.getMybatisDao().getUserById(1).getParkCookie();
        System.out.println("listNo:" + listNo + "parkCookie:" + parkCookie);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://pub.szcport.cn/bbmi/whbManageQuery.action");
        httpPost.setHeader("Cookie", "JSESSIONID=" + parkCookie);
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("queryBean.operType", "E11W"));
        formparams.add(new BasicNameValuePair("queryBean.formId", formId));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "GBK");
            httpPost.setEntity(uefEntity);
            System.out.println("executing request " + httpPost.getURI());
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Html html = Html.create(EntityUtils.toString(entity, "UTF-8"));
                    System.out.println(html);
                    token = html.xpath("//*[@id=\"head_list\"]/input[4]//@value").toString();
                    System.out.println(token);

                }
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
        return token;
    }
}
