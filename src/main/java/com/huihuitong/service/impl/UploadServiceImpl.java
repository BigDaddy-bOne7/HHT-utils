package com.huihuitong.service.impl;

import com.huihuitong.meta.ListStatus;
import com.huihuitong.meta.OrderDetail;
import com.huihuitong.meta.OrderHeader;
import com.huihuitong.meta.OrderInfo;
import com.huihuitong.service.GetFormIdService;
import com.huihuitong.service.GetOrderInfoService;
import com.huihuitong.service.ServiceProcess;
import com.huihuitong.service.UploadService;
import com.huihuitong.utils.Utils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class UploadServiceImpl implements UploadService, ServiceProcess {
    private String cookie = Utils.getMybatisDao().getParkCookie();

    private GetOrderInfoService orderService = new GetOrderInfoServiceImpl();
    private GetFormIdService formService = new GetFormIdServiceImpl();
    DecimalFormat df = new DecimalFormat("#0.0000");
    @Override
    public String execute(String copNo) {
        if (Utils.getMybatisDao().getStatus(copNo) == 1) {
            return upload(copNo);
        }else{
            return null;
        }
    }

    @Override
    public String upload(String copNo) {
        // 获取运单编号
        ListStatus listStatus = Utils.getMybatisDao().getListStatusByCopNo(copNo);
        String logisticsNo = listStatus.getLogisticsNo();
        // 获取清单编号
        String listNo = listStatus.getListNo();
        // 获取订单信息
        String orderNo = listStatus.getOrderNo();
        OrderInfo orderInfo = orderService.getOrderInfo(orderNo, logisticsNo);
        // 获取企业备案名称
        if (orderInfo == null) {
            System.out.println("copNo的值是：" + copNo + ",当前方法=UploadServiceImpl.upload()");
            return "ECM中无此订单:"+copNo;
        }
        OrderHeader orderHeader = orderInfo.getOrderHeader();
        String cusName = Utils.getMybatisDao().getCusCode(orderHeader.getOrgCode());
        // 上传表头信息
        uploadHeader(orderHeader, listNo, cusName);

        String formId = null;
        int n = 0;
        while (formId == null && n < 10) {
            // 延时1秒
            try {
                Thread.sleep(2000);
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
        List<OrderDetail> orderDetails = orderInfo.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            // 延时1秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(formId + "....." + orderDetail.getGname());
            uploadBody(orderDetail, formId, orderHeader.getOrgCode());

        }
        // 更新数据库状态
        Utils.getMybatisDao().updateListStatus(copNo, 2);
        Utils.temporaryNum++;
        return null;
    }

    @Override
    public void uploadHeader(OrderHeader orderHeader, String listNo, String cusName) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://pub.szcport.cn/bbmi/headImOLShoppingOutSaveOrUpdate.action");
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build());
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
        formparams.add(new BasicNameValuePair("storeFormHead.packNo", "1"));
        formparams.add(new BasicNameValuePair("storeFormHead.grossWt", df.format(orderHeader.getGrossWeight()) + ""));
        formparams.add(new BasicNameValuePair("storeFormHead.netwet", df.format(orderHeader.getNetWeight()) + ""));
        formparams.add(new BasicNameValuePair("storeFormHead.fee", "0.0"));
        formparams.add(new BasicNameValuePair("storeFormHead.insur", "0.0"));
        formparams.add(new BasicNameValuePair("storeFormHead.tradeCountry", "142"));
        formparams.add(new BasicNameValuePair("storeFormHead.sender", orderHeader.getCopName()));
        formparams.add(new BasicNameValuePair("storeFormHead.senderCountry", "142"));
        formparams.add(new BasicNameValuePair("storeFormHead.senderCity", "深圳市"));
        formparams.add(new BasicNameValuePair("storeFormHead.receiver", orderHeader.getBuyerName()));
        formparams.add(new BasicNameValuePair("storeFormHead.receiverCountry", "142"));
        formparams.add(new BasicNameValuePair("storeFormHead.receiverCity", orderHeader.getConsigneeCity()));
        formparams.add(new BasicNameValuePair("storeFormHead.receiverIdCard", orderHeader.getBuyerIdNumber()));
        formparams.add(new BasicNameValuePair("storeFormHead.receiverTel", orderHeader.getBuyerTelephone()));
        formparams.add(new BasicNameValuePair("storeFormHead.agentCode", "4403660004"));
        formparams.add(new BasicNameValuePair("storeFormHead.agentName", "深圳市卓鼎汇通供应链服务有限公司"));
        formparams.add(new BasicNameValuePair("storeFormHead.tradeCo", "4403660004"));
        formparams.add(new BasicNameValuePair("storeFormHead.ebpCode", orderHeader.getEbpCode()));
        formparams.add(new BasicNameValuePair("storeFormHead.orderNo", orderHeader.getOrderNo()));
        formparams.add(new BasicNameValuePair("storeFormHead.logisticsCode", "4403360004"));
        formparams.add(new BasicNameValuePair("storeFormHead.logisticsNo", orderHeader.getLogisticsNo()));
        formparams.add(new BasicNameValuePair("storeFormHead.payCode", orderHeader.getPayCode()));
        formparams.add(new BasicNameValuePair("storeFormHead.paymentNo", orderHeader.getPayTransactionId()));
        formparams.add(new BasicNameValuePair("storeFormHead.trafNo", orderHeader.getTranfNo()));
        formparams.add(new BasicNameValuePair("businessTypeMark", "E11W"));
        formparams.add(new BasicNameValuePair("storeFormHead.operType", "E11W"));
        formparams.add(new BasicNameValuePair("storeFormHead.IEMode", "E"));
        formparams.add(new BasicNameValuePair("storeFormHead.relativeFormId", listNo));
        formparams.add(new BasicNameValuePair("storeFormHead.cusName", cusName));
        formparams.add(new BasicNameValuePair("storeFormHead.cusCode", orderHeader.getOrgCode()));
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
    public void uploadBody(OrderDetail orderDetail, String formId, String cusCode) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://pub.szcport.cn/bbmi/listImOLShoppingOutSaveOrUpdate.action");
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build());
        httpPost.setHeader("Cookie", "JSESSIONID=" + cookie);
        // 添加参数
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("relation.mergerMode", "0"));
        formparams.add(new BasicNameValuePair("relation.goodsId", orderDetail.getItemRecordNo()));
        formparams.add(new BasicNameValuePair("relation.codeTs", orderDetail.getGcode()));
        formparams.add(new BasicNameValuePair("relation.GNameCn", orderDetail.getGname()));
        formparams.add(new BasicNameValuePair("relation.GModel", orderDetail.getGmodel()));
        formparams.add(new BasicNameValuePair("relation.GQty", orderDetail.getQty()));
        formparams.add(new BasicNameValuePair("relation.GUnit", orderDetail.getUnit()));
        formparams.add(new BasicNameValuePair("relation.declTotal", orderDetail.getTotalPrice()));
        formparams.add(new BasicNameValuePair("relation.curr", "142"));
        double qty1 = Integer.valueOf(orderDetail.getQty())*Double.valueOf(orderDetail.getQty1());
        formparams.add(new BasicNameValuePair("relation.qty1", df.format(qty1)+""));
        formparams.add(new BasicNameValuePair("relation.unit1", "035"));
        formparams.add(new BasicNameValuePair("relation.originCountry", orderDetail.getOriCountry()));
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
                String redirectUrl = "Location: http://pub.szcport.cn/bbmi/queryImOLShoppingOutRelations.action";
                System.out.println("的值是：" + response.getFirstHeader("Location").toString() + ",当前方法=UploadServiceImpl.uploadBody()");
                if(!redirectUrl.equals(response.getFirstHeader("Location").toString())){
                    uploadBody(orderDetail,formId,cusCode);
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
