package com.huihuitong.meta;

/**
 * Created by yangz on 2017/10/25 14:25:44.
 */
public class CustomsInfo {
    private String customsChannel;
    private String commerceCode;
    private String commerceName;
    private Long amount;
    private Long goodsAmount;
    private Long tax;
    private Long freight;
    private Long insuredAmount;
    private String status;
    private String orderId;
    private String merchantCommerceName;
    private String merchantCommerceCode;
    private String storeHouse;
    private String customsCode;
    private String ciqCode;
    private String functionCode;
    private String businessType;
    private String dxpid;

    public CustomsInfo() {
    }

    public String getCustomsChannel() {
        return this.customsChannel;
    }

    public void setCustomsChannel(String customsChannel) {
        this.customsChannel = customsChannel;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getGoodsAmount() {
        return this.goodsAmount;
    }

    public void setGoodsAmount(Long goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Long getTax() {
        return this.tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public Long getFreight() {
        return this.freight;
    }

    public void setFreight(Long freight) {
        this.freight = freight;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchantCommerceName() {
        return this.merchantCommerceName;
    }

    public void setMerchantCommerceName(String merchantCommerceName) {
        this.merchantCommerceName = merchantCommerceName;
    }

    public String getMerchantCommerceCode() {
        return this.merchantCommerceCode;
    }

    public void setMerchantCommerceCode(String merchantCommerceCode) {
        this.merchantCommerceCode = merchantCommerceCode;
    }

    public String getStoreHouse() {
        return this.storeHouse;
    }

    public void setStoreHouse(String storeHouse) {
        this.storeHouse = storeHouse;
    }

    public String getCustomsCode() {
        return this.customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }

    public String getCiqCode() {
        return this.ciqCode;
    }

    public void setCiqCode(String ciqCode) {
        this.ciqCode = ciqCode;
    }

    public String getFunctionCode() {
        return this.functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Long getInsuredAmount() {
        return this.insuredAmount;
    }

    public void setInsuredAmount(Long insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public String getCommerceCode() {
        return this.commerceCode;
    }

    public void setCommerceCode(String commerceCode) {
        this.commerceCode = commerceCode;
    }

    public String getCommerceName() {
        return this.commerceName;
    }

    public void setCommerceName(String commerceName) {
        this.commerceName = commerceName;
    }

    public String getDxpid() {
        return this.dxpid;
    }

    public void setDxpid(String dxpid) {
        this.dxpid = dxpid;
    }
}
