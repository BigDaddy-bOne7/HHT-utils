package com.huihuitong.service.impl;

import com.huihuitong.meta.ListStatus;
import com.huihuitong.meta.User;
import com.huihuitong.service.DownloadService;
import com.huihuitong.utils.Utils;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import static com.huihuitong.utils.Utils.*;


/**
 * @author yangz
 */
public class DownloadServiceImpl implements DownloadService, PageProcessor {
    // 获取全局变量
    private String uniteCookie;
    private User user;
    private LocalDate StartDate;
    private LocalDate EndDate;
    // 设置页面显示编码，添加cookie
    private Site site = Site.me().setRetryTimes(10).setSleepTime(3 * 1000).setTimeOut(100000).setCharset("GBK")
            .addHeader("Cookie", "JSESSIONID=" + uniteCookie)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
            .addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("Host", "www.szceb.cn");

    public DownloadServiceImpl() {
    }

    private DownloadServiceImpl(String userName, LocalDate startDate, LocalDate endDate) {
        this.user = getMybatisDao().getUser(userName);
        this.uniteCookie = user.getUniteCookie();
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.site = Site.me().setRetryTimes(10).setSleepTime(3 * 1000).setTimeOut(100000).setCharset("GBK")
                .addHeader("Cookie", "JSESSIONID=" + uniteCookie);
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void downLoad(String userName, LocalDate startDate, LocalDate endDate) {
        new com.huihuitong.service.impl.UniteLoginServiceImpl().login(userName);
        System.out.println("1的值是：" + 1 + ",当前方法=DownloadServiceImpl.downLoad()");
        user = getMybatisDao().getUser(userName);
        uniteCookie = user.getUniteCookie();
        System.out.println("cookie:" + uniteCookie);
        StartDate = (startDate == null) ? LocalDate.now() : startDate;
        EndDate = (endDate == null) ? LocalDate.now() : endDate;
        totalNo = 0;
        presentNo = 0;
        Spider.create(new DownloadServiceImpl(userName, startDate, endDate))
                .addUrl("http://www.szceb.cn/goodsBill.do?method=searchGoodsBillStatus&page=1" + "&cusStatus=31"
                        + "&qdateType=1&strCreateStartTime=" + StartDate + "&strCreateEndTime=" + EndDate)
                .thread(20).run();
        System.out.println("quest complete!");
        page = 1;
    }

    @Override
    public void process(Page page) {
        // 获取页面数量，存为Utils.lastPage
        String pageNo = page.getHtml()
                .xpath("/html/body/table/tbody/tr[2]/td[2]/table[3]/tbody/tr/td/table/tbody/tr/td/div/text()")
                .toString();
        if (pageNo != null) {
            if ("没有找到任何数据".equals(pageNo.trim())) {
                Utils.page = lastPage + 1;
            } else {
                String m = Pattern.compile("[^,0-9]").matcher(pageNo).replaceAll("").trim();
                String[] ls = m.split(",");
                lastPage = Integer.valueOf(ls[1]);
                totalNo = Integer.valueOf(ls[0]);
            }
        }
        // 爬取内部编号，清单编号，存为2个数组
        List<String> copNo = page.getHtml().xpath("/html/body/table/tbody/tr[2]/td[2]/table[2]/tbody/tr/td[1]/a/text()")
                .all();
        List<String> listNo = page.getHtml().xpath("/html/body/table/tbody/tr[2]/td[2]/table[2]/tbody/tr/td[3]/text()")
                .all();
        List<String> logisticsNo = page.getHtml()
                .xpath("/html/body/table/tbody/tr[2]/td[2]/table[2]/tbody/tr/td[4]/text()").all();
        List<String> orderNo = page.getHtml().xpath("/html/body/table/tbody/tr[2]/td[2]/table[2]/tbody/tr/td[5]/text()")
                .all();
        List<String> date = page.getHtml().xpath("/html/body/table/tbody/tr[2]/td[2]/table[2]/tbody/tr/td[10]/text()")
                .all();
        // 将爬取到的数据存到数据库
        for (int i = 0; i < copNo.size(); i++) {
            ListStatus lns = new ListStatus();
            lns.setOrgCode(user.getOrgCode());
            lns.setCopNo(copNo.get(i).trim());
            lns.setListNo(listNo.get(i + 1).trim());
            lns.setLogisticsNo(logisticsNo.get(i + 1).trim());
            lns.setOrderNo(orderNo.get(i + 1).trim());
            String date1 = date.get(i+1).substring(0,date.get(i+1).indexOf(" "));
            lns.setDate(Date.valueOf(date1));
            lns.setStatus(1);
            ListStatus listStatus = getMybatisDao().getListStatusByCopNo(copNo.get(i).trim());
            if (listStatus == null) {
                getMybatisDao().insertListStatus(lns);
            }
            presentNo++;
            System.out.println(presentNo + "/" + totalNo);
        }
        while (Utils.page <= lastPage) {
            page.addTargetRequest("http://www.szceb.cn/goodsBill.do?method=searchGoodsBillStatus&page=" + Utils.page++
                    + "&cusStatus=31" + "&qdateType=1&strCreateStartTime=" + StartDate + "&strCreateEndTime="
                    + EndDate);
        }
    }

}
