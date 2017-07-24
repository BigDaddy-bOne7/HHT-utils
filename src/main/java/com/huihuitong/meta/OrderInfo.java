package com.huihuitong.meta;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by yangz on 2017/7/20 9:40.
 */
@Repository
public class OrderInfo {
    private String CopName;
    private int packNo;
    private double grossWeight;
    private double netWeight;
    private double freight;
    private double insuredFee;
    private String country;
    private String buyerName;
    private String consigneeCountry;
    private String consigneeCity;
    private String buyerIdNumber;
    private String buyerTelephone;
    private String ebpCode;
    private String orderNo;
    private String logisticsCode;
    private String logisticsNo;
    private String payCode;
    private String payTransactionId;
    private String tranfNo;
    private String OrgCode;
    private double taxTotal;
    private ArrayList<com.huihuitong.meta.OrderDtl> OrderDtls;

    public OrderInfo() {
    }

    public String getCopName() {
        return CopName;
    }

    public void setCopName(String copName) {
        CopName = copName;
    }

    public int getPackNo() {
        return packNo;
    }

    public void setPackNo(int packNo) {
        this.packNo = packNo;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getInsuredFee() {
        return insuredFee;
    }

    public void setInsuredFee(double insuredFee) {
        this.insuredFee = insuredFee;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getConsigneeCountry() {
        return consigneeCountry;
    }

    public void setConsigneeCountry(String consigneeCountry) {
        this.consigneeCountry = consigneeCountry;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public String getBuyerIdNumber() {
        return buyerIdNumber;
    }

    public void setBuyerIdNumber(String buyerIdNumber) {
        this.buyerIdNumber = buyerIdNumber;
    }

    public String getBuyerTelephone() {
        return buyerTelephone;
    }

    public void setBuyerTelephone(String buyerTelephone) {
        this.buyerTelephone = buyerTelephone;
    }

    public String getEbpCode() {
        return ebpCode;
    }

    public void setEbpCode(String ebpCode) {
        this.ebpCode = ebpCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayTransactionId() {
        return payTransactionId;
    }

    public void setPayTransactionId(String payTransactionId) {
        this.payTransactionId = payTransactionId;
    }

    public String getTranfNo() {
        return tranfNo;
    }

    public void setTranfNo(String tranfNo) {
        this.tranfNo = tranfNo;
    }

    public String getOrgCode() {
        return OrgCode;
    }

    public void setOrgCode(String orgCode) {
        OrgCode = orgCode;
    }

    public double getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(double taxTotal) {
        this.taxTotal = taxTotal;
    }

    public ArrayList<com.huihuitong.meta.OrderDtl> getOrderDtls() {
        return OrderDtls;
    }

    public void setOrderDtls(ArrayList<com.huihuitong.meta.OrderDtl> orderDtls) {
        OrderDtls = orderDtls;
    }
}
