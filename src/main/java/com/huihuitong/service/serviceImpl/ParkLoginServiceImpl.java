package com.huihuitong.service.serviceImpl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.huihuitong.service.ParkLoginService;
import com.huihuitong.utils.Utils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ParkLoginServiceImpl implements ParkLoginService {
    private static final String LOGIN_URL = "http://pub.szcport.cn/bbmi/index.action";

    @Override
    public void login() {
        @SuppressWarnings("resource") final WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);

        webClient.getOptions().setThrowExceptionOnScriptError(false);

        webClient.getOptions().setCssEnabled(true);

        webClient.getOptions().setJavaScriptEnabled(true);

        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        webClient.getOptions().setTimeout(300000);

        // 获取首页
        HtmlPage page = null;
        try {
            page = webClient.getPage(LOGIN_URL);
        } catch (FailingHttpStatusCodeException | IOException e) {
            e.printStackTrace();
        }

        String sJs = "javascript:card.userName = '胡长富'; card.userCardNo = '8910000366158'; "
                + "card.companyName = '深圳市卓鼎汇通供应链服务有限公司'; card.companyOrgCode = '072538088'; "
                + "card.password = '88888888'; var url = 'checkCASign.action'; var jsondata = transfPara(card, 'cardInfo'); "
                + "$.post(url, jsondata, function(data) {var dataTemp = data;if (dataTemp.caSign) {getServerRandom();} "
                + "else {window.location = 'index.action';return;}}, 'json');";
        assert page != null;
        page.executeJavaScript(sJs);

        String cookie = webClient.getCookieManager().getCookie("JSESSIONID").getValue();
        System.out.println("new parkCookie = " + cookie);
        Utils.getMybatisDao().updateParkCookie(cookie);
    }
}
