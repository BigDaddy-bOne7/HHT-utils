package com.huihuitong.service.serviceImpl;

import com.huihuitong.service.UpdateParkStatusService;
import com.huihuitong.utils.Utils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.regex.Pattern;

public class UpdateParkStatusServiceImpl implements UpdateParkStatusService, PageProcessor {
    private String deliverId;
    private Site site;
    private int pages = 2;
    private int pageNo = 1;

    private UpdateParkStatusServiceImpl(String deliverId) {
        this.deliverId = deliverId;
        String cookie = Utils.getMybatisDao().getParkCookie();
        this.site = Site.me().setRetryTimes(10).setSleepTime(3 * 1000).setTimeOut(100000).setCharset("GBK")
                .addHeader("Cookie", "JSESSIONID=" + cookie)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Host", "pub.szcport.cn");
    }

    public UpdateParkStatusServiceImpl() {
    }

    @Override
    public void updateListStatus(String deliverId) {

        Spider.create(new UpdateParkStatusServiceImpl(deliverId))
                .addUrl("http://pub.szcport.cn/bbmi/bbmStoreFormHeadQQuery.action?bbmStoreFormHeadQueryBean.formType=E11W" +
                        "&bbmStoreFormHeadQueryBean.trafNo=" + deliverId)
                .thread(10).run();
    }

    @Override
    public void process(Page page) {
        String pageNum = page.getHtml().xpath("//*[@id='page_area']/text()").toString();
        if (pageNum != null) {
            String m = Pattern.compile("[^\\s0-9]").matcher(pageNum).replaceAll("").trim();
            System.out.println("m的值是：" + m + ",当前方法=UpdateParkStatusServiceImpl.process()");
            String[] ls = m.split("\\s");
            pages = Integer.valueOf(ls[1]);
        }
        while (pageNo < pages) {
            page.addTargetRequest("http://pub.szcport.cn/bbmi/bbmStoreFormHeadQQuery.action?bbmStoreFormHeadQueryBean.formType=E11W" +
                    "&bbmStoreFormHeadQueryBean.trafNo=" + deliverId + "&pageIndex=" + (++pageNo));
        }
        List<String> formIds = page.getHtml().xpath("//*[@id=\"table_area\"]/table/tbody/tr/td[2]/text()").all();
        List<String> statuses = page.getHtml().xpath("//*[@id=\"table_area\"]/table/tbody/tr/td[9]/text()").all();
        for (int i = 0; i < formIds.size(); i++) {
            Utils.getMybatisDao().updateParkStatus(statuses.get(i), formIds.get(i));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
