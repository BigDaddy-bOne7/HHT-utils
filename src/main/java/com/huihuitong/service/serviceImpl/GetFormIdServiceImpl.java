package com.huihuitong.service.serviceImpl;

import com.huihuitong.service.GetFormIdService;
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
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangz on 2017/7/20 10:23.
 */
@Service
public class GetFormIdServiceImpl implements GetFormIdService {
    @Override
    public String getFormId(String listNo) {
        String formId = "";
        String parkCookie = Utils.getMybatisDao().getUserById(1).getParkCookie();
        System.out.println("listNo:" + listNo + "parkCookie:" + parkCookie);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://pub.szcport.cn/bbmi/bbmStoreFormHeadQQuery.action");
        httpPost.setHeader("Cookie", "JSESSIONID=" + parkCookie);

        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("bbmStoreFormHeadQueryBean.formType", "E11W"));
        formparams.add(new BasicNameValuePair("bbmStoreFormHeadQueryBean.statusInfo", "10"));
        formparams.add(new BasicNameValuePair("bbmStoreFormHeadQueryBean.relativeFormId", listNo));
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
                    formId = html.xpath("//*[@id='table_area']/table/tbody/tr[1]/td[2]/text()").toString();
                    System.out.println(formId);
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

        return formId;
    }

}
