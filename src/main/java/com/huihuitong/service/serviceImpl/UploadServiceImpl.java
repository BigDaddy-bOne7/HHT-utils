package com.huihuitong.service.serviceImpl;

import com.huihuitong.meta.OrderDtl;
import com.huihuitong.meta.OrderInfo;
import com.huihuitong.service.GetFormIdService;
import com.huihuitong.service.GetOrderInfoService;
import com.huihuitong.service.ServiceProcess;
import com.huihuitong.service.UploadService;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangz on 2017/7/20 10:48.
 */
public class UploadServiceImpl implements UploadService, ServiceProcess {
    private String cookie = Utils.getMybatisDao().getUserById(1).getParkCookie();

    private GetOrderInfoService orderService = new GetOrderInfoServiceImpl();
    private GetFormIdService formService = new GetFormIdServiceImpl();

    @Override
    public void execute(String copNo) {
        if (Utils.getMybatisDao().getStatus(copNo) == 1) {
            upload(copNo);
        }
    }

    @Override
    public String upload(String copNo) {
        // 获取运单编号
        String logisticsNo = Utils.getMybatisDao().getLogisticsNo(copNo);
        // 获取清单编号
        String listNo = Utils.getMybatisDao().getListNo(copNo);
        // 获取订单信息
        OrderInfo orderInfo = orderService.getOrderInfo(copNo, logisticsNo);
        // 获取企业备案名称
        System.out.println(orderInfo.getOrgCode());
        String cusName = Utils.getMybatisDao().getCusCode(orderInfo.getOrgCode());
        // 上传表头信息
        uploadHeader(orderInfo, listNo, cusName);

        String formId = null;
        int n = 0;
        while (formId == null && n < 5) {
            // 延时1秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            n++;
            formId = formService.getFormId(listNo);
        }
        if (formId == null) {
            System.out.println("error!---listNo:" + listNo);
            return null;
        } else {
            Utils.getMybatisDao().updateFormId(formId, listNo);
        }
        // 上传表体信息
        List<OrderDtl> orderDtls = orderInfo.getOrderDtls();
        for (OrderDtl orderDtl : orderDtls) {
            System.out.println(formId + "....." + orderDtl.getGname());
            uploadBody(orderDtl, formId, orderInfo.getOrgCode());
            // 延时1秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 更新数据库状态
        Utils.getMybatisDao().updateListNoStatus(copNo, 2);
        return null;
    }

    @Override
    public void uploadHeader(OrderInfo orderInfo, String listNo, String cusName) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://pub.szcport.cn/bbmi/headImOLShoppingOutSaveOrUpdate.action");
        httpPost.setHeader("Cookie", "JSESSIONID=" + cookie);
        // 添加参数
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("storeFormHead.emsNo", "I440366000415001"));
        formparams.add(new BasicNameValuePair("storeFormHead.IEPort", "5349"));
        formparams.add(new BasicNameValuePair("storeFormHead.tradeMode", "1210"));
        formparams.add(new BasicNameValuePair("storeFormHead.declPort", "5349"));
        formparams.add(new BasicNameValuePair("storeFormHead.destinationPort", "142"));
        formparams.add(new BasicNameValuePair("storeFormHead.trafMode", "Y"));
        formparams.add(new BasicNameValuePair("storeFormHead.wrapType", "2"));
        formparams.add(new BasicNameValuePair("storeFormHead.packNo", orderInfo.getPackNo() + ""));
        formparams.add(new BasicNameValuePair("storeFormHead.grossWt", orderInfo.getGrossWeight() + ""));
        formparams.add(new BasicNameValuePair("storeFormHead.netwet", orderInfo.getNetWeight() + ""));
        formparams.add(new BasicNameValuePair("storeFormHead.fee", orderInfo.getFreight() + ""));
        formparams.add(new BasicNameValuePair("storeFormHead.insur", orderInfo.getInsuredFee() + ""));
        formparams.add(new BasicNameValuePair("storeFormHead.tradeCountry", "142"));
        formparams.add(new BasicNameValuePair("storeFormHead.sender", orderInfo.getCopName()));
        formparams.add(new BasicNameValuePair("storeFormHead.senderCountry", "142"));
        formparams.add(new BasicNameValuePair("storeFormHead.senderCity", "深圳市"));
        formparams.add(new BasicNameValuePair("storeFormHead.receiver", orderInfo.getBuyerName()));
        formparams.add(new BasicNameValuePair("storeFormHead.receiverCountry", orderInfo.getConsigneeCountry()));
        formparams.add(new BasicNameValuePair("storeFormHead.receiverCity", orderInfo.getConsigneeCity()));
        formparams.add(new BasicNameValuePair("storeFormHead.receiverIdCard", orderInfo.getBuyerIdNumber()));
        formparams.add(new BasicNameValuePair("storeFormHead.receiverTel", orderInfo.getBuyerTelephone()));
        formparams.add(new BasicNameValuePair("storeFormHead.agentCode", "4403660004"));
        formparams.add(new BasicNameValuePair("storeFormHead.agentName", "深圳市卓鼎汇通供应链服务有限公司"));
        formparams.add(new BasicNameValuePair("storeFormHead.tradeCo", "4403660004"));
        formparams.add(new BasicNameValuePair("storeFormHead.ebpCode", orderInfo.getEbpCode()));
        formparams.add(new BasicNameValuePair("storeFormHead.orderNo", orderInfo.getOrderNo()));
        formparams.add(new BasicNameValuePair("storeFormHead.logisticsCode", orderInfo.getLogisticsCode()));
        formparams.add(new BasicNameValuePair("storeFormHead.logisticsNo", orderInfo.getLogisticsNo()));
        formparams.add(new BasicNameValuePair("storeFormHead.payCode", orderInfo.getPayCode()));
        formparams.add(new BasicNameValuePair("storeFormHead.paymentNo", orderInfo.getPayTransactionId()));
        formparams.add(new BasicNameValuePair("storeFormHead.trafNo", orderInfo.getTranfNo()));
        formparams.add(new BasicNameValuePair("businessTypeMark", "E11W"));
        formparams.add(new BasicNameValuePair("storeFormHead.operType", "E11W"));
        formparams.add(new BasicNameValuePair("storeFormHead.IEMode", "E"));
        formparams.add(new BasicNameValuePair("storeFormHead.relativeFormId", listNo));
        formparams.add(new BasicNameValuePair("storeFormHead.cusName", cusName));
        formparams.add(new BasicNameValuePair("storeFormHead.cusCode", orderInfo.getOrgCode()));
        formparams.add(new BasicNameValuePair("storeFormHead.relativeFormType", "6"));

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "GBK");
            httpPost.setEntity(uefEntity);

            System.out.println("executing request " + httpPost.getURI());
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("--------------------------------------");
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    System.out.println("--------------------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void uploadBody(OrderDtl orderDtl, String formId, String cusCode) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://pub.szcport.cn/bbmi/listImOLShoppingOutSaveOrUpdate.action");
        httpPost.setHeader("Cookie", "JSESSIONID=" + cookie);
        // 添加参数
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("relation.mergerMode", "0"));
        formparams.add(new BasicNameValuePair("relation.goodsId", orderDtl.getItemRecordNo()));
        formparams.add(new BasicNameValuePair("relation.codeTs", orderDtl.getGcode()));
        formparams.add(new BasicNameValuePair("relation.GNameCn", orderDtl.getGname()));
        formparams.add(new BasicNameValuePair("relation.GModel", orderDtl.getGmodel()));
        formparams.add(new BasicNameValuePair("relation.GQty", orderDtl.getQty()));
        formparams.add(new BasicNameValuePair("relation.GUnit", orderDtl.getUnit()));
        formparams.add(new BasicNameValuePair("relation.declTotal", orderDtl.getTotalPrice()));
        formparams.add(new BasicNameValuePair("relation.curr", "142"));
        formparams.add(new BasicNameValuePair("relation.qty1", orderDtl.getQty1()));
        formparams.add(new BasicNameValuePair("relation.unit1", orderDtl.getUnit1()));
        formparams.add(new BasicNameValuePair("relation.originCountry", orderDtl.getOriCountry()));
        formparams.add(new BasicNameValuePair("relation.useTo", "11"));
        formparams.add(new BasicNameValuePair("pageIndex", "1"));
        formparams.add(new BasicNameValuePair("relation.formId", formId));
        formparams.add(new BasicNameValuePair("relation.emsNo", "I440366000415001"));
        formparams.add(new BasicNameValuePair("relation.cusCode", cusCode));
        formparams.add(new BasicNameValuePair("relation.tradeCo", "4403660004"));

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "GBK");
            httpPost.setEntity(uefEntity);

            System.out.println("executing request " + httpPost.getURI());
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
