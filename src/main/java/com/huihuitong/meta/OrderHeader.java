package com.huihuitong.meta;

public class OrderHeader {
    private int id;
    private String CopName;
    private int packNo;
    private double grossWeight;
    private double netWeight;
    private String buyerName;
    private String consigneeCity;
    private String buyerIdNumber;
    private String buyerTelephone;
    private String ebpCode;
    private String orderNo;
    private String logisticsNo;
    private String payCode;
    private String payTransactionId;
    private String tranfNo;
    private String OrgCode;

    public OrderHeader() {
    }

    @Override
    public String toString() {
        return "OrderHeader{" +
                "id=" + id +
                ", CopName='" + CopName + '\'' +
                ", packNo=" + packNo +
                ", grossWeight=" + grossWeight +
                ", netWeight=" + netWeight +
                ", buyerName='" + buyerName + '\'' +
                ", consigneeCity='" + consigneeCity + '\'' +
                ", buyerIdNumber='" + buyerIdNumber + '\'' +
                ", buyerTelephone='" + buyerTelephone + '\'' +
                ", ebpCode='" + ebpCode + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", logisticsNo='" + logisticsNo + '\'' +
                ", payCode='" + payCode + '\'' +
                ", payTransactionId='" + payTransactionId + '\'' +
                ", tranfNo='" + tranfNo + '\'' +
                ", OrgCode='" + OrgCode + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderHeader that = (OrderHeader) o;

        if (id != that.id) return false;
        if (packNo != that.packNo) return false;
        if (Double.compare(that.grossWeight, grossWeight) != 0) return false;
        if (Double.compare(that.netWeight, netWeight) != 0) return false;
        if (CopName != null ? !CopName.equals(that.CopName) : that.CopName != null) return false;
        if (buyerName != null ? !buyerName.equals(that.buyerName) : that.buyerName != null) return false;
        if (consigneeCity != null ? !consigneeCity.equals(that.consigneeCity) : that.consigneeCity != null)
            return false;
        if (buyerIdNumber != null ? !buyerIdNumber.equals(that.buyerIdNumber) : that.buyerIdNumber != null)
            return false;
        if (buyerTelephone != null ? !buyerTelephone.equals(that.buyerTelephone) : that.buyerTelephone != null)
            return false;
        if (ebpCode != null ? !ebpCode.equals(that.ebpCode) : that.ebpCode != null) return false;
        if (orderNo != null ? !orderNo.equals(that.orderNo) : that.orderNo != null) return false;
        if (logisticsNo != null ? !logisticsNo.equals(that.logisticsNo) : that.logisticsNo != null) return false;
        if (payCode != null ? !payCode.equals(that.payCode) : that.payCode != null) return false;
        if (payTransactionId != null ? !payTransactionId.equals(that.payTransactionId) : that.payTransactionId != null)
            return false;
        if (tranfNo != null ? !tranfNo.equals(that.tranfNo) : that.tranfNo != null) return false;
        return OrgCode != null ? OrgCode.equals(that.OrgCode) : that.OrgCode == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (CopName != null ? CopName.hashCode() : 0);
        result = 31 * result + packNo;
        temp = Double.doubleToLongBits(grossWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(netWeight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (buyerName != null ? buyerName.hashCode() : 0);
        result = 31 * result + (consigneeCity != null ? consigneeCity.hashCode() : 0);
        result = 31 * result + (buyerIdNumber != null ? buyerIdNumber.hashCode() : 0);
        result = 31 * result + (buyerTelephone != null ? buyerTelephone.hashCode() : 0);
        result = 31 * result + (ebpCode != null ? ebpCode.hashCode() : 0);
        result = 31 * result + (orderNo != null ? orderNo.hashCode() : 0);
        result = 31 * result + (logisticsNo != null ? logisticsNo.hashCode() : 0);
        result = 31 * result + (payCode != null ? payCode.hashCode() : 0);
        result = 31 * result + (payTransactionId != null ? payTransactionId.hashCode() : 0);
        result = 31 * result + (tranfNo != null ? tranfNo.hashCode() : 0);
        result = 31 * result + (OrgCode != null ? OrgCode.hashCode() : 0);
        return result;
    }
}
